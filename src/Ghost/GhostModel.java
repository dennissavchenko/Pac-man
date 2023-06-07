package Ghost;

import Coins.CoinsController;
import Coins.CoinsModel;
import Lives.LivesController;
import Lives.LivesModel;
import Map.MapGenerator;
import Pacman.PacmanController;
import Pacman.PacmanModel;
import Score.ScoreController;
import Score.ScoreModel;

import javax.swing.*;
import java.awt.*;

public class GhostModel {

    private GhostView view;

    private final PacmanModel pacmanModel;

    private final CoinsModel coinsModel;

    private Thread superPower;

    private Thread moving;

    private Thread animation;

    private int side;

    private int speed;

    private int rash = 1;

    private boolean move = true;

    private final String colour;

    private static int ID = 0;

    protected int id;

    private int x;

    private int y;

    private int r;

    private int c;

    private int cooConst;

    private final JLabel label;

    private int direction = 4;

    public int getR() {
        return r;
    }

    public int getC() {
        return c;
    }

    public static void setID(int ID) {
        GhostModel.ID = ID;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public JLabel getLabel() {
        return label;
    }

    public String getColour() {
        return colour;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public GhostView getView() {
        return view;
    }

    public void setView(GhostView view) {
        this.view = view;
    }

    public void startMoving() {
        moving = new Thread(() ->  {
            while (!Thread.currentThread().isInterrupted()) {
                r = y / side;
                c = x / side;
                if((int)(Math.random() * 10) == 9 && x % side == cooConst && y % side == cooConst) changeDirection();
                if(r == 0 && direction == 4) y = (MapGenerator.getData().length * side) - cooConst;
                else if(r == MapGenerator.getData().length - 1 && direction == 2) y = 0;
                else if(c == 0 && direction == 3) x = (MapGenerator.getData()[0].length * side) - cooConst;
                else if(c == MapGenerator.getData()[0].length - 1 && direction == 1) x = 0;
                else {
                    switch (direction) {
                        case 1 -> {
                            if (MapGenerator.getData()[r][c + 1] == "W" && x % side == cooConst) changeDirection();
                            else x += cooConst;
                        }
                        case 2 -> {
                            if (MapGenerator.getData()[r + 1][c] == "W" && y % side == cooConst) changeDirection();
                            else y += cooConst;
                        }
                        case 3 -> {
                            if (MapGenerator.getData()[r][c - 1] == "W" && x % side == cooConst) changeDirection();
                            else x -= cooConst;
                        }
                        case 4 -> {
                            if (MapGenerator.getData()[r - 1][c] == "W" && y % side == cooConst) changeDirection();
                            else y -= cooConst;
                        }
                    }
                }
                label.setLocation(x, y);
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        moving.start();
    }

    public void startAnimation() {
        animation = new Thread(() ->  {
            while (!Thread.currentThread().isInterrupted()) {
                String path;
                if(pacmanModel.getRash()) {
                    path = "./Images/";
                    if(rash <= 5) path += "dark/dark";
                    else path += "white/white";
                    if(rash % 2 == 0) path += "_rush_move.png";
                    else path += "_rush_stand.png";
                    if(rash < 10) rash++;
                    else rash = 1;
                } else {
                    path = "./Images/" + colour + "/" + colour;
                    switch (direction) {
                        case 1 -> path += "_right";
                        case 2 -> path += "_down";
                        case 3 -> path += "_left";
                        case 4 -> path += "_up";
                    }
                    if(move) {
                        path += "_move.png";
                        move = false;
                    } else {
                        path += "_stand.png";
                        move = true;
                    }}
                ImageIcon icon = new ImageIcon(path);
                Image image = icon.getImage();
                Image scaledImage = image.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
                icon = new ImageIcon(scaledImage);
                label.setIcon(icon);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        animation.start();
    }

    public void startSuperPower() {
        superPower = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                if((int)(Math.random() * 4) == 3) {
                    r = y / side;
                    c = x / side;
                    boolean inCave = false;
                    for (int[] ghost : MapGenerator.getLocation()) {
                        if ((ghost[0] == r || ghost[0] - 1 == r) && ghost[1] == c) {
                            inCave = true;
                            break;
                        }
                    }
                    if(!inCave) {
                        if(coinsModel.getCoinsType()[r][c] != 2) {
                            switch ((int) (Math.random() * 5)) {
                                case 0 -> {
                                    coinsModel.setCoinsType(r, c, 3);
                                    setSuperPower("./Images/super/speed_up.png");
                                }
                                case 1 -> {
                                    coinsModel.setCoinsType(r, c, 4);
                                    setSuperPower("./Images/super/2x.png");
                                }
                                case 2 -> {
                                    coinsModel.setCoinsType(r, c, 5);
                                    setSuperPower("./Images/super/slow_down.png");
                                }
                                case 3 -> {
                                    coinsModel.setCoinsType(r, c, 6);
                                    setSuperPower("./Images/super/cave.png");
                                }
                                case 4 -> {
                                    coinsModel.setCoinsType(r, c, 7);
                                    setSuperPower("./Images/super/life.png");
                                }
                            }
                        }
                    }
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        superPower.start();
    }

    public void refresh(int side) {
        this.side = side;
        cooConst = side / 6;
        y = cooConst + side * r;
        x = cooConst + side * c;
        label.setSize(side * 2 / 3, side * 2 / 3);
    }

    public void cave() {
        Thread cave = new Thread(() -> {
            MapGenerator.lock();
            x = side * MapGenerator.getLocation()[id][1] + side / 6;
            y = side * MapGenerator.getLocation()[id][0] + side / 6;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            MapGenerator.unlock();
        });
        cave.start();
    }

    public void putInCave() {
        x = side * MapGenerator.getLocation()[id][1] + side / 6;
        y = side * MapGenerator.getLocation()[id][0] + side / 6;
    }

    private void changeDirection() {
        int newDirection = (int)(Math.random() * 4 + 1);
        while (newDirection == direction) newDirection = (int)(Math.random() * 4 + 1);
        direction = newDirection;
    }

    public void kill() {
        moving.interrupt();
        animation.interrupt();
        superPower.interrupt();
    }

    public GhostModel(int side, PacmanController pacmanController, CoinsController coinsController) {

        pacmanModel = pacmanController.getModel();

        coinsModel = coinsController.getModel();

        this.speed = pacmanModel.getLevelSpeed() + 10;

        this.id = ID++;

        label = new JLabel();
        label.setSize(side * 2 / 3, side * 2 / 3);

        String[] colours = {"pink", "blue", "orange", "red"};
        if(MapGenerator.getLocation().length == 4) colour = colours[id];
        else colour = colours[(int)(Math.random() * colours.length)];

        this.side = side;
        x = side * MapGenerator.getLocation()[id][1] + side / 6;
        y = side * MapGenerator.getLocation()[id][0] + side / 6;
        cooConst = side / 6;

        startMoving();
        startSuperPower();
        startAnimation();

    }

    private void setSuperPower(String path) {
        coinsModel.getCoins()[r][c].setSize(pacmanModel.getSide() * 2 / 3, pacmanModel.getSide() * 2 / 3);
        ImageIcon icon = new ImageIcon(path);
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(coinsModel.getCoins()[r][c].getWidth(), coinsModel.getCoins()[r][c].getHeight(), Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledImage);
        coinsModel.getCoins()[r][c].setText("");
        coinsModel.getCoins()[r][c].setIcon(icon);
    }

}
