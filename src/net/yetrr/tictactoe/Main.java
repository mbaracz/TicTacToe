package net.yetrr.tictactoe;

import net.yetrr.tictactoe.ai.impl.AlphaBetaAdvanced;
import net.yetrr.tictactoe.ai.impl.AlphaBetaPruning;
import net.yetrr.tictactoe.ai.impl.Minimax;
import net.yetrr.tictactoe.ai.impl.Random;
import net.yetrr.tictactoe.game.Game;
import net.yetrr.tictactoe.game.GameMode;
import net.yetrr.tictactoe.state.State;
import net.yetrr.tictactoe.util.DrawUtil;
import net.yetrr.tictactoe.util.TextUtil;
import processing.core.PApplet;
import processing.core.PFont;

public class Main extends PApplet {

    private Game game;
    private PFont font;
    private float opacity = 255;

    private int level;
    private boolean mainFadeIn;
    private boolean mainFadeOut;
    private boolean playButtonClicked;

    public static float BASE;
    public static final float SIZE = 100;
    public static final float LINE_START_Y = 200;
    public static final String APPLICATION_TITLE = "Tic - Tac - Toe";

    private static Main instance;

    public Main() {
        instance = this;
    }

    @Override
    public void settings() {
        size((int) (displayWidth * 0.8), (int) (displayHeight * 0.79));
    }

    @Override
    public void setup() {
        surface.setTitle(APPLICATION_TITLE);
        font = createFont("Arial", 16, true);
        BASE = (width / 2F) - (1.5F * SIZE);
        game = new Game(GameMode.NOT_DEFINED);
    }

    @Override
    public void draw() {
        GameMode mode = this.game.getMode();

        background(0);

        DrawUtil.drawApplicationTitle(this,
                font, APPLICATION_TITLE, width,
                new int[]{255, 255, 255, (int) this.opacity}
        );

        if (!mainFadeOut) {
            DrawUtil.drawModeText(this, font, width);

            DrawUtil.drawModeButtons(this,
                    game.getMode(), width, height,
                    new int[]{231, 76, 60, (int) this.opacity},
                    new int[]{255, 255, 255, (int) this.opacity},
                    new int[]{255, 255, 255, (int) this.opacity}
            );

            if (mode == GameMode.AI) {
                DrawUtil.drawAiSubButtons(this,
                        font, level, width, height,
                        new int[]{46, 304, 113, (int) this.opacity},
                        new int[]{255, 255, 255, (int) this.opacity},
                        new int[]{255, 255, 255, (int) this.opacity}
                );
            }

            DrawUtil.drawPlayButton(this,
                    font, width, height,
                    new int[]{255, 255, 255, (int) this.opacity},
                    new int[]{255, 255, 255, (int) this.opacity}
            );

            if (playButtonClicked) {
                if (opacity <= 0) {
                    mainFadeOut = true;
                    opacity = 1;
                } else {
                    opacity -= PI * atan(opacity);
                }
            }
        }

        if (mainFadeOut) {
            DrawUtil.drawBoardLines(this,
                    width, SIZE, LINE_START_Y,
                    new int[]{255, 255, 255, (int) this.opacity}
            );

            if (!mainFadeIn) {
                if (opacity >= 255) {
                    opacity = 255;
                    mainFadeIn = true;

                    if (this.game.getMode() == GameMode.AI) {
                        switch (this.level) {
                            case 1:
                                this.game.setAlgorithm(new Random());
                                break;
                            case 2:
                                this.game.setAlgorithm(new Minimax(this.game.getBoard().getTurn(), Double.POSITIVE_INFINITY));
                                break;
                            case 3:
                                this.game.setAlgorithm(new AlphaBetaPruning(this.game.getBoard().getTurn(), Double.POSITIVE_INFINITY));
                                break;
                            case 4:
                                this.game.setAlgorithm(new AlphaBetaAdvanced(this.game.getBoard().getTurn(), Double.POSITIVE_INFINITY));
                                break;
                        }
                    }
                } else {
                    opacity += 2 * PI * atan(opacity);
                }
            }

            if (mainFadeIn) {
                this.game.getBoard().drawBoard(this);

                if (this.game.getBoard().isGameOver()) {
                    String text = "Game over! %s!";

                    State state = this.game.getBoard().getWinner();

                    if (state == State.BLANK) {
                        text = String.format(text, "Draw");
                    } else if (this.game.getMode() == GameMode.AI) {
                        text = String.format(text, state == State.CIRCLE ? "AI won" : "You won");
                    } else {
                        text = String.format(text, TextUtil.capitalize(state.name()) + " won");
                    }

                    DrawUtil.drawGameOverText(this,
                            font, text, width, LINE_START_Y,
                            new int[]{255, 255, 255}
                    );

                    DrawUtil.drawNewGameButton(this,
                            font, width, LINE_START_Y,
                            new int[]{255, 255, 255}
                    );
                }
            }
        }
    }

