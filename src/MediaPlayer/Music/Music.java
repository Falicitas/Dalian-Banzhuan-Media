package MediaPlayer.Music;

import MediaPlayer.Client;
import MediaPlayer.PlayerPanel.PlayerPanel;
import javazoom.jl.player.Player;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.id3.ID3v23Frame;

import java.io.*;
import java.net.URL;

/**
 * @Author 宋佳俊
 * @create 2021/7/9 14:16
 */
// 此类对应歌曲数据库
public class Music {
    private int id;                     //音乐编号
    private String singerName;          //歌手名字
    private String album;               //专辑名
    private String songName;            //音乐名字
    private int year;

    private URL url;                    //音乐URL
    private int favor;                  //点赞量
    private int views;
    private int downloads;

    private File musicFile;             //音乐文件对象
    private String musicPath;           //音乐文件路径
    private int duration;              //音乐时长
    private String totalTime;           //音乐时长字符串
    private int userId;                 //上传者ID

    public static PlayerPanel panel;
    public static MusicPlayThread lastThread;
    public static MusicPlayThread nowThread;
    public static boolean isRunning = false;


    private static final String FilePath = "src\\MediaPlayer\\Music\\sound\\";

    //网络下载使用 不需要加.mp3后缀
    public Music() {

    }

    //本地使用  需加.mp3后缀
    public Music(String musicName) {
        this.musicPath = FilePath + musicName;
        musicFile = new File(musicPath);
        try {
            setMP3Info();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Music(String musicName, int userId) {
        this(musicName);
        this.userId = userId;
    }


    public void play() {
        if (musicPath == null) {
            try {
                musicPath = FilePath + singerName + "-" + songName + ".mp3";
                FileOutputStream fileOutputStream = new FileOutputStream(musicPath);
                Client.download(id, fileOutputStream);
                System.out.println("已下载歌曲至" + musicPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //当前/现在 播放线程切换
        if (!isRunning) {
            nowThread = new MusicPlayThread();
            nowThread.setMusic(this);
            nowThread.start();
            isRunning = true;
        } else {
            lastThread = nowThread;
            nowThread = new MusicPlayThread();
            lastThread.stop();
            nowThread.setMusic(this);
            nowThread.start();
        }

        //改变音乐播放器对应的样式
        //改变播放按钮
        panel.setSuspendIcon(1);
        panel.nowMusic = Music.nowThread.getMusic();
        panel.setLabels();
    }

    public void setMP3Info() throws Exception {
        final String SONG_NAME_KEY = "TIT2";
        final String ARTIST_KEY = "TPE1";
        final String ALBUM_KEY = "TALB";
        try {
            MP3File mp3File = (MP3File) AudioFileIO.read(musicFile);
            MP3AudioHeader audioHeader = (MP3AudioHeader) mp3File.getAudioHeader();
            // 单位为秒
            duration = audioHeader.getTrackLength();
            //System.out.println(duration);
            totalTime = duration / 60 + ":" + (duration % 60 < 10 ? "0" + duration % 60 : duration % 60);

            // 歌曲名称
            songName = getInfoFromFrameMap(mp3File, SONG_NAME_KEY);
            // 歌手名称
            singerName = getInfoFromFrameMap(mp3File, ARTIST_KEY);
            // 歌曲专辑
            album = getInfoFromFrameMap(mp3File, ALBUM_KEY);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String getInfoFromFrameMap(MP3File mp3File, String key) throws Exception {
        ID3v23Frame frame = (ID3v23Frame) mp3File.getID3v2Tag().frameMap.get(key);
        return frame.getContent();
    }

    public File getMusicFile() {
        return musicFile;
    }

    public String getMusicPath() {
        return musicPath;
    }

    public String getSongName() {
        return songName;
    }

    public String getSingerName() {
        return singerName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public int getFavor() {
        return favor;
    }

    public void setFavor(int favor) {
        this.favor = favor;
    }

    public void setMusicFile(File musicFile) {
        this.musicFile = musicFile;
    }

    public void setMusicPath(String musicPath) {
        this.musicPath = musicPath;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    @Override
    public String toString() {
        return "Music{" +
                "singerName='" + singerName + '\'' +
                ", album='" + album + '\'' +
                ", songName='" + songName + '\'' +
                ", duration=" + duration +
                ", totalTime='" + totalTime + '\'' +
                '}';
    }
}
