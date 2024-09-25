package kr.eme.semiModule.managers

import kr.eme.semiModule.main
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType

object ModuleItemManager {
    private val moduleMap = mutableMapOf<String, String>() // 모듈 ID와 모듈 이름을 매핑

    fun setModuleIdToNBT(item: ItemStack, moduleId: String, moduleName: String): ItemStack {
        val meta = item.itemMeta ?: return item
        val key = NamespacedKey(main, "moduleId")
        meta.persistentDataContainer.set(key, PersistentDataType.STRING, moduleId)
        item.itemMeta = meta
        moduleMap[moduleId] = moduleName // ID와 이름을 매핑

        // 디버그 로그 추가
        println("Module ID: $moduleId, Module Name: $moduleName - NBT에 저장되었습니다.")

        return item
    }

    fun getModuleIdFromNBT(item: ItemStack): String? {
        val meta = item.itemMeta ?: return null
        val key = NamespacedKey(main, "moduleId")
        val moduleId = meta.persistentDataContainer.get(key, PersistentDataType.STRING)

        // 디버그 로그 추가
        println("NBT에서 읽은 Module ID: $moduleId")
        return moduleId
    }

    // 모듈 ID를 기반으로 모듈 아이템을 생성하는 함수 추가
    fun createModuleItem(moduleId: String): ItemStack? {
        val module = ModuleManager.getModuleById(moduleId) ?: return null
        var item = ItemStack(module.material) // 모듈에 맞는 재료로 변경

        item = setModuleIdToNBT(item, moduleId, module.name)
        val meta: ItemMeta = item.itemMeta ?: return null
        meta.setDisplayName("§a${module.name}")
        meta.lore = listOf("§6설치 아이템", "§a사용법 : 블럭 설치 후 우클릭")
        item.itemMeta = meta

        // NBT 데이터가 정상적으로 들어갔는지 확인하는 디버그 메시지
        val key = NamespacedKey(main, "moduleId")
        val savedModuleId = item.itemMeta?.persistentDataContainer?.get(key, PersistentDataType.STRING)
        println("아이템 생성 후 NBT 확인 - Module ID: $savedModuleId")

        return item
    }

}
