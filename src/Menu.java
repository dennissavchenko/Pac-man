import Customs.CustomFont;
import HighScores.HighScores;
import Listeners.ButtonMouseListener;
import Listeners.DisposeActionListener;
import Listeners.QuitListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Menu extends JFrame {
    public Menu() {

        ImageIcon icon = new ImageIcon("./Images/menu.png");

        JLabel label = new JLabel(icon);

        label.setBackground(Color.BLACK);

        JPanel panel = new JPanel();

        JButton newGame = new JButton("New Game");
        JButton highScores = new JButton("High Scores");
        JButton exit = new JButton("Exit");

        newGame.setFocusPainted(false);
        highScores.setFocusPainted(false);
        exit.setFocusPainted(false);

        Font buttonFont = new Font(CustomFont.getCustomFont("semi-bold.ttf"), Font.PLAIN, 12);

        newGame.setFont(buttonFont);
        highScores.setFont(buttonFont);
        exit.setFont(buttonFont);

        newGame.addMouseListener(new ButtonMouseListener());
        highScores.addMouseListener(new ButtonMouseListener());
        exit.addMouseListener(new ButtonMouseListener());

        JLabel logo = new JLabel("Pacman");

        logo.setFont(new Font(CustomFont.getCustomFont("semi-bold.ttf"), Font.ITALIC, 30));
        logo.setForeground(Color.white);
        logo.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel menu = new JPanel();

        menu.setLayout(new GridLayout(3,1));

        panel.setLayout(new FlowLayout());
        panel.add(newGame);
        panel.add(highScores);
        panel.add(exit);

        panel.setBackground(Color.BLACK);

        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        menu.add(logo);

        menu.add(label);

        menu.add(panel);

        exit.addActionListener(new DisposeActionListener(this));

        newGame.addActionListener(e -> SwingUtilities.invokeLater(NewGame::new));

        highScores.addActionListener(e -> SwingUtilities.invokeLater(HighScores::new));

        menu.setBackground(Color.black);

        setLayout(new BorderLayout());

        setFocusable(true);

        addKeyListener(new QuitListener(this));

        add(menu);

        pack();
        setTitle("Pacman — Menu");
        setBackground(Color.black);
        setMinimumSize(new Dimension(500, 250));
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

    }
}
