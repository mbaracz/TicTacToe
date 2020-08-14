package net.yetrr.tictactoe.ai.impl;

import net.yetrr.tictactoe.board.Board;
import net.yetrr.tictactoe.ai.Algorithm;
import net.yetrr.tictactoe.state.State;

public class Minimax implements Algorithm {

    private final double depth;

    private final State player;

    public Minimax(State player, double depth) {
        if (depth < 1) {
            throw new IllegalArgumentException("Maximum depth must be greater than 0!");
        }

        this.depth = depth;
        this.player = player;
    }

    @Override
    public void move(Board board) {
        miniMax(this.player, board, 0);
    }

    private int miniMax(State player, Board board, int currentDepth) {
        if (currentDepth++ == this.depth || board.isGameOver()) {
            return score(player, board);
        }

        if (board.getTurn() == player) {
            return getMax(player, board, currentDepth);
        } else {
            return getMin(player, board, currentDepth);
        }
    }

    private int getMax(State player, Board board, int currentDepth) {
        double bestScore = Double.NEGATIVE_INFINITY;
        int index = -1;

        for (Integer move : board.getAvailableMoves()) {
            Board modifiedBoard = board.getDeepCopy();
            modifiedBoard.move(move);

            int score = miniMax(player, modifiedBoard, currentDepth);

            if (score >= bestScore) {
                bestScore = score;
                index = move;
            }
        }

        board.move(index);
        return (int) bestScore;
    }

    private int getMin(State player, Board board, int currentDepth) {
        double bestScore = Double.POSITIVE_INFINITY;
        int index = -1;

        for (Integer move : board.getAvailableMoves()) {
            Board modifiedBoard = board.getDeepCopy();
            modifiedBoard.move(move);

            int score = miniMax(player, modifiedBoard, currentDepth);

            if (score <= bestScore) {
                bestScore = score;
                index = move;
            }
        }

        board.move(index);
        return (int) bestScore;
    }

    private int score(State player, Board board) {
        State opponent = player.reverse();

        if (board.isGameOver() && board.getWinner() == player) {
            return 10;
        } else if (board.isGameOver() && board.getWinner() == opponent) {
            return -10;
        } else {
            return 0;
        }
    }

}
