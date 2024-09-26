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
import java.util.logging.Level
import kotlin.io.path.exists

abstract class AbstractExpansionModule(
    id: String,
    name: String,
    material: Material
) : Module(id, name, material), Expandable {

    abstract fun getOffsets(): Map<BlockFace, Offset>

    // 확장 로직 (공통)
    override fun expandModule(
        blockLocation: Location,
        playerLocation: Location,
        playerDirection: BlockFace
    ) {
        val structureFile = main.dataFolder.toPath().resolve("structures/${id.lowercase()}.nbt")

        if (!structureFile.exists()) {
            println("Structure 파일을 찾을 수 없습니다: ${structureFile.toFile().absolutePath}")
            return
        }

        // 회전 및 미러링 설정 (필요에 따라 조정)
        val rotation = when (playerDirection) {
            BlockFace.WEST -> StructureRotation.ROTATION_90
            BlockFace.NORTH -> StructureRotation.ROTATION_180
            BlockFace.EAST -> StructureRotation.ROTATION_270
            else -> StructureRotation.NONE // 남쪽이 기본값
        }

        // 방향별 오프셋 가져오기
        val offset = getOffsets()[playerDirection] ?: Offset(0.0, 0.0, 0.0)

        // 위치 조정
        val adjustedLocation = Location(
            blockLocation.world,
            blockLocation.x + offset.x,
            playerLocation.y + offset.y,
            blockLocation.z + offset.z
        )
        // 구조물 배치
        StructureBlockLibApi.INSTANCE
            .loadStructure(main)
            .at(adjustedLocation)
            .rotation(rotation)
            .loadFromPath(structureFile)
            .onException { e -> main.logger.log(Level.SEVERE, "Structure 파일을 불러오지 못하였습니다.", e) }
            .onResult { _ -> main.logger.log(Level.INFO, "Structure $id 를 성공적으로 불러왔습니다.") }

        // 모듈 블록 제거 및 등록 해제
        blockLocation.block.type = Material.AIR
        ModuleBlockManager.removeModuleBlock(blockLocation.block, id)
        println("모듈 ${name}이 설치되었으며, 해당 모듈은 등록에서 제거되었습니다.")
    }

    // 모듈과 상호작용하는 메서드 (구현을 자식 클래스에서 정의)
    abstract override fun interact()
}
