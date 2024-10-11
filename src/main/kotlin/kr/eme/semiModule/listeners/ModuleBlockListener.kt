package kr.eme.semiModule.listeners

import kr.eme.semiModule.managers.ModuleBlockManager
import kr.eme.semiModule.managers.ModuleItemManager
import kr.eme.semiModule.managers.ModuleManager
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent

object ModuleBlockListener : Listener {
    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        val player = event.player
        val item = event.itemInHand

        val moduleIdBeforePlace = ModuleItemManager.getModuleIdFromNBT(item)
        println("모듈 ID (놓기 전): $moduleIdBeforePlace")

        // 로그 추가: NBT 에서 모듈 ID 가져오기 전에 로그 출력
        player.sendMessage("§e아이템이 설치되었습니다: ${item.type}")
        val moduleId = ModuleItemManager.getModuleIdFromNBT(item)

        if (moduleId == null) {
            player.sendMessage("§c이 아이템은 모듈이 아닙니다.")
            return
        }

        val blockBelowLocation = event.block.location.clone().add(0.0, -1.0, 0.0)
        if (blockBelowLocation.block.type != Material.GREEN_GLAZED_TERRACOTTA) {
            player.sendMessage("§c모듈을 설치할 수 없습니다. 초록색 유광 테라코타 위에 설치해주세요.")
            event.isCancelled = true
            return
        }
        // 모듈 블록을 등록하고 플레이어에게 아이템 이름을 알림
        ModuleBlockManager.registerModuleBlock(event.block, moduleId)
        player.sendMessage("§a모듈을 설치했습니다: ${item.itemMeta?.displayName ?: moduleId}")
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val block = event.block

        // 로그 추가: 블록이 파괴되었을 때 로그 출력
        event.player.sendMessage("§e블록이 파괴되었습니다: ${block.type}")

        // 블록에 등록된 모듈을 확인하고 제거
        val moduleId = ModuleBlockManager.getModuleBlockData(block) ?: run {
            event.player.sendMessage("§c이 블록은 모듈이 아닙니다.")
            return
        }

        val moduleName = ModuleManager.getModuleByName(moduleId) ?: moduleId  // 모듈 이름 가져오기
        ModuleBlockManager.removeModuleBlock(block, moduleId)
        event.player.sendMessage("§c모듈을 파괴했습니다: $moduleName")
    }
}
