package kr.eme.semiModule.objects.modules.expandable

import kr.eme.semiModule.objects.Offset
import kr.eme.semiModule.objects.modules.AbstractExpansionRotationModule
import org.bukkit.Material
import org.bukkit.block.BlockFace

class HouseModule(
    id: String,
    name: String,
    material: Material
) : AbstractExpansionRotationModule(id, name, material) {
    override fun interact() {
        println("Interacting house module")
    }

    override fun getOffsets(): Map<BlockFace, Offset> {
        TODO("Not yet implemented")
    }
}