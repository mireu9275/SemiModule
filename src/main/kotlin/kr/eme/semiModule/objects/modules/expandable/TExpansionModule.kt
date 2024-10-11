package kr.eme.semiModule.objects.modules.expandable

import kr.eme.semiModule.objects.Offset
import kr.eme.semiModule.objects.modules.AbstractExpansionRotationModule
import org.bukkit.Material
import org.bukkit.block.BlockFace

class TExpansionModule(
    id: String,
    name: String,
    material: Material
) : AbstractExpansionRotationModule(id, name, material) {
    override fun interact() {
        print("T자 확장 모듈과 상호작용 중입니다!")
    }

    override fun getOffsets(): Map<BlockFace, Offset> {
        TODO("Not yet implemented")
    }
}