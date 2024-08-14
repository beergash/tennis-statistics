package it.beergash.tennis.data.scanner.model;

public class GenericMatchRequest {

    private int startYear = 1968;
    private int endYear = 2020;

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }
}
