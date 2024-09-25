package kr.eme.semiModule.interfaces

import org.bukkit.Location
import org.bukkit.block.BlockFace

interface Expandable {
    fun expandModule(
        blockLocation: Location,
        playerLocation: Location,
        playerDirection: BlockFace
    )
}