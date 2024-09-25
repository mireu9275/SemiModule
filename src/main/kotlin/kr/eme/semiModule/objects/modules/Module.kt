package kr.eme.semiModule.objects.modules

import org.bukkit.Material

abstract class Module(
    val id: String,
    val name: String,
    val material: Material,
    val structureWidth: Double = 0.0,
    val structureHeight: Double = 0.0
) {
    abstract fun interact()
}