    @Override
    public void mousePressed() {
        if (!mainFadeOut) {
            int playMin = (width / 2) - 100, playMax = playMin + 200, playMinY = (height / 2) + 120, playMaxY = playMinY + 50;
            int levelSelectMinY = (height / 2) + 70, levelSelectMaxY = levelSelectMinY + 30;
            int modeSelectMinY = height / 2, modeSelectMaxY = modeSelectMinY + 50;
            int playerMin = (width / 2) - 225, playerMax = playerMin + 200;
            int aiMin = (width / 2) + 25, aiMax = aiMin + 200;

            if (mouseY >= modeSelectMinY && mouseY <= modeSelectMaxY) {
                if (mouseX >= aiMin && mouseX <= aiMax) {
                    this.game.setMode(GameMode.AI);
                } else if (mouseX >= playerMin && mouseX <= playerMax) {
                    this.game.setMode(GameMode.PLAYER);
                }
            }

            if (mouseY >= playMinY && mouseY <= playMaxY && mouseX >= playMin && mouseX <= playMax) {
                if ((this.game.getMode() == GameMode.AI && this.level != 0) || (this.game.getMode() == GameMode.PLAYER)) {
                    this.playButtonClicked = true;
                }
            }

            if (this.game.getMode() == GameMode.AI && mouseY >= levelSelectMinY && mouseY <= levelSelectMaxY) {
                if (mouseX >= (width / 2F) + 25 && mouseX <= (width / 2F) + 25 + 45) this.level = 1;
                if (mouseX >= (width / 2F) + 75 && mouseX <= (width / 2F) + 75 + 45) this.level = 2;
                if (mouseX >= (width / 2F) + 125 && mouseX <= (width / 2F) + 125 + 45) this.level = 3;
                if (mouseX >= (width / 2F) + 175 && mouseX <= (width / 2F) + 175 + 45) this.level = 4;
            }
        }

        if (this.mainFadeIn && !this.game.getBoard().isGameOver()) {
            this.game.getBoard().handleClick(mouseX, mouseY);
        }

        if (this.game != null && this.game.getBoard() != null && this.game.getBoard().isGameOver()) {
            float newXMin = (width / 2F) - 50, newXMax = newXMin + 100;
            float newYMin = LINE_START_Y - 40, newYMax = newYMin + 30;

            if (mouseX >= newXMin && mouseX <= newXMax && mouseY >= newYMin && mouseY <= newYMax) {
                this.game = new Game(GameMode.NOT_DEFINED);
                this.playButtonClicked = false;
                this.mainFadeOut = false;
                this.mainFadeIn = false;
                this.opacity = 255;
            }
        }
    }

    public Game getGame() {
        return this.game;
    }

    public static Main getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        String[] appletArgs = {"net.yetrr.tictactoe.Main"};

        if (args != null) {
            PApplet.main(concat(appletArgs, args));
        } else {
            PApplet.main(appletArgs);
        }
    }

}
