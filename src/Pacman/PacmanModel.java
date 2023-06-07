package Pacman;

import Coins.CoinsController;
import Coins.CoinsModel;
import Game.GameModel;
import Ghost.GhostModel;
import Map.MapGenerator;
import Score.ScoreController;
import Score.ScoreModel;

import java.nio.file.FileAlreadyExistsException;

public class PacmanModel {


    private int levelSpeed;

    private int rashPoints;

    private int speed;

    private int twice;

    private final GameModel gameModel;

    private final ScoreModel scoreModel;

    private Thread newLevelCheck;

    private Thread moving;

    private Thread eating;

    private Thread coins;

    private Thread collision;

    private Thread death;

    private boolean rash;

    private PacmanView view;

    private final CoinsModel coinsModel;

    private boolean isDead;

    private boolean isOpening;

    private int angle;

    private int constAngle;

    private int arc;

    private int side;

    private int cooConst;

    private int x;

    private int y;

    private int r;

    private int c;

    private int direction;

    public int getLevelSpeed() {
        return levelSpeed;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isDead() {
        return isDead;
    }

    public boolean getRash() {
        return rash;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getAngle() {
        return angle;
    }

    public int getArc() {
        return arc;
    }

    public int getSide() {
        return side;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setView(PacmanView view) {
        this.view = view;
    }

    public void startPacman() {
        x = side + side / 6;
        y = side + side / 6;
        r = 1;
        c = 1;
        direction = 1;
        angle = 45;
        arc = 270;
        constAngle = angle;
        isOpening = true;
        isDead = false;
    }

    public void startMoving() {
        moving = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            while (!Thread.currentThread().isInterrupted()) {
                r = y / side;
                c = x / side;
                if(r < MapGenerator.getData().length && c < MapGenerator.getData()[0].length) {
                    if (r == 0 && direction == 4) y = (MapGenerator.getData().length * side) - cooConst;
                    else if (r == MapGenerator.getData().length - 1 && direction == 2) y = 0;
                    else if (c == 0 && direction == 3) x = (MapGenerator.getData()[0].length * side) - cooConst;
                    else if (c == MapGenerator.getData()[0].length - 1 && direction == 1) x = 0;
                    else {
                        switch (direction) {
                            case 1 -> {
                                if (!(MapGenerator.getData()[r][c + 1] == "W" && x % side == cooConst)) x += cooConst;
                            }
                            case 2 -> {
                                if (!((MapGenerator.getData()[r + 1][c] == "W" || MapGenerator.getData()[r + 1][c] == "G") && y % side == cooConst))
                                    y += cooConst;
                            }
                            case 3 -> {
                                if (!(MapGenerator.getData()[r][c - 1] == "W" && x % side == cooConst)) x -= cooConst;
                            }
                            case 4 -> {
                                if (!(MapGenerator.getData()[r - 1][c] == "W" && y % side == cooConst)) y -= cooConst;
                            }
                        }
                    }
                }
                view.repaint();
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        moving.start();
    }

    public void startEating() {
        eating = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                int angleIncrement = 15;
                if (isOpening) {
                    angle += angleIncrement;
                    arc -= angleIncrement * 2;
                    if (angle >= constAngle) {
                        isOpening = false;
                    }
                } else {
                    angle -= angleIncrement;
                    arc += angleIncrement * 2;
                    if (angle <= constAngle - 45) {
                        isOpening = true;
                    }
                }
                if(view != null) view.repaint();
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        eating.start();
    }

    public void startCollision() {
        collision = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                    for (int i = 0; gameModel.getGhostModels() != null && i < gameModel.getGhostModels().length; i++) {
                        if(gameModel.getGhostModels()[i] != null && gameModel.getGhostModels()[i].getR() == r && gameModel.getGhostModels()[i].getC() == c) {
                            if (rash) {
                                gameModel.getGhostModels()[i].putInCave();
                                scoreModel.setScore(200 * rashPoints);
                                rashPoints *= 2;
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                }
                            } else {
                                isDead = true;
                                if (gameModel.getLivesModel().getLife() != 0) {
                                    Thread.currentThread().interrupt();
                                    gameModel.getLivesModel().death();
                                    death();
                                }
                            }
                        }
                    }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        collision.start();
    }

    public void startCoins() {
        coins = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            while (!Thread.currentThread().isInterrupted()) {
                int row = y / side;
                int column = x / side;
                if(row < coinsModel.getCoinsType().length && column < coinsModel.getCoinsType()[row].length && coinsModel.getCoinsType()[row][column] != 0) {
                    switch (coinsModel.getCoinsType()[row][column]) {
                        case 1 -> scoreModel.setScore(10 * twice);
                        case 2 -> {
                            scoreModel.setScore(50 * twice);
                            if (!rash) {
                                new Thread(() -> {
                                    MapGenerator.lock();
                                    rashPoints = 1;
                                    rash = true;
                                    try {
                                        Thread.sleep(10000);
                                    } catch (InterruptedException e) {
                                        Thread.currentThread().interrupt();
                                    }
                                    rash = false;
                                    MapGenerator.unlock();
                                }).start();
                            }
                        }
                        case 3 -> new Thread(() -> {
                            speed -= speed % 2 == 0 ? speed / 2 : speed / 2 + 1;
                            try {
                                Thread.sleep(10000);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                            speed = levelSpeed;
                        }).start();
                        case 4 -> new Thread(() -> {
                            twice *= 2;
                            try {
                                Thread.sleep(10000);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                            twice = 1;
                        }).start();
                        case 5 -> new Thread(() -> {
                            int tmp = gameModel.getGhostModels()[0].getSpeed();
                            for(GhostModel ghost : gameModel.getGhostModels()) {
                                ghost.setSpeed(tmp + (tmp % 2 == 0 ? tmp / 2 : tmp / 2 + 1));
                            }
                            try {
                                Thread.sleep(10000);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                            for(int i = 0; gameModel.getGhostModels() != null && i < gameModel.getGhostModels().length; i++) {
                                gameModel.getGhostModels()[i].setSpeed(levelSpeed + 10);
                            }
                        }).start();
                        case 6 -> {
                            if(!rash) {
                                for(GhostModel ghostModel : gameModel.getGhostModels()) ghostModel.cave();
                            }
                        }
                        case 7 -> gameModel.getLivesModel().newLife();
                    }
                    coinsModel.getCoins()[row][column].setText("");
                    coinsModel.getCoins()[row][column].setIcon(null);
                    coinsModel.setCoinsType(row, column, 0);
                    try {
                        Thread.sleep(speed);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
        coins.start();
    }

    public void startDeath() {
        death = new Thread(() -> {
            int angleIncrement = 15;
            angle = 135;
            arc = 270;
            while (arc > 0) {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                angle += angleIncrement;
                arc -= angleIncrement * 2;
                view.repaint();
            }
            if(gameModel.getLivesModel().getLife() != 0) {
                gameModel.showGhosts();
                startMoving();
                startEating();
                startCoins();
                startCollision();
                startNewLevelCheck();
                startPacman();
            }
        });
        death.start();
    }

    public void startNewLevelCheck() {
        newLevelCheck = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                boolean noCoins = true;
                for (int i = 0; i < coinsModel.getCoinsType().length && noCoins; i++) {
                    for (int j = 0; j < coinsModel.getCoinsType()[i].length; j++) {
                        if (coinsModel.getCoinsType()[i][j] == 1 || coinsModel.getCoinsType()[i][j] == 2) {
                            noCoins = false;
                            break;
                        }
                    }
                }
                if(noCoins) {
                    moving.interrupt();
                    eating.interrupt();
                    coins.interrupt();
                    gameModel.newLevel();
                    Thread.currentThread().interrupt();
                    if(levelSpeed > 30) levelSpeed -= 10;
                    speed = levelSpeed;
                    startPacman();
                    startMoving();
                    startEating();
                    startCoins();
                    startNewLevelCheck();
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        newLevelCheck.start();
    }

    public void refresh(int side) {
        this.side = side;
        cooConst = side / 6;
        y = cooConst + side * r;
        x = cooConst + side * c;
        view.repaint();
    }

    public void death() {
        moving.interrupt();
        eating.interrupt();
        coins.interrupt();
        newLevelCheck.interrupt();
        gameModel.death();
        startDeath();
    }

    public void updateDirection(int direction) {
        switch (direction) {
            case 1 -> {
                angle = 45;
                constAngle = angle;
                arc = 270;
            }
            case 2 -> {
                angle = 315;
                constAngle = angle;
                arc = 270;
            }
            case 3 -> {
                angle = 225;
                constAngle = angle;
                arc = 270;
            }
            case 4 -> {
                angle = 135;
                constAngle = angle;
                arc = 270;
            }
        }
    }

    public int getCoordinate(int coordinate, int direction) {
        int r = (coordinate + cooConst) / side;
        int c = (coordinate + cooConst) / side;
        if(direction == 1 || direction == 3) return r * side + cooConst;
        else return c * side + cooConst;
    }

    public void kill() {
        moving.interrupt();
        eating.interrupt();
        coins.interrupt();
        collision.interrupt();
    }


    public PacmanModel(int side, CoinsController coinsController, GameModel gameModel, ScoreController scoreController) {
        this.side = side;
        coinsModel = coinsController.getModel();
        scoreModel = scoreController.getModel();
        this.gameModel = gameModel;
        x = side + side / 6;
        y = side + side / 6;
        cooConst = side / 6;
        r = 1;
        c = 1;
        twice = 1;
        rash = false;
        angle = 45;
        arc = 270;
        levelSpeed = 60;
        speed = levelSpeed;
        constAngle = angle;
        isOpening = true;
        direction = 1;
        startMoving();
        startEating();
        startCoins();
        startCollision();
        startNewLevelCheck();
    }
}

