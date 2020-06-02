package tech.sodiium.pamatic;

import org.bukkit.plugin.java.JavaPlugin;
import tech.sodiium.pamatic.commands.PasteSchematic;

import java.util.Objects;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        Objects.requireNonNull(this.getCommand("pasteschematic")).setExecutor(new PasteSchematic());
    }
}
