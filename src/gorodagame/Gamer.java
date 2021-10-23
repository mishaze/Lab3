package gorodagame;

public class Gamer {

    private String name;
    private int numInGame = 0;
    private boolean isGame = true;

    public Gamer(String name) {
        this.name = name;
    }
    public Gamer(String name, int numInGame) {
        this.name = name;
        this.numInGame = numInGame;
    }

    public int getNumInGame() {
        return numInGame;
    }

    public void setNumInGame(int numInGame) {
        this.numInGame = numInGame;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGame() {
        return isGame;
    }

    public void setGame(boolean game) {
        isGame = game;
    }
}
