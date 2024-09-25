package kr.eme.semiModule.interfaces

import com.sk89q.worldedit.bukkit.WorldEditPlugin
import org.bukkit.Location
import org.bukkit.block.BlockFace

interface Expandable {
    fun expandModule(location: Location, playerDirection: BlockFace, worldEdit: WorldEditPlugin)
}