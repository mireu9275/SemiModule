package kr.eme.semiModule

import com.sk89q.worldedit.bukkit.WorldEditPlugin
import kr.eme.semiModule.commands.ModuleCommand
import kr.eme.semiModule.listeners.ModuleListener
import kr.eme.semiModule.managers.ModuleBlockManager
import org.bukkit.plugin.java.JavaPlugin

class SemiModule : JavaPlugin() {
    override fun onEnable() {
        main = this
        worldEditPlugin = server.pluginManager.getPlugin("worldEdit") as? WorldEditPlugin
        ?: run {
            logger.severe("WorldEdit 플러그인을 찾을 수 없습니다!")
            server.pluginManager.disablePlugin(this)
            return
        }
        server.pluginManager.registerEvents(ModuleListener, this)
        server.pluginManager.registerEvents(ModuleBlockListener, this)
        this.getCommand("module")?.setExecutor(ModuleCommand)
        ModuleBlockManager.loadAllModules()
    }
    override fun onDisable() {
        ModuleBlockManager.saveAllModules()
    }
}