package kr.eme.semiModule.managers

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

object FileManager {

    // 모듈의 블록 데이터를 YML 로 저장
    fun saveModuleYmlData(file: File, blockData: Map<Location, String>) {
        val yml = YamlConfiguration()

        blockData.forEach { (location, moduleId) ->
            val key = "${location.world?.name}:${location.blockX},${location.blockY},${location.blockZ}"
            yml.set(key, moduleId)
        }

        yml.save(file)
    }

    // YML 에서 모듈 블록 데이터 로드
    fun loadModuleYmlData(file: File): Map<Location, String> {
        val yml = YamlConfiguration.loadConfiguration(file)
        val blockData = mutableMapOf<Location, String>()

        yml.getKeys(false).forEach { key ->
            val parts = key.split(":")
            val world = Bukkit.getWorld(parts[0])
            val coords = parts[1].split(",")
            val location = Location(world, coords[0].toDouble(), coords[1].toDouble(), coords[2].toDouble())
            val moduleId = yml.getString(key)
            if (moduleId != null) {
                blockData[location] = moduleId
            }
        }

        return blockData
    }
}
