package it.founderhunt.Utils;

import com.google.common.collect.Maps;

import java.util.HashMap;

public class Stats {

    public static HashMap<String, Integer> MAP = Maps.newHashMap();

    public static void loadAll() {
        // TODO Prende tutti i dati nel database e li memorizza nella mappa in alto.

        /*
            for(Row row : Database) {
                MAP.putIfAbsent(row.getPlayerName(), row.getPoints());
            }

         */
    }

}
