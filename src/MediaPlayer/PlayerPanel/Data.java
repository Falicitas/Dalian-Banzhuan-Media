package MediaPlayer.PlayerPanel;

import javax.swing.*;
import java.net.URL;

/**
 * @Author 宋佳俊
 * @create 2021/7/8 11:01
 */
public class Data {
    public static URL frameIconURL = Data.class.getResource("resources/frameIcon.png");
    public static ImageIcon frameIcon = new ImageIcon(frameIconURL);

    public static URL lastSongIconURL = Data.class.getResource("resources/上一首.png");
    public static ImageIcon lastSongIcon = new ImageIcon(lastSongIconURL);

    public static URL nextSongIconURL = Data.class.getResource("resources/下一首.png");
    public static ImageIcon nextSongIcon = new ImageIcon(nextSongIconURL);

    public static  URL labaIconURL = Data.class.getResource("resources/喇叭.png");
    public static ImageIcon labaIcon = new ImageIcon(labaIconURL);

    public static  URL playListURL = Data.class.getResource("resources/播放列表.png");
    public static ImageIcon playListIcon = new ImageIcon(playListURL);

    public static  URL listCycleURL = Data.class.getResource("resources/列表循环.png");
    public static ImageIcon listCycleIcon = new ImageIcon(listCycleURL);

    public static  URL singleCycleURL = Data.class.getResource("resources/单曲循环.png");
    public static ImageIcon singleCycleIcon = new ImageIcon(singleCycleURL);

    public static  URL randomURL = Data.class.getResource("resources/随机播放.png");
    public static ImageIcon randomIcon = new ImageIcon(randomURL);

    public static  URL sequenceURL = Data.class.getResource("resources/顺序播放.png");
    public static ImageIcon sequenceIcon = new ImageIcon(sequenceURL);

    public static  URL suspendURL = Data.class.getResource("resources/暂停 停止.png");
    public static ImageIcon suspendIcon = new ImageIcon(suspendURL);

    public static  URL startURL = Data.class.getResource("resources/开始.png");
    public static ImageIcon startIcon = new ImageIcon(startURL);
}
