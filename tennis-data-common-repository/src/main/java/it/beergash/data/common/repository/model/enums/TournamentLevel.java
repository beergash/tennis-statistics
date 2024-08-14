package it.beergash.data.common.repository.model.enums;

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
}
