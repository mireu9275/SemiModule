package kr.eme.semiModule.objects.modules

import org.bukkit.Material

abstract class Module(
    val id: String,
    val name: String,
    val material: Material
) {
    abstract fun interact()
}