package kr.eme.semiModule.objects.modules.expandable

import kr.eme.semiModule.objects.modules.AbstractExpansionModule
import org.bukkit.Material

class TExpansionModule(
    id: String,
    name: String,
    material: Material
) : AbstractExpansionModule(id, name, material) {
    override fun interact() {
        print("T자 확장 모듈과 상호작용 중입니다!")
    }
}