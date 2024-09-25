package kr.eme.semiModule.commands

import kr.eme.semiModule.managers.ModuleItemManager
import kr.eme.semiModule.managers.ModuleManager
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object ModuleCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("콘솔에서는 이 명령어를 사용할 수 없습니다.")
            return true
        }

        if (args.isEmpty()) {
            sendUsageMessage(sender)
            return true
        }

        when (args[0].lowercase()) {
            "list","리스트","목록" -> listModules(sender)
            "give","주기","지급" -> giveModule(sender, args)
            else -> sendUsageMessage(sender)
        }
        return true
    }

    // 사용법 출력 함수
    private fun sendUsageMessage(sender: CommandSender) {
        sender.sendMessage("§e사용법: \n/module list - 등록된 모듈 목록 보기\n/module give <PlayerName> <ModuleID> <QTY> - 특정 플레이어에게 모듈 지급")
    }

    // 모듈 목록 출력 함수
    private fun listModules(sender: CommandSender) {
        val moduleList = ModuleManager.getAllModuleIds()
        if (moduleList.isEmpty()) {
            sender.sendMessage("§c등록된 모듈이 없습니다.")
        } else {
            sender.sendMessage("§a현재 등록된 모듈 목록:")
            moduleList.forEach { sender.sendMessage("- $it") }
        }
    }

    // 모듈 지급 함수
    private fun giveModule(sender: CommandSender, args: Array<out String>) {
        if (args.size < 4) {
            sender.sendMessage("§c사용법: /module give <PlayerName> <ModuleID> <QTY>")
            return
        }

        val targetPlayer = Bukkit.getPlayer(args[1])
        val moduleId = args[2]
        val quantity = args[3].toIntOrNull()

        if (targetPlayer == null) {
            sender.sendMessage("§c해당 플레이어를 찾을 수 없습니다: ${args[1]}")
            return
        }

        if (quantity == null || quantity <= 0) {
            sender.sendMessage("§c올바른 수량을 입력해주세요: ${args[3]}")
            return
        }

        val moduleItem = ModuleItemManager.createModuleItem(moduleId)

        if (moduleItem != null) {
            moduleItem.amount = quantity
            targetPlayer.inventory.addItem(moduleItem)
            sender.sendMessage("§a${targetPlayer.name}님에게 ${quantity}개의 ${moduleId} 모듈 아이템을 지급했습니다.")
            targetPlayer.sendMessage("§a${sender.name}님이 ${quantity}개의 ${moduleId} 모듈 아이템을 지급했습니다.")
        } else {
            sender.sendMessage("§c존재하지 않는 모듈 ID입니다: $moduleId")
        }
    }
}
