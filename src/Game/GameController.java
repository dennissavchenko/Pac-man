package Game;

import java.awt.event.*;

public class GameController {

    public GameController(GameModel model, GameView view) {

        model.setView(view);

        view.setFocusable(true);

        view.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                model.setResized(true);
                if (view.getWidth() > model.getWidth() + 6 * model.getColumns() && view.getHeight() > model.getHeight() + 6 * model.getRows() + (55 + model.getLivesView().getHeight())) {
                    model.getMapModel().setSide(model.getMapModel().getSide() + 6);
                    model.refresh();
                }
                if (view.getWidth() < model.getWidth() - 6 * model.getColumns() && view.getHeight() < model.getHeight() + 6 - model.getRows() + (55 + model.getLivesView().getHeight())) {
                    model.getMapModel().setSide(model.getMapModel().getSide() - 6);
                    model.refresh();
                }
            }
        });

        view.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {

            }

            @Override
            public void windowClosed(WindowEvent e) {
                if(!model.getKilled()) model.kill();
            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

        view.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

                if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_Q) {
                    model.kill();
                } else if(!model.getPacmanModel().isDead()) {
                    int keyCode = e.getKeyCode();
                    if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
                        model.getPacmanModel().setY(model.getPacmanModel().getCoordinate(model.getPacmanModel().getY(), model.getPacmanModel().getDirection()));
                        model.getPacmanModel().setDirection(3);
                    } else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
                        model.getPacmanModel().setY(model.getPacmanModel().getCoordinate(model.getPacmanModel().getY(), model.getPacmanModel().getDirection()));
                        model.getPacmanModel().setDirection(1);
                    } else if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
                        model.getPacmanModel().setX(model.getPacmanModel().getCoordinate(model.getPacmanModel().getX(), model.getPacmanModel().getDirection()));
                        model.getPacmanModel().setDirection(4);
                    } else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
                        model.getPacmanModel().setX(model.getPacmanModel().getCoordinate(model.getPacmanModel().getX(), model.getPacmanModel().getDirection()));
                        model.getPacmanModel().setDirection(2);
                    }
                    model.getPacmanModel().updateDirection(model.getPacmanModel().getDirection());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

    }


}
