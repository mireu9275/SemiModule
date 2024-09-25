package kr.eme.semiModule.listeners

import kr.eme.semiModule.interfaces.Expandable
import kr.eme.semiModule.managers.ModuleBlockManager
import kr.eme.semiModule.managers.ModuleManager
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

object ModuleListener : Listener {
    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        if (event.action.isRightClick) {
            // 쿨다운 확인
            if (CooldownManager.isInCooldown(player)) {
                // 쿨다운 메시지를 한 번만 출력
                if (CooldownManager.canShowCooldownMessage(player)) {
                    val remainingTime = CooldownManager.getRemainingCooldown(player)
                    player.sendMessage("§c쿨다운 중입니다. 남은 시간: $remainingTime 초")
                    CooldownManager.setMessageCooldown(player) // 메시지 출력 시간 기록
                }
                return
            }
            val block: Block = event.clickedBlock ?: return
            val moduleId = ModuleBlockManager.getModuleBlockData(block) ?: return
            val module = ModuleManager.getModuleById(moduleId) ?: return
            println("$module ${module.id} : is Expandable ${module is Expandable}")
            if (module is Expandable) {
                val playerDirection = player.facing // 플레이어가 바라보는 방향
                module.expandModule(block.location, player.location, playerDirection)
            }
            player.sendMessage("§a${module.name} 모듈과 상호작용했습니다!")
            CooldownManager.setCooldown(player)
        }
    }
}