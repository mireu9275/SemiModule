package kr.eme.semiModule.objects.modules.expandable

import kr.eme.semiModule.objects.Offset
import kr.eme.semiModule.objects.modules.AbstractExpansionModule
import org.bukkit.Material
import org.bukkit.block.BlockFace

class BasicExpansionModule(
    id: String,
    name: String,
    material: Material
) : AbstractExpansionModule(id, name, material) {

    private val offsets: Map<BlockFace, Offset> = mapOf(
        BlockFace.NORTH to Offset(7.0, -5.0, 0.0),
        BlockFace.SOUTH to Offset(-7.0, -5.0, 0.0),
        BlockFace.WEST to Offset(0.0, -5.0, -7.0),
        BlockFace.EAST to Offset(0.0, -5.0, 7.0)
    )

    override fun interact() {
        println("Basic 확장 모듈과 상호작용 중입니다!")
    }

    override fun getOffsets(): Map<BlockFace, Offset> {
        return offsets
    }
}
