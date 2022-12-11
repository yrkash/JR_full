package com.javarush.task.task35.task3513_2048;

import java.util.*;
import java.util.stream.Collectors;

public class Model {

    private static final int FIELD_WIDTH = 4;
    public Tile[][] gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];

    int score;
    int maxTile;

    private Stack<Tile[][]> previousStates = new Stack<>();
    private Stack<Integer> previousScores = new Stack<>();

    private boolean isSaveNeeded = true;

    public void autoMove() {
        PriorityQueue<MoveEfficiency> queue =
                new PriorityQueue<>(4 , Collections.reverseOrder());
        Move leftMove = new Move() {
            @Override
            public void move() {
                left();
            }
        };
        queue.offer(getMoveEfficiency(leftMove));
        queue.offer(getMoveEfficiency(this::right));
        queue.offer(getMoveEfficiency(()->up()));
        queue.offer(getMoveEfficiency(this::down));
        queue.peek().getMove().move();

    }

    public boolean hasBoardChanged() {
        boolean boardChanged = false;
        Tile[][] testTiles = previousStates.peek();
        for (int x = 0; x < FIELD_WIDTH; x++) {
            for (int y = 0; y < FIELD_WIDTH; y++) {
                if (testTiles[x][y].value != gameTiles[x][y].value) {
                    boardChanged = true;
                    break;
                }
            }
        }
        return boardChanged;
    }

    public MoveEfficiency getMoveEfficiency(Move move) {
        move.move();
        MoveEfficiency moveEfficiency =
                new MoveEfficiency(getEmptyTiles().size(),score,move);
        if (!hasBoardChanged()) return new MoveEfficiency(-1,0, move);
        rollback();
        return moveEfficiency;
    }


    private void saveState(Tile[][] tiles) {
        Tile[][] saveTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int x = 0; x < FIELD_WIDTH; x++) {
            for (int y = 0; y < FIELD_WIDTH; y++) {
                saveTiles[x][y] = new Tile(gameTiles[x][y].value);
            }
        }
        previousStates.push(saveTiles);
        previousScores.push(score);
        isSaveNeeded = false;
    }

    public void rollback() {
        if (!previousStates.isEmpty()) gameTiles = previousStates.pop();
        if (!previousScores.isEmpty()) score = previousScores.pop();
    }

    public Tile[][] getGameTiles() {
        return gameTiles;
    }

    private int getEmptyTilesCount() {
        return getEmptyTiles().size();
    }

    private boolean isFull() {
        return getEmptyTilesCount() == 0;
    }

    public void randomMove() {
        int n = ((int)(Math.random() * 100)) % 4;
        switch (n) {
            case 0:
                left();
                break;
            case 1:
                right();
                break;
            case 2:
                up();
                break;
            case 3:
                down();
                break;
        }
    }

    boolean canMove() {
        if (!isFull()) {
            return true;
        }

        for (int x = 0; x < FIELD_WIDTH; x++) {
            for (int y = 0; y < FIELD_WIDTH; y++) {
                Tile t = gameTiles[x][y];
                if ((x < FIELD_WIDTH - 1 && t.value == gameTiles[x + 1][y].value)
                        || ((y < FIELD_WIDTH - 1) && t.value == gameTiles[x][y + 1].value)) {
                    return true;
                }
            }
        }
        return false;
    }


    public Model() {
        resetGameTiles();
    }

    private boolean compressTiles(Tile[] tiles) {
        int insertPosition = 0;
        boolean result = false;
        for (int i = 0; i < FIELD_WIDTH; i++) {
            if (!tiles[i].isEmpty()) {
                if (i != insertPosition) {
                    tiles[insertPosition] = tiles[i];
                    tiles[i] = new Tile();
                    result = true;
                }
                insertPosition++;
            }
        }
        return result;
    }

    private boolean mergeTiles(Tile[] tiles) {
        boolean isChanged = false;
        for (int i = 0; i < tiles.length - 1; i++) {
            if (tiles[i].value == tiles[i + 1].value && tiles[i].value != 0) {
                isChanged = true;
                int updatedValue = tiles[i].value * 2;

                if (maxTile < updatedValue) maxTile = updatedValue;
                score += updatedValue;
                tiles[i].value *= 2;
                tiles[i + 1].value = 0;
            }
        }
        compressTiles(tiles);
        return isChanged;
    }

    public void rotation () {

        Tile[][] result = new Tile [FIELD_WIDTH][FIELD_WIDTH];

        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                result[i][j] = gameTiles[FIELD_WIDTH - j - 1][i];
            }
        }
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                gameTiles[i][j] = result[i][j];
            }
        }
    }

    public void left() {
        if (isSaveNeeded) saveState(gameTiles);
        boolean moveFlag = false;
        for (int i = 0; i < FIELD_WIDTH; i++) {
            if (compressTiles(gameTiles[i]) | mergeTiles(gameTiles[i])) {
                moveFlag = true;
            }
        }
        isSaveNeeded = true;
        if (moveFlag) {
            addTile();
        }
    }

    public void right() {
        saveState(gameTiles);
        rotation();
        rotation();
        left();
        rotation();
        rotation();
    }
    public void down() {
        saveState(gameTiles);
        rotation();
        left();
        rotation();
        rotation();
        rotation();
    }
    public void up() {
        saveState(gameTiles);
        rotation();
        rotation();
        rotation();
        left();
        rotation();
    }

    private List<Tile> getEmptyTiles() {
        List<Tile> emptyTiles = Arrays.stream(gameTiles)
                .flatMap(Arrays::stream)
                .filter(Tile::isEmpty)
                .collect(Collectors.toList());
        return emptyTiles;
    }

    public void addTile() {
        List<Tile> emptyTiles = this.getEmptyTiles();
        if (emptyTiles.isEmpty()) return;
        int randomTileNumber = (int) (Math.random() * emptyTiles.size());
        int randomTileValue = (Math.random() < 0.9) ? 2 : 4;
        Tile randomTile = emptyTiles.get(randomTileNumber);
        randomTile.value = randomTileValue;
    }

    public void resetGameTiles() {

        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                gameTiles[i][j] = new Tile();
            }
        }
        addTile();
        addTile();
    }
}
