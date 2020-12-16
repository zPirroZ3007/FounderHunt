package it.founderhunt.Utils;

import it.founderhunt.FounderHunt;
import net.tecnocraft.utils.utils.fileconfigurations.ConfigFile;

public class Config {

    public static ConfigFile SPAWN;
    public static ConfigFile KITS;
    public static ConfigFile DATABASE;

    public static void initalize() {
        createFolder();
        SPAWN = ConfigFile.initialize("spawnpoints", FounderHunt.inst());
        KITS = ConfigFile.initialize("kits", FounderHunt.inst());
        DATABASE = ConfigFile.initialize("database", FounderHunt.inst());
    }

    private static void createFolder() {
        FounderHunt founderHunt = FounderHunt.inst();

        if (!founderHunt.getDataFolder().exists())
            if(founderHunt.getDataFolder().mkdir())
                founderHunt.getLogger().info("Cartella del plugin creata correttamente.");
    }

}
