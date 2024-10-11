package kr.eme.semiModule.managers

import com.github.shynixn.structureblocklib.api.enumeration.StructureRotation
import kr.eme.semiModule.objects.modules.expandable.RotationModule
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

object RotationGUIManager {

    // 3 * 9 크기의 GUI 열기
    fun openRotationGUI(player: Player) {
        val inventory: Inventory = Bukkit.createInventory(null, 27, "회전 방향 선택")  // 27은 3 * 9 사이즈

        // 북(N), 서(W), 남(S), 동(E) 방향 아이템 추가 (원하는 슬롯에 배치)
        inventory.setItem(2, createDirectionItem(StructureRotation.ROTATION_180, "N", Material.BLUE_STAINED_GLASS_PANE))  // 북쪽
        inventory.setItem(9, createDirectionItem(StructureRotation.ROTATION_90, "W", Material.BLUE_STAINED_GLASS_PANE))   // 서쪽
        inventory.setItem(11, createDirectionItem(StructureRotation.NONE, "E", Material.RED_STAINED_GLASS_PANE))          // 동쪽
        inventory.setItem(19, createDirectionItem(StructureRotation.ROTATION_270, "S", Material.GREEN_STAINED_GLASS_PANE))// 남쪽

        // 플레이어에게 인벤토리 열기
        player.openInventory(inventory)
    }

    // 각 방향에 해당하는 아이템 생성
    private fun createDirectionItem(rotation: StructureRotation, name: String, material: Material): ItemStack {
        val item = ItemStack(material)
        val meta = item.itemMeta
        meta?.setDisplayName(name)
        item.itemMeta = meta
        return item
    }

    // GUI 에서 클릭 이벤트 처리
    fun handleInventoryClick(event: InventoryClickEvent) {
        val player = event.whoClicked as Player
        val clickedInventory = event.view.title  // 'title' 대신 'view.title' 사용

        if (clickedInventory == "회전 방향 선택") {
            val selectedRotation = when (event.slot) {
                2 -> StructureRotation.ROTATION_180  // 북쪽
                9 -> StructureRotation.ROTATION_90   // 서쪽
                11 -> StructureRotation.NONE          // 남쪽
                19 -> StructureRotation.ROTATION_270  // 동쪽
                else -> return
            }

            // 선택된 회전 방향으로 모듈 배치
            val rotationModule = RotationModule("rotation_module", "회전 모듈", Material.EMERALD_BLOCK)
            rotationModule.expandModule(player.location, player.location, selectedRotation, player.facing)

            player.closeInventory() // GUI 닫기
            event.isCancelled = true // 클릭 이벤트 취소
        }
    }
}
