package kr.eme.semiModule.objects.modules.expandable

import kr.eme.semiModule.objects.modules.AbstractExpansionModule
import org.bukkit.Material

class HouseModule(
    id: String,
    name: String,
    material: Material,
    structureWidth: Double = 0.0,
    structureHeight: Double = 0.0
) : AbstractExpansionModule(id, name, material, structureWidth, structureHeight) {
    override fun interact() {
        println("Interacting house module")
    }
}