package MediaPlayer.CenterBlock;

import javax.swing.*;
import java.awt.*;

/**
 * @author Zhejian Lai
 * @Description //TODO
 * @date 2021-07-07-22:52
 */
public class CenterPanel extends JPanel {

    private static final int DEFAULT_WIDTH = 850;
    private static final int DEFAULT_HEIGHT = 480;

    public CenterPanel() {
        setBounds(170,70,DEFAULT_WIDTH,DEFAULT_HEIGHT);
        //add(new RatingPanel("Test排行榜",null));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(new ImageIcon("src/MediaPlayer/res/background.jpg").getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}
