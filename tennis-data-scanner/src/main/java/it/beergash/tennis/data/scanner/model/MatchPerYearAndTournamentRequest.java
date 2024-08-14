package it.beergash.tennis.data.scanner.model;

public class MatchPerYearAndTournamentRequest {

    private int year;
    private String tournament;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTournament() {
        return tournament;
    }

    public void setTournament(String tournament) {
        this.tournament = tournament;
    }
}
