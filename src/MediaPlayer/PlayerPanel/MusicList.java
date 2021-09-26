package MediaPlayer.PlayerPanel;

import MediaPlayer.Music.Music;

import java.util.ArrayList;

/**
 * @Author 宋佳俊
 * @create 2021/7/9 14:40
 */

public class MusicList {
    private String musicListName;
    private ArrayList<Music> musics;
    private ArrayList<MusicButton> musicButtons;

    public MusicList(String musicListName){
        this.musicListName = musicListName;
        musics = new ArrayList<Music>();
        musicButtons = new ArrayList<MusicButton>();
    }

    public void addMusic(Music music){
        musics.add(music);
        addMusicButton(new MusicButton(music));
    }

    private void addMusicButton(MusicButton mb){
        musicButtons.add(mb);
    }

    public ArrayList<Music> getMusics(){
        return musics;
    }

    public Music getMusic(int index){
        return musics.get(index);
    }

    public MusicButton getMusicButton(int index){
        return musicButtons.get(index);
    }

    public ArrayList<MusicButton> getAllMusicButtons(){
        return musicButtons;
    }

    public String getMusicListName(){
        return musicListName;
    }

    public void setMusicListName(String musicListName) {
        this.musicListName = musicListName;
    }

    public int size(){
        return musics.size();
    }

    public void clear(){
        musics.clear();
        musicButtons.clear();
    }

    public void resetList(ArrayList<Music> musics){
        clear();
        for(int i = 0;i < musics.size();i++){
            addMusic(musics.get(i));
        }
    }
}