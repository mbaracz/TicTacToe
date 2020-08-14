package net.yetrr.tictactoe.state;

public enum State {

    BLANK, CROSS, CIRCLE;

    public State reverse() {
        if (this == BLANK) {
            throw new IllegalArgumentException("You can't reverse the blank state!");
        }

        return this == CROSS ? CIRCLE : CROSS;
    }

}
