package net.yetrr.tictactoe.board;

import net.yetrr.tictactoe.Main;
import net.yetrr.tictactoe.game.Game;
import net.yetrr.tictactoe.state.State;
import processing.core.PApplet;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Board {

    public static final int SIZE = 3;

    private State[][] states;
    private State winner;
    private State turn;

    private int moveCount;
    private boolean gameOver;
    private Set<Integer> availableMoves;

    public Board() {
        this.availableMoves = new HashSet<>();
        this.states = new State[SIZE][SIZE];
        reset();
    }

    public void init() {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                this.states[x][y] = State.BLANK;
            }
        }

        this.availableMoves.clear();

        for (int i = 0; i < SIZE * SIZE; i++) {
            this.availableMoves.add(i);
        }
    }

    public void reset() {
        this.moveCount = 0;
        this.gameOver = false;
        this.winner = State.BLANK;
        this.turn = State.CROSS;
        init();
    }

    public boolean move(int index) {
        return move(index % SIZE, index / SIZE);
    }

    private boolean move(int x, int y) {
        if (this.gameOver) {
            throw new IllegalStateException("The game is over!");
        }

        if (this.states[y][x] == State.BLANK) {
            this.states[y][x] = this.turn;
        } else {
            return false;
        }

        this.moveCount++;

        this.availableMoves.remove(y * SIZE + x);
        if (this.moveCount == SIZE * SIZE) {
            this.winner = State.BLANK;
            this.gameOver = true;
        }

        checkRow(y);
        checkColumn(x);
        checkDiagonalFromTopLeft(x, y);
        checkDiagonalFromTopRight(x, y);

        this.turn = this.turn.reverse();

        if (this.gameOver) {
            return true;
        }

        Game game = Main.getInstance().getGame();
        if (this.turn != State.CROSS && game.getAlgorithm() != null) {
            game.getAlgorithm().move(this);
        }

        return true;
    }

    private void checkRow(int row) {
        for (int i = 1; i < SIZE; i++) {
            if (this.states[row][i] != this.states[row][i - 1]) {
                break;
            }

            if (i == SIZE - 1) {
                this.winner = this.turn;
                this.gameOver = true;
            }
        }
    }

    private void checkColumn(int column) {
        for (int i = 1; i < SIZE; i++) {
            if (this.states[i][column] != this.states[i - 1][column]) {
                break;
            }

            if (i == SIZE - 1) {
                this.winner = this.turn;
                this.gameOver = true;
            }
        }
    }

    private void checkDiagonalFromTopRight(int x, int y) {
        if (SIZE - 1 - x == y) {
            for (int i = 1; i < SIZE; i++) {
                if (this.states[SIZE - 1 - i][i] != this.states[SIZE - i][i - 1]) {
                    break;
                }

                if (i == SIZE - 1) {
                    this.winner = this.turn;
                    this.gameOver = true;
                }
            }
        }
    }

    private void checkDiagonalFromTopLeft(int x, int y) {
        if (x == y) {
            for (int i = 1; i < SIZE; i++) {
                if (this.states[i][i] != this.states[i - 1][i - 1]) {
                    break;
                }

                if (i == SIZE - 1) {
                    this.winner = this.turn;
                    this.gameOver = true;
                }
            }
        }
    }

    public Board getDeepCopy() {
        Board board = new Board();

        Arrays.setAll(board.states, i -> this.states[i].clone());
        board.availableMoves = new HashSet<>();
        board.availableMoves.addAll(this.availableMoves);
        board.moveCount = this.moveCount;
        board.gameOver = this.gameOver;
        board.winner = this.winner;
        board.turn = this.turn;
        return board;
    }

    public boolean isGameOver() {
        return this.gameOver;
    }

    public State getTurn() {
        return this.turn;
    }

    public State getWinner() {
        if (!this.gameOver) {
            throw new IllegalStateException("The game is not finished yet!");
        }

        return this.winner;
    }

    public Set<Integer> getAvailableMoves() {
        return this.availableMoves;
    }

    public void drawBoard(PApplet applet) {
        applet.noFill();
        applet.strokeWeight(1.5F);

        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                State state = this.states[y][x];
                applet.stroke(255);

                float minX = Main.BASE + (Main.SIZE * x), maxX = minX + Main.SIZE;
                float minY = Main.LINE_START_Y + (Main.SIZE * y), maxY = minY + Main.SIZE;

                if (state == State.CIRCLE) {
                    applet.ellipse(minX + (Main.SIZE / 2), minY + (Main.SIZE / 2), 0.6F * Main.SIZE, 0.6F * Main.SIZE);
                } else if (state == State.CROSS) {
                    applet.stroke(255, 0, 0);
                    applet.line(minX, minY, maxX, maxY);
                    applet.line(minX, maxY, maxX, minY);
                }
            }
        }
    }

    public void handleClick(float mouseX, float mouseY) {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                State state = this.states[y][x];

                if (state == State.BLANK) {
                    float xMin = Main.BASE + (Main.SIZE * x), xMax = Main.BASE;
                    float yMin = Main.LINE_START_Y + (Main.SIZE * y), yMax = Main.LINE_START_Y;

                    int xCopy = x == 0 ? 1 : x + 1, yCopy = y == 0 ? 1 : y + 1;
                    xMax += (xCopy * Main.SIZE);
                    yMax += (yCopy * Main.SIZE);

                    if (mouseX >= xMin && mouseX <= xMax && mouseY >= yMin && mouseY <= yMax) {
                        this.move(x, y);
                    }
                }
            }
        }
    }

}
