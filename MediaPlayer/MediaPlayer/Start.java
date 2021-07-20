package MediaPlayer;

import MediaPlayer.Music.Music;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.sql.*;

/**
 * @author Zhejian Lai
 * @Description //TODO
 * @date 2021-07-07-22:44
 */
public class Start {
    public static MainSceneFrame frame;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Client.init("192.168.31.133", 8888);
            //Client.init("127.0.0.1", 8888);

            frame = new MainSceneFrame();
            frame.setTitle("音乐播放器");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.setVisible(true);
        });

    }

    @Test
    public void modifyDatabase() {
        Client.init("192.168.31.133", 8888);

        FileInputStream fis = null;//bg1.jpg
        try {
            Music music = new Music("Ayo97 _ 阿涵 - 感谢你曾来过.mp3");
            fis = new FileInputStream(new File(music.getMusicPath()));
            byte[] b = new byte[1024];
            int len, sum = 0;
            while ((len = fis.read(b)) != -1) {
                sum += len;
            }
            fis.close();
            FileInputStream f = new FileInputStream(new File(music.getMusicPath()));// bg1.jpg

            //Client.upload(0, music.getSongName(), sum, f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void upload() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Connection conn = DriverManager.getConnection("Jdbc:mysql://localhost:3306/MusicPlayer", "root", "1234");
            Connection conn = DriverManager.getConnection("Jdbc:mysql://192.168.31.133:3306/project", "root", "123456");
            PreparedStatement ps = conn.prepareStatement("insert into Music(id, name, singer, year, views, downloads, provider, source, album, lenth ) values(?,?,?,?,?,?,?,?,?,?);");
            Music music = new Music("Ayo97 _ 阿涵 - 感谢你曾来过.mp3");
            ps.setInt(1, 7);// id
            ps.setString(2, music.getSongName());// name
            ps.setString(3, music.getSingerName());// singer
            ps.setNull(4, Types.INTEGER);// year
            ps.setInt(5, 0);// views
            ps.setInt(6, 0);// downloads
            ps.setInt(7, 0);// provider
            ps.setBinaryStream(8, new FileInputStream(music.getMusicPath()));// source
            ps.setString(9, music.getAlbum());// album
            ps.setString(10, music.getTotalTime());// totaltime
            int x = ps.executeUpdate();
            System.out.println(x);
        } catch (Exception e) {
            System.out.println(2);
            e.printStackTrace();
        }
    }
}
