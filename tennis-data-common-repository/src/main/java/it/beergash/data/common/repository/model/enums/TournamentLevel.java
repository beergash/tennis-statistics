package it.beergash.data.common.repository.model.enums;

import java.util.Arrays;

/**
 * @Author Andrea Aresta
 */
public enum TournamentLevel {

    GRAND_SLAM("G"),
    MASTERS("M"),
    ATP_250_500("A");

    private String value;

    TournamentLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TournamentLevel valueOfString(String value) {
        if (value == null) {
            throw new IllegalArgumentException("TournamentLevel cannot be null!");
        } else {
            return Arrays.asList(values()).stream()
                    .filter(r -> value.equals(r.value)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.format("TournamentLevel not valid: %s", value)));

        }
    }
}
