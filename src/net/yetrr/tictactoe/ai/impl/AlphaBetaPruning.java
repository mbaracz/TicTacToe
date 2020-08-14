package net.yetrr.tictactoe.ai.impl;

import net.yetrr.tictactoe.board.Board;
import net.yetrr.tictactoe.ai.Algorithm;
import net.yetrr.tictactoe.state.State;

public class AlphaBetaPruning implements Algorithm {

    private final double depth;

    private final State player;

    public AlphaBetaPruning(State player, double depth) {
        if (depth < 1) {
            throw new IllegalArgumentException("Maximum depth must be greater than 0.");
        }

        this.player = player;
        this.depth = depth;
    }

    @Override
    public void move(Board board) {
        alphaBetaPruning(this.player, board, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0);
    }

    private int alphaBetaPruning(State player, Board board, double alpha, double beta, int currentDepth) {
        if (currentDepth++ == depth || board.isGameOver()) {
            return score(player, board);
        }

        if (board.getTurn() == player) {
            return getMax(player, board, alpha, beta, currentDepth);
        } else {
            return getMin(player, board, alpha, beta, currentDepth);
        }
    }

    private int getMax(State player, Board board, double alpha, double beta, int currentDepth) {
        int index = -1;

        for (Integer move : board.getAvailableMoves()) {
            Board modifiedBoard = board.getDeepCopy();
            modifiedBoard.move(move);
            int score = alphaBetaPruning(player, modifiedBoard, alpha, beta, currentDepth);

            if (score > alpha) {
                alpha = score;
                index = move;
            }

            if (alpha >= beta) {
                break;
            }
        }

        if (index != -1) {
            board.move(index);
        }

        return (int) alpha;
    }

    private int getMin(State player, Board board, double alpha, double beta, int currentDepth) {
        int index = -1;

        for (Integer move : board.getAvailableMoves()) {
            Board modifiedBoard = board.getDeepCopy();
            modifiedBoard.move(move);

            int score = alphaBetaPruning(player, modifiedBoard, alpha, beta, currentDepth);

            if (score < beta) {
                beta = score;
                index = move;
            }

            if (alpha >= beta) {
                break;
            }
        }

        if (index != -1) {
            board.move(index);
        }

        return (int) beta;
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
