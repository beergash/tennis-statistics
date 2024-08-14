package it.beergash.data.common.repository.model;

public class Match {

    private String tournament;
    private String year;
    public  String surface;
    private String playerWinner;
    private String playerLoser;
    private String nationalityPlayerWinner;
    private String nationalityPlayerLoser;
    private String score;
    private String round;
    private String tournamentLevel;

    public String getTournament() {
        return tournament;
    }

    public void setTournament(String tournament) {
        this.tournament = tournament;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSurface() {
        return surface;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }

    public String getPlayerWinner() {
        return playerWinner;
    }

    public void setPlayerWinner(String playerWinner) {
        this.playerWinner = playerWinner;
    }

    public String getPlayerLoser() {
        return playerLoser;
    }

    public void setPlayerLoser(String playerLoser) {
        this.playerLoser = playerLoser;
    }

    public String getNationalityPlayerWinner() {
        return nationalityPlayerWinner;
    }

    public void setNationalityPlayerWinner(String nationalityPlayerWinner) {
        this.nationalityPlayerWinner = nationalityPlayerWinner;
    }

    public String getNationalityPlayerLoser() {
        return nationalityPlayerLoser;
    }

    public void setNationalityPlayerLoser(String nationalityPlayerLoser) {
        this.nationalityPlayerLoser = nationalityPlayerLoser;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getTournamentLevel() {
        return tournamentLevel;
    }

    public void setTournamentLevel(String tournamentLevel) {
        this.tournamentLevel = tournamentLevel;
    }

    @Override
    public String toString() {
        return "Match{" +
                "tournament='" + tournament + '\'' +
                ", year='" + year + '\'' +
                ", surface='" + surface + '\'' +
                ", playerWinner='" + playerWinner + '\'' +
                ", playerLoser='" + playerLoser + '\'' +
                ", nationalityPlayerWinner='" + nationalityPlayerWinner + '\'' +
                ", nationalityPlayerLoser='" + nationalityPlayerLoser + '\'' +
                ", score='" + score + '\'' +
                ", round='" + round + '\'' +
                ", tournamentLevel=" + tournamentLevel +
                '}';
    }
}
