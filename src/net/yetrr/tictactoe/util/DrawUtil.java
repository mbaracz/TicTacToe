package net.yetrr.tictactoe.util;

import net.yetrr.tictactoe.game.GameMode;
import processing.core.PApplet;
import processing.core.PFont;

import java.util.function.Consumer;

import static processing.core.PConstants.CENTER;

public class DrawUtil {

    public static void drawBoardLines(PApplet applet, float width, float size, float yStart, int[] strokeRgba) {
        applet.stroke(strokeRgba[0], strokeRgba[1], strokeRgba[2], strokeRgba[3]);

        /* Vertical board lines */
        applet.line(width / 2F - (size / 2), yStart, width / 2F - (size / 2), yStart + (size * 3));
        applet.line(width / 2F + (size / 2), yStart, width / 2F + (size / 2), yStart + (size * 3));
        applet.line(width / 2F + (size * 1.5F), yStart, width / 2F + (size * 1.5F), yStart + (size * 3));
        applet.line(width / 2F - (size * 1.5F), yStart, width / 2F - (size * 1.5F), yStart + (size * 3));
        /* Vertical board lines */

        /* Horizontal board lines */
        applet.line(width / 2F - (size * 1.5F), yStart, width / 2F + (size * 1.5F), yStart);
        applet.line(width / 2F - (size * 1.5F), yStart + size, width / 2F + (size * 1.5F), yStart + size);
        applet.line(width / 2F - (size * 1.5F), yStart + size * 2, width / 2F + (size * 1.5F), yStart + size * 2);
        applet.line(width / 2F - (size * 1.5F), yStart + size * 3, width / 2F + (size * 1.5F), yStart + size * 3);
        /* Horizontal board lines */
    }

    public static void drawModeButtons(PApplet applet, GameMode mode, float width, float height, int[] selectedRgba, int[] unselectedRgba, int[] textRgba) {
        Runnable drawAiButton = () -> applet.rect((width / 2F) + 25, height / 2F, 200, 50, 36, 36, 36, 36);
        Runnable drawPlayerButton = () -> applet.rect((width / 2F) - 225, height / 2F, 200, 50, 36, 36, 36, 36);

        applet.noFill();
        if (mode == GameMode.PLAYER) {
            applet.stroke(selectedRgba[0], selectedRgba[1], selectedRgba[2], selectedRgba[3]);
            drawPlayerButton.run();
            applet.stroke(unselectedRgba[0], unselectedRgba[1], unselectedRgba[2], unselectedRgba[3]);
            drawAiButton.run();
        } else if (mode == GameMode.AI) {
            applet.stroke(selectedRgba[0], selectedRgba[1], selectedRgba[2], selectedRgba[3]);
            drawAiButton.run();
            applet.stroke(unselectedRgba[0], unselectedRgba[1], unselectedRgba[2], unselectedRgba[3]);
            drawPlayerButton.run();
        } else {
            drawAiButton.run();
            drawPlayerButton.run();
        }

        applet.fill(textRgba[0], textRgba[1], textRgba[2], textRgba[3]);
        applet.text("Player", (width / 2F) - 225 + 100, height / 2F + 32);
        applet.text("AI", (width / 2F) + 25 + 100, height / 2F + 32);
    }

    public static void drawAiSubButtons(PApplet applet, PFont font, int level, float width, float height, int[] selectedRgba, int[] unselectedRgba, int[] textRgba) {
        Consumer<Boolean> stroke = bool -> {
            if (bool) {
                applet.stroke(selectedRgba[0], selectedRgba[1], selectedRgba[2], selectedRgba[3]);
            } else {
                applet.stroke(unselectedRgba[0], unselectedRgba[1], unselectedRgba[2], unselectedRgba[3]);
            }
        };

        applet.noFill();

        stroke.accept(level == 1);
        applet.rect((width / 2F) + 25, height / 2F + 70, 45, 30, 36, 36, 36, 36);
        stroke.accept(level == 2);
        applet.rect((width / 2F) + 75, height / 2F + 70, 45, 30, 36, 36, 36, 36);
        stroke.accept(level == 3);
        applet.rect((width / 2F) + 125, height / 2F + 70, 45, 30, 36, 36, 36, 36);
        stroke.accept(level == 4);
        applet.rect((width / 2F) + 175, height / 2F + 70, 45, 30, 36, 36, 36, 36);

        applet.stroke(textRgba[0], textRgba[1], textRgba[2], textRgba[3]);
        applet.textFont(font, 16);
        applet.text("1", (width / 2F) + 47, height / 2F + 91);
        applet.text("2", (width / 2F) + 47 + 50, height / 2F + 91);
        applet.text("3", (width / 2F) + 47 + 2 * 50, height / 2F + 91);
        applet.text("4", (width / 2F) + 47 + 3 * 50, height / 2F + 91);
    }

    public static void drawPlayButton(PApplet applet, PFont font, float width, float height, int[] strokeRgba, int[] textRgba) {
        applet.noFill();
        applet.textFont(font, 20);
        applet.stroke(strokeRgba[0], strokeRgba[1], strokeRgba[2], strokeRgba[3]);
        applet.rect((width / 2F) - 100, height / 2F + 120, 200, 50, 36, 36, 36, 36);
        applet.fill(textRgba[0], textRgba[1], textRgba[2], textRgba[3]);
        applet.text("Play", (width / 2F) - 100 + 100, height / 2F + 120 + 32);
    }

    public static void drawNewGameButton(PApplet applet, PFont font, float width, float yStart, int[] strokeRgb) {
        applet.noFill();
        applet.stroke(strokeRgb[0], strokeRgb[1], strokeRgb[2]);
        applet.rect((width / 2F) - 50, yStart - 40, 100, 30, 32, 32, 32, 32);
        applet.textFont(font, 16);
        applet.textAlign(CENTER);
        applet.text("New game", (width / 2F), yStart - 20);
    }

    public static void drawApplicationTitle(PApplet applet, PFont font, String title, float width, int[] strokeRgba) {
        applet.textFont(font, 50);
        applet.fill(strokeRgba[0], strokeRgba[1], strokeRgba[2], strokeRgba[3]);
        applet.textAlign(CENTER);
        applet.text(title, width / 2F, 100);
    }

    public static void drawGameOverText(PApplet applet, PFont font, String text, float width, float yStart, int[] textRgb) {
        applet.textFont(font, 16);
        applet.stroke(textRgb[0], textRgb[1], textRgb[2]);
        applet.text(text, width / 2F, yStart - 55);
    }

    public static void drawModeText(PApplet applet, PFont font, float width) {
        applet.textFont(font, 20);
        applet.text("Select mode", width / 2F, 250);
    }

}
