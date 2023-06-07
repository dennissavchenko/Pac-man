package Listeners;

import Customs.CustomFont;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ButtonMouseListener implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        button.setFont(new Font(CustomFont.getCustomFont("semi-bold.ttf"), Font.BOLD, 12));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        button.setFont(new Font(CustomFont.getCustomFont("semi-bold.ttf"), Font.PLAIN, 12));
    }

}
