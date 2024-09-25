package kr.eme.semiModule

import kr.eme.semiModule.commands.ModuleCommand
import kr.eme.semiModule.listeners.ModuleListener
import kr.eme.semiModule.managers.ModuleBlockManager
import org.bukkit.plugin.java.JavaPlugin

class SemiModule : JavaPlugin() {
    override fun onEnable() {
        main = this
        server.pluginManager.registerEvents(ModuleListener, this)
        server.pluginManager.registerEvents(ModuleBlockListener, this)
        this.getCommand("module")?.setExecutor(ModuleCommand)
        ModuleBlockManager.loadAllModules()
    }
    override fun onDisable() {
        ModuleBlockManager.saveAllModules()
    }
}