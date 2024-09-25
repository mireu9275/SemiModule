package kr.eme.semiModule.objects.modules

import com.github.shynixn.structureblocklib.api.bukkit.StructureBlockLibApi
import com.github.shynixn.structureblocklib.api.enumeration.StructureRotation
import kr.eme.semiModule.interfaces.Expandable
import kr.eme.semiModule.main
import kr.eme.semiModule.managers.ModuleBlockManager
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.BlockFace
import java.util.logging.Level
import kotlin.io.path.exists

abstract class AbstractExpansionModule(
    id: String,
    name: String,
    material: Material,
    structureWidth: Double,
    structureHeight: Double
) : Module(id, name, material, structureWidth, structureHeight), Expandable {

    // 확장 로직 (공통)
    override fun expandModule(
        blockLocation: Location,
        playerLocaion: Location,
        playerDirection: BlockFace
    ) {
        // 스케마틱 파일 위치
        val structureFile = main.dataFolder.toPath().resolve("structures/${id.lowercase()}.nbt")

        if (!structureFile.exists()) {
            println("Structure 파일을 찾을 수 없습니다: ${structureFile.toFile().absolutePath}")
            return
        }

        val rotation = when (playerDirection) {
            BlockFace.EAST -> StructureRotation.ROTATION_90
            BlockFace.SOUTH -> StructureRotation.ROTATION_180
            BlockFace.WEST -> StructureRotation.ROTATION_270
            else -> StructureRotation.NONE // 기본값 북
        }

        val (finalWidth, finalHeight) = when (rotation) {
            StructureRotation.ROTATION_90, StructureRotation.ROTATION_270 -> structureHeight to structureWidth
            else -> structureWidth to structureHeight
        }

        val centerOffset = Location(
            playerLocaion.world,
            playerLocaion.x - finalWidth / 2,
            playerLocaion.y,
            playerLocaion.z - finalHeight / 2
        )

        StructureBlockLibApi.INSTANCE
            .loadStructure(main)
            .at(centerOffset)
            .rotation(rotation)
            .loadFromPath(structureFile)
            .onException { e -> main.logger.log(Level.SEVERE, "Structure 파일을 불러오지 못하였습니다.")}
            .onResult { _ -> main.logger.log(Level.INFO, "Structure $id 를 불러왔습니다.")}

        // 모듈 블록 제거 및 등록 해제
        blockLocation.block.type = Material.AIR
        ModuleBlockManager.removeModuleBlock(blockLocation.block, id)
        println("모듈 ${name}이 설치되었으며, 해당 모듈은 등록에서 제거되었습니다.")
    }

    // 모듈과 상호작용하는 메서드 (구현을 자식 클래스에서 정의)
    abstract override fun interact()
}
