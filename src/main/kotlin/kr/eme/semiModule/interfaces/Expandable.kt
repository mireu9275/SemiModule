package kr.eme.semiModule.interfaces

import org.bukkit.Location
import org.bukkit.block.BlockFace

interface Expandable {
    fun expandModule(location: Location, playerDirection: BlockFace)
}