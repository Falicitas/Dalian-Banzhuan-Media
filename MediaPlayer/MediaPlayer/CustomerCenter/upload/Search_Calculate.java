package MediaPlayer.CustomerCenter.upload;

import MediaPlayer.CustomerCenter.userInformation.User;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author 宋佳俊
 * @create 2021/7/20 17:06
 */
public class Search_Calculate {
    public void search() {//从数据库查找当前用户数据,查找当前用户上传的歌曲，查找完设置headImage、nickname、login_time、userlist

        User.user_Information_state = true;
    }

    public void tailor() {                   //裁剪图片，适应图标大小
        ImageIcon icon1 = new ImageIcon();
        Image img = icon1.getImage();
        Image newimg = img.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(newimg);
        User.headImage = icon;
    }

    //old是注册时间的字符串，计算等级
    public void data_calculate_grade(String old) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d1 = df.parse(old);
            Date d2 = df.parse(df.format(new Date()));
            long diff = d2.getTime() - d1.getTime();//这样得到的差值是毫秒级别
            long days = diff / (1000 * 60 * 60 * 24);
            User.grade = ((int) days / 100) + 1;
            System.out.println("" + days + "天");
        } catch (Exception e) {
        }
    }

    Search_Calculate() {

    }
}
