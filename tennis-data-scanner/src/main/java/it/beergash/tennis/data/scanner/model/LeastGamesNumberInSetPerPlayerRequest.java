package it.beergash.tennis.data.scanner.model;

public class LeastGamesNumberInSetPerPlayerRequest extends GenericMatchRequest {

    private int leastGamesNumber;

    public int getLeastGamesNumber() {
        return leastGamesNumber;
    }

    public void setLeastGamesNumber(int leastGamesNumber) {
        this.leastGamesNumber = leastGamesNumber;
    }
}
