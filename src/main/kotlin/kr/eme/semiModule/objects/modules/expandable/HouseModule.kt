package kr.eme.semiModule.objects.modules.expandable

import kr.eme.semiModule.objects.modules.AbstractExpansionModule
import org.bukkit.Material

class HouseModule(
    id: String,
    name: String,
    material: Material
) : AbstractExpansionModule(id, name, material) {
    override fun interact() {
        println("Interacting house module")
    }
}