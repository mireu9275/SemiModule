package kr.eme.semiModule.objects.modules.expandable

import kr.eme.semiModule.objects.Offset
import kr.eme.semiModule.objects.modules.AbstractExpansionRotationModule
import org.bukkit.Material
import org.bukkit.block.BlockFace

class RotationModule (
    id: String,
    name: String,
    material: Material
) : AbstractExpansionRotationModule(id, name, material) {
    // 각 방향에 따른 오프셋을 설정
    private val offsets: Map<BlockFace, Offset> = mapOf(
        BlockFace.NORTH to Offset(1.0, 0.0, -1.0),
        BlockFace.SOUTH to Offset(-1.0, 0.0, 1.0),
        BlockFace.WEST to Offset(-1.0, 0.0, -1.0),
        BlockFace.EAST to Offset(1.0, 0.0, 1.0)
    )
    override fun getOffsets(): Map<BlockFace, Offset> {
        return offsets
    }
    override fun interact() {
        println("Rotation 모듈과 상호작용 중입니다!")
    }
}