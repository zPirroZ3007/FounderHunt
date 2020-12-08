package it.minecraft.founderhunt.Utils;

import it.minecraft.founderhunt.FounderHunt;
import net.tecnocraft.utils.utils.fileconfigurations.ConfigFile;

public class Config {

    public static ConfigFile SPAWN;
    public static ConfigFile KITS;

    public static void initalize() {
        createFolder();
        SPAWN = ConfigFile.initialize("spawnpoints", FounderHunt.get());
        KITS = ConfigFile.initialize("kits", FounderHunt.get());
    }

    private static void createFolder() {
        FounderHunt founderHunt = FounderHunt.get();

        if (!founderHunt.getDataFolder().exists())
            if(founderHunt.getDataFolder().mkdir())
                founderHunt.getLogger().info("Cartella del plugin creata correttamente.");
    }

}
