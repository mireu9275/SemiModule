package kr.eme.semiModule.objects.modules.expandable

import kr.eme.semiModule.objects.modules.AbstractExpansionModule
import org.bukkit.Material

class BasicExpansionModule(
    id: String,
    name: String,
    material: Material
) : AbstractExpansionModule(id, name, material) {
    override fun interact() {
        println("Basic 확장 모듈과 상호작용 중입니다!")
    }
}
