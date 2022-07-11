package src.com.frame;

import javax.swing.*;

public class Main extends JFrame {

    public static void main(String[] args) {
	    JFrame window = new JFrame();

        ImageIcon icon = new ImageIcon("res/icon.svg");

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Predator Within");

        window.setIconImage(icon.getImage());

        Panel panel = new Panel();
        window.add(panel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        panel.startGameThread();
    }
}
