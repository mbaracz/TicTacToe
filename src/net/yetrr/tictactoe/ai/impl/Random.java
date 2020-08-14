package net.yetrr.tictactoe.ai.impl;

import net.yetrr.tictactoe.board.Board;
import net.yetrr.tictactoe.ai.Algorithm;

public class Random implements Algorithm {

    @Override
    public void move(Board board) {
        int[] moves = new int[board.getAvailableMoves().size()];
        int index = 0;

        for (Integer item : board.getAvailableMoves()) {
            moves[index++] = item;
        }

        int randomMove = moves[new java.util.Random().nextInt(moves.length)];
        board.move(randomMove);
    }

}
