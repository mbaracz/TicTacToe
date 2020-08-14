package net.yetrr.tictactoe.game;

import net.yetrr.tictactoe.ai.Algorithm;
import net.yetrr.tictactoe.board.Board;

public class Game {

    private Board board;
    private GameMode mode;
    private Algorithm algorithm;

    public Game(GameMode mode) {
        this.mode = mode;
        this.board = new Board();
    }

    public void setAlgorithm(Algorithm algorithm) {
        if (this.mode != GameMode.AI) {
            throw new IllegalStateException();
        }

        this.algorithm = algorithm;
    }

    public Algorithm getAlgorithm() {
        return this.algorithm;
    }

    public void setMode(GameMode mode) {
        this.mode = mode;
    }

    public GameMode getMode() {
        return this.mode;
    }

    public Board getBoard() {
        return this.board;
    }

}
