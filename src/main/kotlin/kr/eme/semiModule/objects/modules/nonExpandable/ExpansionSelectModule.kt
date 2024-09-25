package kr.eme.semiModule.objects.modules.nonExpandable

import kr.eme.semiModule.objects.modules.Module
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player

class ExpansionSelectModule(
    id: String,
    name: String,
    material: Material,
) : Module(id, name, material) {
    override fun interact() {
        println("Interacting BasicExpandModule")
    }

    fun openExpansionGUI(player: Player) {
        println("Open ExpansionGUI")
        val inventory = Bukkit.createInventory(null, 27, "모듈 선택")
        player.openInventory(inventory)
    }
}