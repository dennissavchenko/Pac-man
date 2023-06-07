import Customs.CustomFont;
import Game.GameController;
import Game.GameModel;
import Game.GameView;
import Listeners.ButtonMouseListener;
import Listeners.DisposeActionListener;
import Listeners.SliderChangeListener;

import javax.swing.*;
import java.awt.*;

public class NewGame extends JFrame {
    public NewGame() {

        Font semiBold = new Font(CustomFont.getCustomFont("semi-bold.ttf"), Font.PLAIN, 12);
        Font regular = new Font(CustomFont.getCustomFont("regular.ttf"), Font.BOLD, 9);

        JPanel labels = new JPanel();
        labels.setLayout(new BoxLayout(labels, BoxLayout.Y_AXIS));
        JPanel sliders = new JPanel();
        sliders.setLayout(new BoxLayout(sliders, BoxLayout.Y_AXIS));
        JPanel values = new JPanel();
        values.setLayout(new BoxLayout(values, BoxLayout.Y_AXIS));

        JLabel label1 = new JLabel("Choose number of rows: ");

        label1.setFont(semiBold);
        label1.setForeground(Color.WHITE);

        labels.add(label1);
        labels.add(Box.createVerticalStrut(20));

        JSlider slider1 = new JSlider(JSlider.HORIZONTAL, 10, 100, 95);
        slider1.setMajorTickSpacing(10);
        slider1.setMinorTickSpacing(5);
        slider1.setPaintTicks(true);
        slider1.setPaintLabels(true);
        slider1.setLabelTable(slider1.createStandardLabels(20));
        slider1.setForeground(Color.white);
        slider1.setFont(regular);

        JLabel value1 = new JLabel(String.valueOf(slider1.getValue()));

        value1.setFont(semiBold);
        value1.setForeground(Color.WHITE);

        values.add(value1);
        values.add(Box.createVerticalStrut(20));

        slider1.addChangeListener(new SliderChangeListener(value1, slider1));

        sliders.add(slider1);

        JLabel label2 = new JLabel("Choose number of columns: ");

        label2.setFont(semiBold);
        label2.setForeground(Color.WHITE);

        labels.add(label2);

        JSlider slider2 = new JSlider(JSlider.HORIZONTAL, 10, 100, 95);
        slider2.setMajorTickSpacing(10);
        slider2.setMinorTickSpacing(5);
        slider2.setPaintTicks(true);
        slider2.setPaintLabels(true);
        slider2.setLabelTable(slider2.createStandardLabels(20));
        slider2.setForeground(Color.white);
        slider2.setFont(regular);

        sliders.add(slider2);

        JLabel value2 = new JLabel(String.valueOf(slider2.getValue()));

        value2.setFont(semiBold);
        value2.setForeground(Color.WHITE);

        slider2.addChangeListener(new SliderChangeListener(value2, slider2));

        values.add(value2);

        JPanel panelCenter = new JPanel();

        panelCenter.setLayout(new FlowLayout());

        panelCenter.setBackground(Color.BLACK);
        sliders.setBackground(Color.BLACK);
        values.setBackground(Color.BLACK);
        labels.setBackground(Color.BLACK);

        panelCenter.add(Box.createHorizontalStrut(10));
        panelCenter.add(labels);
        panelCenter.add(sliders);
        panelCenter.add(values);
        panelCenter.add(Box.createHorizontalStrut(10));

        JPanel panelButtons = new JPanel();

        panelButtons.setBackground(Color.BLACK);

        JButton cancel = new JButton("Cancel");
        JButton confirm = new JButton("Confirm");

        cancel.setFont(semiBold);
        confirm.setFont(semiBold);

        cancel.addActionListener(new DisposeActionListener(this));

        panelButtons.add(cancel);
        panelButtons.add(confirm);

        cancel.setOpaque(true);
        cancel.setBorderPainted(false);
        cancel.setForeground(Color.white);
        cancel.setBackground(Color.BLACK);

        cancel.addMouseListener(new ButtonMouseListener());
        confirm.addMouseListener(new ButtonMouseListener());

        cancel.setFocusPainted(false);
        confirm.setFocusPainted(false);

        confirm.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                GameModel gameModel = new GameModel(slider1.getValue(), slider2.getValue());
                GameView gameView = new GameView(gameModel);
                new GameController(gameModel, gameView);
            });
            dispose();
        });

        panelButtons.setLayout(new FlowLayout(FlowLayout.RIGHT));

        setLayout(new BorderLayout());

        add(panelCenter, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);

        pack();
        setBackground(Color.BLACK);
        setTitle("Pacman — New Game");
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}