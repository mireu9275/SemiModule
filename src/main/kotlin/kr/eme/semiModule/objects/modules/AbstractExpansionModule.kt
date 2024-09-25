package kr.eme.semiModule.objects.modules

import com.sk89q.worldedit.bukkit.BukkitWorld
import com.sk89q.worldedit.bukkit.WorldEditPlugin
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats
import com.sk89q.worldedit.function.operation.Operations
import com.sk89q.worldedit.math.BlockVector3
import com.sk89q.worldedit.session.ClipboardHolder
import kr.eme.semiModule.interfaces.Expandable
import kr.eme.semiModule.managers.ModuleBlockManager
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.BlockFace
import java.io.File

abstract class AbstractExpansionModule(
    id: String,
    name: String,
    material: Material
) : Module(id, name, material), Expandable {

    // 확장 로직 (공통)
    override fun expandModule(location: Location, playerDirection: BlockFace, worldEdit: WorldEditPlugin) {
        // 스케마틱 파일 위치
        val schematicFile = File(worldEdit.dataFolder, "schematics/$id.schem")

        if (!schematicFile.exists()) {
            println("스케마틱 파일을 찾을 수 없습니다: ${schematicFile.absolutePath}")
            return
        }

        // WorldEdit 세션
        val weWorld = BukkitWorld(location.world)
        val worldEditInstance = worldEdit.worldEdit // WorldEdit 인스턴스를 가져옴

        worldEditInstance.editSessionFactory.getEditSession(weWorld, -1).use { editSession ->
            try {
                val format = ClipboardFormats.findByFile(schematicFile) ?: return
                val clipboard = format.getReader(schematicFile.inputStream())?.use { it.read() } ?: return

                // 플레이어 방향을 기준으로 회전 각도 결정
                val transform = when (playerDirection) {
                    BlockFace.EAST -> com.sk89q.worldedit.math.transform.AffineTransform().rotateY(270.0) // EAST
                    BlockFace.SOUTH -> com.sk89q.worldedit.math.transform.AffineTransform().rotateY(180.0) // SOUTH
                    BlockFace.WEST -> com.sk89q.worldedit.math.transform.AffineTransform().rotateY(90.0)// WEST
                    else -> com.sk89q.worldedit.math.transform.AffineTransform() // 기본값은 북쪽
                }

                // 클립보드 복사 및 붙여넣기
                val clipboardHolder = ClipboardHolder(clipboard)
                clipboardHolder.transform = transform

                val pasteLocation = BlockVector3.at(location.x, location.y, location.z)
                val operation = clipboardHolder.createPaste(editSession)
                    .to(pasteLocation)
                    .ignoreAirBlocks(false) // 공기 블록 무시 여부
                    .build()

                Operations.complete(operation)
                println("스케마틱 파일 ${id}.schem 이 위치 ${pasteLocation}에 설치되었습니다.")

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // 모듈 블록 제거 및 등록 해제
        location.block.type = Material.AIR
        ModuleBlockManager.removeModuleBlock(location.block, id)
        println("모듈 ${name}이 설치되었으며, 해당 모듈은 등록에서 제거되었습니다.")
    }

    // 모듈과 상호작용하는 메서드 (구현을 자식 클래스에서 정의)
    abstract override fun interact()
}
