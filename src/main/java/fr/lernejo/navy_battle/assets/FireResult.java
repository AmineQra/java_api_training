package fr.lernejo.navy_battle.assets;

import java.util.Arrays;

public enum FireResult {
    MISS("miss"), HIT("hit"), SUNK("sunk");

    private final String apiString;

    FireResult(String res) {
        this.apiString = res;
    }

    /**
     * Turn an API entry into a FireResult instance
     */
    public static FireResult fromAPI(String value) {
        var res = Arrays.stream(FireResult.values()).filter(f -> f.apiString.equals(value)).findFirst();

        if (res.isEmpty())
            throw new RuntimeException("Invalid value!");

        return res.get();
    }

    public String toAPI() {
        return apiString;
    }
}
