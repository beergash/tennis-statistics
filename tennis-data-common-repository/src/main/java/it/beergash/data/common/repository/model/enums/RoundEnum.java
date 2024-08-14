package it.beergash.data.common.repository.model.enums;

import java.util.Arrays;

public enum RoundEnum {

    FINAL("F", 1),
    SEMIFINALS("SF", 2),
    QUARTER_FINALS("QF", 3),
    EIGHT_FINALS("R16", 4),
    R32("R32", 5),
    R64("R64", 6),
    R128("R128", 7),
    Q1("Q1", 10),
    Q2("Q2", 11),
    Q3("Q3", 12);

    private String value;
    private int stage;

    RoundEnum(String value, int stage) {
        this.value = value;
        this.stage = stage;
    }

    public static RoundEnum valueOfString(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Round cannot be null!");
        } else {
            return Arrays.asList(values()).stream()
                    .filter(r -> value.equals(r.value)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.format("Round not valid: %s", value)));

        }
    }

    public String value() {
        return this.value;
    }

    public int getStage() {
        return stage;
    }
}
