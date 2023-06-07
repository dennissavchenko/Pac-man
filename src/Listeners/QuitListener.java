package Listeners;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class QuitListener implements KeyListener {

    private JFrame frame;

    public QuitListener(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.isControlDown() && e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_Q) {
            frame.dispose();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
