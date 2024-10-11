package kr.eme.semiModule.interfaces

import com.github.shynixn.structureblocklib.api.enumeration.StructureRotation
import org.bukkit.Location
import org.bukkit.block.BlockFace

interface Expandable {
    fun expandModule(
        blockLocation: Location,
        playerLocation: Location,
        playerDirection: BlockFace
    )
    fun expandModule(
        blockLocation: Location,
        playerLocation: Location,
        selectedRotation: StructureRotation,
        playerDirection: BlockFace
    )
}