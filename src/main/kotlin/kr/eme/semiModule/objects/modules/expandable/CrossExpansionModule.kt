package kr.eme.semiModule.objects.modules.expandable

import kr.eme.semiModule.objects.Offset
import kr.eme.semiModule.objects.modules.AbstractExpansionRotationModule
import org.bukkit.Material
import org.bukkit.block.BlockFace

class CrossExpansionModule(
    id: String,
    name: String,
    material: Material
) : AbstractExpansionRotationModule(id, name, material) {
    override fun interact() {
        TODO("Not yet implemented")
    }

    override fun getOffsets(): Map<BlockFace, Offset> {
        TODO("Not yet implemented")
    }
}