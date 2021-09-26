package MediaPlayer.PlayerPanel;

import MediaPlayer.Music.Music;
import MediaPlayer.Music.MusicPlayThread;

import javax.swing.*;
import java.awt.event.*;

/**
 * @Author 宋佳俊
 * @create 2021/7/12 15:57
 */

//歌曲按钮，用于定位歌曲，并双击播放
public class MusicButton extends JButton {
    private Music music;

    public MusicButton(Music music) {
        this.music = music;
        setFocusPainted(false);
        setContentAreaFilled(false);
        this.addActionListener(e -> music.play());
    }
}
