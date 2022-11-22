package com.javarush.games.minesweeper;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;

import java.util.ArrayList;
import java.util.List;

public class MinesweeperGame extends Game {
    private static final int SIDE = 9;
    private static final String MINE = "\uD83D\uDCA3";
    private static final String FLAG = "\uD83D\uDEA9";
    private GameObject[][] gameField = new GameObject[SIDE][SIDE];
    private int countMinesOnField;
    private int countFlags;
    private int countClosedTiles = SIDE * SIDE;
    private boolean isGameStopped;
    private int score;

    @Override
    public void initialize() {
        setScreenSize(SIDE, SIDE);
        createGame();
    }

    @Override
    public void onMouseLeftClick(int x, int y) {
        if (isGameStopped == true) {
            restart();
            return;
        }
        openTile(x, y);
    }

    @Override
    public void onMouseRightClick(int x, int y) {markTile(x, y);}

    private void createGame() {
        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                boolean isMine = getRandomNumber(10) < 1;
                if (isMine) {
                    countMinesOnField++;
                }
                gameField[y][x] = new GameObject(x, y, isMine);
                setCellColor(x, y, Color.ORANGE);
                setCellValue(x, y, "");
            }
        }
        countMineNeighbors();
        countFlags = countMinesOnField;
    }

    private List<GameObject> getNeighbors(GameObject gameObject) {
        List<GameObject> result = new ArrayList<>();
        for (int y = gameObject.y - 1; y <= gameObject.y + 1; y++) {
            for (int x = gameObject.x - 1; x <= gameObject.x + 1; x++) {
                if (y < 0 || y >= SIDE) {
                    continue;
                }
                if (x < 0 || x >= SIDE) {
                    continue;
                }
                if (gameField[y][x] == gameObject) {
                    continue;
                }
                result.add(gameField[y][x]);
            }
        }
        return result;
    }

    private void countMineNeighbors() {
        for (int j = 0; j < SIDE; j++) {
            for (int i = 0; i < SIDE; i++) {
                if (!gameField[i][j].isMine) {
                    for (GameObject res : getNeighbors(gameField[i][j])) {
                        if (res.isMine) {
                            gameField[i][j].countMineNeighbors++;
                        }
                    }
                }
            }
        }
    }

    private void openTile(int x, int y) {
        if (gameField[y][x].isOpen || gameField[y][x].isFlag || isGameStopped) {return;}

        gameField[y][x].isOpen = true;
        setCellColor(x, y, Color.GREEN);
        countClosedTiles--;

        if (gameField[y][x].isMine) {
            setCellValueEx(x, y, Color.RED, MINE);
            gameOver();
        } else if (gameField[y][x].countMineNeighbors == 0) {
            setCellValue(x, y, "");
            for ( GameObject obj : getNeighbors(gameField[y][x])) {
                if (!obj.isOpen) {
                    openTile(obj.x, obj.y);
                }
            }
        } else {
            setCellNumber(x, y, gameField[y][x].countMineNeighbors);
        }

        if (countClosedTiles == countMinesOnField && gameField[y][x].isMine == false) {
            win();
        }

        if (gameField[y][x].isOpen == true && gameField[y][x].isMine == false) {
            score += 5;
        }

        setScore(score);
    }

    private void markTile(int x, int y) {
        if (isGameStopped || gameField[y][x].isOpen || (countFlags == 0 && !gameField[y][x].isFlag)) {
            return;
        }

        if (gameField[y][x].isFlag) {
            countFlags++;
            gameField[y][x].isFlag = false;
            setCellValue(x, y, "");
            setCellColor(x, y, Color.ORANGE);
        } else {
            countFlags--;
            gameField[y][x].isFlag = true;
            setCellValue(x, y, FLAG);
            setCellColor(x, y, Color.YELLOW);
        }
    }

    private void gameOver() {
        isGameStopped = true;
        showMessageDialog(Color.WHITE, "You Lose", Color.BLACK, 50 );
    }

    private void win() {
        showMessageDialog(Color.WHITE, "You Win", Color.BLACK, 50 );
        isGameStopped = true;
    }

    private void restart() {
        countClosedTiles = SIDE * SIDE;
        score = 0;
        countMinesOnField = 0;
        setScore(score);
        isGameStopped = false;
        createGame();
    }
}