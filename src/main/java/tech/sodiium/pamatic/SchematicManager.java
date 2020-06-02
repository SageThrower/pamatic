package tech.sodiium.pamatic;

import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.object.RelightMode;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.math.BlockVector3;
import org.bukkit.Location;
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
}
