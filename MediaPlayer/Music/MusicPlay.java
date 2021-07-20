package MediaPlayer.Music;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @Author 宋佳俊
 * @create 2021/7/13 15:35
 */
public class MusicPlay {
    public Music music;
    private Player player;

    public MusicPlay(Music music){
        this.music = music;
    }

    public void play(){
        try{
            /*InputStream is = MusicPlay.class.
                    getClassLoader().
                    getResourceAsStream(music.getMusicPath());
            */
            InputStream is = new FileInputStream(music.getMusicPath());
            player = new Player(is);
            player.play();
        } catch (JavaLayerException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
