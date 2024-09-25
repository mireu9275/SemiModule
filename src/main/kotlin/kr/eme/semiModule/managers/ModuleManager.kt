package kr.eme.semiModule.managers

import kr.eme.semiModule.objects.modules.expandable.BasicExpansionModule
import kr.eme.semiModule.objects.modules.expandable.CrossExpansionModule
import kr.eme.semiModule.objects.modules.expandable.LExpansionModule
import kr.eme.semiModule.objects.modules.expandable.TExpansionModule
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object ModuleManager {
    private val moduleList = mutableListOf<kr.eme.semiModule.objects.modules.Module>()

    fun registerModule(module: kr.eme.semiModule.objects.modules.Module) {
        moduleList.add(module)
    }

    fun getModuleByName(name: String): kr.eme.semiModule.objects.modules.Module? {
        return moduleList.find { it.name == name }
    }

    fun getModuleById(moduleId: String): kr.eme.semiModule.objects.modules.Module? {
        return moduleList.find { it.id == moduleId }
    }

    // 모듈 ItemStack 생성
    fun getModuleItem(moduleName: String): ItemStack? {
        val module = getModuleByName(moduleName) ?: return null
        val item = ItemStack(module.material)
        val meta = item.itemMeta ?: return null
        meta.setDisplayName("f§l${module.name}")
        meta.lore = listOf("§6설치 아이템", "§a사용법 : 설치 후 우클릭")
        return item
    }

    fun getAllModuleIds(): List<String> {
        return moduleList.map { it.id }
    }

    init {
        // 모듈 초기화
        val basicExpansionModule = BasicExpansionModule("BasicExpansionModule","기본 확장 모듈", Material.WHITE_WOOL, 14.0, 13.0)
        val lExpansionModule = LExpansionModule("LExpansionModule","L자 확장 모듈", Material.STONE)
        val tExpansionModule = TExpansionModule("TExpansionModule","T자 확장 모듈", Material.STONE)
        val crossExpansionModule = CrossExpansionModule("TExpansionModule","십자 확장 모듈", Material.STONE)

        // 모듈 등록
        registerModule(basicExpansionModule)
        registerModule(lExpansionModule)
        registerModule(tExpansionModule)
        registerModule(crossExpansionModule)

    }
}