package kr.eme.semiModule.objects.modules

import com.github.shynixn.structureblocklib.api.bukkit.StructureBlockLibApi
import com.github.shynixn.structureblocklib.api.enumeration.StructureRotation
import kr.eme.semiModule.interfaces.Expandable
import kr.eme.semiModule.main
import kr.eme.semiModule.managers.ModuleBlockManager
import kr.eme.semiModule.objects.Offset
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.BlockFace
import java.nio.file.Path
import java.util.logging.Level
import kotlin.io.path.exists

abstract class AbstractExpansionRotationModule(
    id: String,
    name: String,
    material: Material
) : Module(id, name, material), Expandable {

    abstract fun getOffsets(): Map<BlockFace, Offset>

    // 구조물 파일을 가져오는 공통 메서드
    private fun getStructureFile(): Path? {
        val structureFile = main.dataFolder.toPath().resolve("structures/${id.lowercase()}.nbt")
        if (!structureFile.exists()) {
            println("Structure 파일을 찾을 수 없습니다: ${structureFile.toFile().absolutePath}")
            return null
        }
        return structureFile
    }

    // 오프셋을 계산하는 공통 메서드
    private fun getAdjustedLocation(
        blockLocation: Location,
        playerDirection: BlockFace
    ): Location {
        val offset = getOffsets()[playerDirection] ?: Offset(0.0, 0.0, 0.0)
        return blockLocation.clone().add(offset.x, offset.y, offset.z)
    }

    // 구조물 배치를 위한 공통 메서드
    private fun deployStructure(
        structureFile: Path,
        adjustedLocation: Location,
        rotation: StructureRotation
    ) {
        // 구조물 배치
        StructureBlockLibApi.INSTANCE
            .loadStructure(main)
            .at(adjustedLocation)
            .rotation(rotation)
            .loadFromPath(structureFile)
            .onException { e -> main.logger.log(Level.SEVERE, "Structure 파일을 불러오지 못하였습니다.", e) }
            .onResult { _ -> main.logger.log(Level.INFO, "Structure $id 를 성공적으로 배치했습니다.") }
    }

    // 공통적인 구조물 배치 로직을 위한 메서드
    private fun placeStructure(
        blockLocation: Location,
        rotation: StructureRotation,
        playerDirection: BlockFace
    ) {
        val structureFile = getStructureFile() ?: return

        // 오프셋 적용 후 위치 조정
        val adjustedLocation = getAdjustedLocation(blockLocation, playerDirection)

        // 공통 구조물 배치 메서드 호출
        deployStructure(structureFile, adjustedLocation, rotation)

        // 모듈 블록 제거 및 등록 해제
        blockLocation.block.type = Material.AIR
        ModuleBlockManager.removeModuleBlock(blockLocation.block, id)
        println("모듈 ${name}이 성공적으로 설치되었습니다.")
    }

    // 플레이어 시선 방향을 기준으로 확장
    override fun expandModule(
        blockLocation: Location,
        playerLocation: Location,
        playerDirection: BlockFace
    ) {
        // 회전 설정
        val rotation = when (playerDirection) {
            BlockFace.WEST -> StructureRotation.ROTATION_90
            BlockFace.NORTH -> StructureRotation.ROTATION_180
            BlockFace.EAST -> StructureRotation.ROTATION_270
            else -> StructureRotation.NONE
        }

        // 공통 로직 호출
        placeStructure(blockLocation, rotation, playerDirection)
    }

    // GUI 에서 선택된 회전 방향을 기준으로 확장
    override fun expandModule(
        blockLocation: Location,
        playerLocation: Location,
        selectedRotation: StructureRotation,
        playerDirection: BlockFace
    ) {
        // 공통 로직 호출
        placeStructure(blockLocation, selectedRotation, playerDirection)
    }

    abstract override fun interact()
}
