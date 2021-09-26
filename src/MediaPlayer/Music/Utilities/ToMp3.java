package MediaPlayer.Music.Utilities;

import java.io.File;

/**
 * @Author 宋佳俊
 * @create 2021/7/12 15:44
 */
//用于将歌曲全部改为mp3格式
public final class ToMp3 {
    public static void main(String[] args) {
        ToMp3.toMp3();
    }

    public static void toMp3(){
        File file = new File("src\\MediaPlayer\\Music\\sound");
        File[] files = file.listFiles();
        for(int i = 0;i < files.length;i++){
            File thisFile = files[i];
            String name = thisFile.getName();
            name = name.substring(0,name.length()-4);
            name = name + ".mp3";
            thisFile.renameTo(new File("src\\MediaPlayer\\Music\\sound\\"+name));
        }
    }
}
