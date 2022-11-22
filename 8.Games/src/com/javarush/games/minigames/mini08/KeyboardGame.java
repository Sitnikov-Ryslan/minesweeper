package com.javarush.games.minigames.mini08;

import com.javarush.engine.cell.*;

/* 
Работа с клавиатурой
*/

public class KeyboardGame extends Game {

    @Override
    public void initialize() {
        setScreenSize(3, 3);

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                setCellColor(x, y, Color.WHITE);
            }
        }
    }

    @Override
    public void onKeyPress(Key x) {
        switch (x) {
            case UP: for (int a = 0; a < 3; a++) {setCellColor(a, 0, Color.GREEN);};
            break;
            case DOWN: for (int a = 0; a < 3; a++) {setCellColor(a, 2, Color.GREEN);};
            break;
            case LEFT: for (int a = 0; a < 3; a++) {setCellColor(0, a, Color.GREEN);};
            break;
            case RIGHT: for (int a = 0; a < 3; a++) {setCellColor(2, a, Color.GREEN);};
        }
    }

    @Override
    public void onKeyReleased(Key x) {
        if (x == Key.UP || x == Key.DOWN || x == Key.LEFT || x == Key.RIGHT) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    setCellColor(i, j, Color.WHITE);
                }
            }
        }
    }//напишите тут ваш код
}
