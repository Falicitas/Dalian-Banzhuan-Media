package MediaPlayer.Music;

/**
 * @Author 宋佳俊
 * @create 2021/7/12 22:24
 */
public class MusicPlayThread extends Thread {
    public static final Object lock = new Object();
    private Music music;
    public boolean isSuspend = false;

    public MusicPlayThread() {

    }

    public MusicPlayThread(Music music) {
        this.music = music;
    }

    public Music getMusic(){
        return music;
    }
    @Override
    public void run() {
        while(true){
            MusicPlay mp3 = new MusicPlay(music);
            mp3.play();
        }
    }

    public void setMusic(Music music) {
        this.music = music;
    }
}
