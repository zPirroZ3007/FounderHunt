package it.founderhunt.enums;

import net.tecnocraft.utils.utils.CommandFramework;

public enum GameModes {

    RISCALDAMENTO, COMPETITIVO;

    public static GameModes to(String gamemode) {
        try {
            return valueOf(gamemode.toUpperCase());
        } catch (Exception ex) {
            throw new CommandFramework.ExecuteException("Questa Gamemode non è stata riconosciuta.");
        }
    }

}
