package kr.eme.semiModule.managers

import org.bukkit.entity.Player
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

object CooldownManager {
    private val cooldowns: MutableMap<UUID, Long> = ConcurrentHashMap()
    private val lastMessageTimes: MutableMap<UUID, Long> = ConcurrentHashMap()

    // 쿨다운 시간 설정 (예: 3초)
    private const val COOLDOWN_TIME: Long = 3000

    // 메시지 출력 간격 (예: 1초)
    private const val MESSAGE_INTERVAL: Long = 1000

    // 플레이어가 쿨다운 상태인지 확인
    fun isInCooldown(player: Player): Boolean {
        val lastInteractTime = cooldowns[player.uniqueId] ?: return false
        return System.currentTimeMillis() - lastInteractTime < COOLDOWN_TIME
    }

    // 남은 쿨다운 시간 계산
    fun getRemainingCooldown(player: Player): Long {
        val lastInteractTime = cooldowns[player.uniqueId] ?: return 0
        val timePassed = System.currentTimeMillis() - lastInteractTime
        return if (timePassed < COOLDOWN_TIME) {
            (COOLDOWN_TIME - timePassed) / 1000 // 초 단위로 변환
        } else {
            0
        }
    }

    // 메시지를 최근에 출력했는지 확인 (중복 방지)
    fun canShowCooldownMessage(player: Player): Boolean {
        val lastMessageTime = lastMessageTimes[player.uniqueId] ?: return true
        return System.currentTimeMillis() - lastMessageTime > MESSAGE_INTERVAL
    }

    // 메시지 출력 시간을 기록
    fun setMessageCooldown(player: Player) {
        lastMessageTimes[player.uniqueId] = System.currentTimeMillis()
    }

    // 쿨다운 시간 기록
    fun setCooldown(player: Player) {
        cooldowns[player.uniqueId] = System.currentTimeMillis()
    }
}
