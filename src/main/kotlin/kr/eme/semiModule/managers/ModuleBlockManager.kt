package kr.eme.semiModule.managers

import kr.eme.semiModule.main
import org.bukkit.Location
import org.bukkit.block.Block
import java.io.File

object ModuleBlockManager {
    private val moduleBlockData = mutableMapOf<String, MutableMap<Location, String>>()

    // 블록을 모듈에 등록
    fun registerModuleBlock(block: Block, moduleId: String) {
        val blockData = moduleBlockData.getOrPut(moduleId) { mutableMapOf() }
        blockData[block.location] = moduleId
    }

    // 블록을 모듈에서 제거
    fun removeModuleBlock(block: Block, moduleId: String) {
        moduleBlockData[moduleId]?.remove(block.location)
    }

    // 블록에서 모듈 ID 가져오기
    fun getModuleBlockData(block: Block): String? {
        // 각 모듈의 블록 데이터 중, 블록의 위치와 일치하는 데이터를 찾음
        moduleBlockData.forEach { (_, blockMap) ->
            blockMap[block.location]?.let {
                return it
            }
        }
        return null
    }

    // 모듈 이름으로 블록 데이터 저장 (모듈별로 파일을 저장)
    fun saveBlockDataByModule(moduleId: String) {
        val file = File(main.dataFolder, "$moduleId.yml")
        val blockData = moduleBlockData[moduleId] ?: mutableMapOf()
        FileManager.saveModuleYmlData(file, blockData)
    }

    // 모듈 이름으로 블록 데이터 로드
    fun loadBlockDataByModule(moduleId: String) {
        val file = File(main.dataFolder, "$moduleId.yml")
        moduleBlockData[moduleId] = FileManager.loadModuleYmlData(file).toMutableMap()
    }

    // 모든 모듈 데이터 저장
    fun saveAllModules() {
        println("모든 모듈 데이터를 저장합니다.")  // 디버깅 메시지 추가
        moduleBlockData.forEach { (moduleId, _) ->
            saveBlockDataByModule(moduleId)
            println("모듈 ID: $moduleId 데이터가 저장되었습니다.")
        }
    }

    // 모든 모듈 데이터 로드
    fun loadAllModules() {
        val folder = main.dataFolder
        if (!folder.exists()) {
            println("데이터 폴더가 존재하지 않습니다.")
            return
        }

        folder.listFiles()?.forEach { file ->
            if (file.name.endsWith(".yml")) {
                val moduleId = file.nameWithoutExtension
                loadBlockDataByModule(moduleId)
                println("모듈 ID: $moduleId, 로드된 데이터: ${moduleBlockData[moduleId]}")
            } else {
                println("${file.name}은 YML 파일이 아닙니다.")
            }
        }
    }

}
