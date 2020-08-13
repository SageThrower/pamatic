package tech.sodiium.pamatic;

import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.object.RelightMode;
import com.boydti.fawe.util.EditSessionBuilder;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.function.pattern.Pattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.block.BlockTypes;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

class Coordinate extends BlockVector3 {
    private final int x;
    private final int y;
    private final int z;

    public Coordinate(Location location) {
        this.x = (int)location.getX();
        this.y = (int)location.getY();
        this.z = (int)location.getZ();
    }

    public Coordinate(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }
}

public class SchematicManager {
    public static Clipboard fileToClipboard(File schematicFile) throws IOException {
        return Objects.requireNonNull(ClipboardFormats.findByFile(schematicFile)).load(schematicFile);
    }

    public static void pasteSchematic(Clipboard clipboard, Coordinate coords, com.sk89q.worldedit.world.World world) {
        EditSession editSession = clipboard.paste(world, coords);
        editSession.flushQueue();
        try {
            FaweAPI.fixLighting(world, clipboard.getRegion(), null, RelightMode.ALL);
        } catch (NullPointerException ex) {
            // Bit clunky fix but it sometimes throws a NullPointerException
            // It still works tho
        }
    }

    public static void pasteSchematic(Clipboard clipboard, Coordinate coords, World world) {
        pasteSchematic(clipboard, coords, FaweAPI.getWorld(world.getName()));
    }

    public static void pasteSchematic(Clipboard clipboard, Location location) {
        pasteSchematic(clipboard, new Coordinate(location), location.getWorld());
    }

    public static void pasteSchematic(File schematicFile, Location location) throws IOException {
        pasteSchematic(fileToClipboard(schematicFile), location);
    }

    public static void pasteSchematic(Clipboard schematicClipboard, int x, int y, int z, World world) {
        pasteSchematic(schematicClipboard, new Location(world, x, y, z));
    }

    public static void pasteSchematic(File schematicFile, int x, int y, int z, World world) throws IOException {
        pasteSchematic(fileToClipboard(schematicFile), x, y, z, world);
    }

    public static void fillBlocks(Region region, com.sk89q.worldedit.world.World world, Pattern pattern) {
        EditSession editSession = new EditSessionBuilder(world).fastmode(true).build();
        editSession.setBlocks(region, pattern);
        editSession.flushQueue();
        FaweAPI.fixLighting(world, region, null, RelightMode.ALL);
    }

    public static void fillBlocks(int x1, int y1, int z1, int x2, int y2, int z2, World world, Material block) {
        fillBlocks(new CuboidRegion(new Coordinate(x1, y1, z1), new Coordinate(x2, y2, z2)),
                FaweAPI.getWorld(world.getName()),
                BlockTypes.parse(block.name()));
    }

    public static void fillBlocks(Location location1, Location location2, Material block) {
        fillBlocks(new CuboidRegion(new Coordinate(location1), new Coordinate(location2)),
                FaweAPI.getWorld(location1.getWorld().getName()),
                BlockTypes.parse(block.name()));
    }
}
