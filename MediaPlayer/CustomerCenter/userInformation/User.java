package MediaPlayer.CustomerCenter.userInformation;

import MediaPlayer.Client;
import MediaPlayer.MainSceneFrame;
import MediaPlayer.Start;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

/**
 * @Author 宋佳俊
 * @create 2021/7/18 17:15
 */
public class User {
    public static int userId;
    public static ImageIcon headImage;
    public static String nickname = "——";
    public static String login_time = "——";//注册时间
    public static int grade = 0;
    public static String[] userlist;//用户上传的歌曲
    public static int listNumber;   //用户上传的歌曲数目

    //是否处于已经登陆状态，true表示已经登陆，false表示未登录
    public static boolean up_Load_state = false;//登陆状态
    public static boolean user_Information_state = false;
    public static boolean has_headImage = false;        //默认为没有头像

    public static User nowUser;

    public void User() {

        //设置头像
        File file = new File("src/MediaPlayer/CustomerCenter/userInformation/statics/User" + userId + ".png");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        Client.getHead(userId, fos);
        try {
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Image image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        if (image != null) {
            headImage = new ImageIcon(image);
            Start.frame.getCustomerCenter().getSusWindow().resetHeadImage(headImage);
        }
        Vector<String> usernameRtimeNickname = Client.getUser(userId);
        nickname = usernameRtimeNickname.get(2);
        login_time = usernameRtimeNickname.get(1);
        Start.frame.getCustomerCenter().getSusWindow().resetNickname(nickname);
        Start.frame.getCustomerCenter().getSusWindow().resetLogin_time("注册于" + login_time);
        Start.frame.getCustomerCenter().getUpload_frame().search_calculate.data_calculate_grade(login_time);
        Start.frame.getCustomerCenter().getSusWindow().resetGrade(User.grade);
    }

    public User(int id) {
        userId = id;
        User();
    }

    public static int getUserId() {
        return nowUser.userId;
    }

    public static void setUserId(int userId) {
        nowUser.userId = userId;
    }
}
