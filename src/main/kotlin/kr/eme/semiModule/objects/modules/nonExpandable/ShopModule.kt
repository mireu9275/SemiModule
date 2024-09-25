package kr.eme.semiModule.objects.modules.nonExpandable

import kr.eme.semiModule.objects.modules.Module
import org.bukkit.Material

class ShopModule(
    id: String,
    name: String,
    material: Material
) : Module(id, name, material) {
    override fun interact() {
        println("ShopModule interact")
    }
}