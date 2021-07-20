package MediaPlayer.CustomerCenter.userInformation;
/**
 * @Author Yang
 * @create 2021/7/8 14:34
 */

import MediaPlayer.Client;
import MediaPlayer.CustomerCenter.Data;
import MediaPlayer.Music.Music;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;
import java.io.*;
import javax.swing.filechooser.*;

/**
 * 用户中心悬浮窗，如果未登录弹出登陆框，登陆了则弹出本悬浮窗，所有数据均从数据库寻得
 */
public class SusWindow extends JDialog implements ActionListener {
    private JButton headImage;     //头像按钮
    private JLabel nickname;        //昵称标签
    private JLabel login_time;      //登陆时间
    private JLabel grade;           //用户级别
    private JLabel myUpload;        //我上传的歌单
    private JPanel panel;           //上传的歌的列表
    private JScrollPane mySongList;  //滚动窗口
    private JLabel uploadExplain;    //上传解释标签
    private JButton upload;           //上传按钮
    private Container cont = this.getContentPane();

    public void resetNickname(String name){
        nickname.setText(name);
    }

    public void resetGrade(int grade){
        this.grade.setText("<html>当前级别<br/>"+String.valueOf(grade));
    }

    public void resetLogin_time(String time){
        this.login_time.setText(time);
    }

    private void headImageSetting() {
        //头像图片设置
        headImage = new JButton("+");
        if (!User.has_headImage) {
            headImage.setIcon(Data.headImageButton);
        } else {
            headImage.setIcon(Data.headImage);
        }
        headImage.setBounds(10, 10, 100, 100);
        headImage.addActionListener(this);
        cont.add(headImage);
    }

    public void resetHeadImage(ImageIcon image) {
        headImage.setIcon(image);
        repaint();
    }

    private void nicknameSetting() {
        //昵称设置
        nickname = new JLabel(User.nickname);
        nickname.setBounds(130, 25, 100, 40);
        nickname.setOpaque(true);
        cont.add(nickname);
    }

    private void login_timeSetting() {
        //登陆时间设置
        login_time = new JLabel("注册于" + User.login_time);
        login_time.setBounds(130, 70, 100, 30);
        login_time.setOpaque(true);
        cont.add(login_time);
    }

    private void gradeSetting() {
        grade = new JLabel("<html>当前级别<br/>" + User.grade);
        grade.setBounds(290, 25, 80, 80);
        cont.add(grade);
    }

    private void myUploadListSetting() {
        myUpload = new JLabel("我上传的：");
        myUpload.setBounds(15, 170, 100, 20);
        myUpload.setOpaque(true);
        cont.add(myUpload);

        panel = new JPanel();
        panel.setLayout(new GridLayout(10, 1));
        for (int i = 0; i < User.listNumber; i++) {
            JLabel test = new JLabel((i + 1) + User.userlist[i], JLabel.LEFT);
            panel.add(test);
        }

        mySongList = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //mySongList.setHorizontalAlignment(JLabel.CENTER);
        mySongList.setBounds(15, 200, 200, 300);
        cont.add(mySongList);
    }

    public void init() {
        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        //this.setResizable(false);
        this.setSize(400, 600);
        this.setBackground(Color.WHITE);
        this.setLayout(null);  //清空布局

        /**
         *  头像
         */
        headImageSetting();

        /**
         * 昵称、登陆时间、级别
         */
        nicknameSetting();
        login_timeSetting();
        gradeSetting();

        /**
         * 我上传的歌单
         */
        myUploadListSetting();


        /**
         * 点击上传
         */
        uploadExplain = new JLabel("<html>点击这里上传歌曲哦！请将文件名命名为“歌曲名-作者”哦！");
        uploadExplain.setBounds(240, 260, 120, 60);
        uploadExplain.setOpaque(true);
        cont.add(uploadExplain);

        upload = new JButton("上传");
        upload.setBounds(250, 350, 80, 80);
        cont.add(upload);

        upload.addActionListener(this);
        this.setVisible(false);
    }


    public SusWindow() {
        init();
        //获得焦点和键盘事件
        this.setFocusable(true);
    }

    //事件监听
    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonName = e.getActionCommand();
        if (buttonName.equals("+")) {
            JFileChooser jfc_image = new JFileChooser();
            jfc_image.addChoosableFileFilter(new FileNameExtensionFilter("jpg/png file", "jpg", "png"));//导入可选择的文件的后缀名类型
            int value = jfc_image.showDialog(new JLabel(), "选择");//open也一样
            if (value == JFileChooser.APPROVE_OPTION) { //判断窗口是否点的是打开或保存
                File file = jfc_image.getSelectedFile();
                String str_path = jfc_image.getSelectedFile().getAbsolutePath();
                System.out.println(str_path);
                String str_name = file.getName();
                System.out.println(str_name);
                try {
                    //这里要将取得的文件上传至数据库
                    if (file.exists()) { //文件存在时,读入原文件
                        InputStream inStream = new FileInputStream(str_path);
                        int len, sum = 0;
                        byte[] b = new byte[1024];
                        while ((len = inStream.read(b)) != -1) {
                            sum += len;
                        }
                        Client.upHead(User.getUserId(), sum, new FileInputStream(str_path));
                    }
                    Image image = ImageIO.read(new File(file.getAbsolutePath()));
                    headImage.setIcon(new ImageIcon(image));
                } catch (Exception ex) {
                    System.out.println("上传头像操作出错");
                    ex.printStackTrace();
                }
                System.out.println("上传成功");
            } else {
            }//点了取消
        } else if (buttonName.equals("上传")) {
            JFileChooser jfc_image = new JFileChooser();
            jfc_image.addChoosableFileFilter(new FileNameExtensionFilter("mp3 file", "mp3"));//导入可选择的文件的后缀名类型
            int value = jfc_image.showDialog(new JLabel(), "选择");//open也一样
            if (value == JFileChooser.APPROVE_OPTION) { //判断窗口是否点的是打开或保存
                File file = jfc_image.getSelectedFile();
                String str_path = jfc_image.getSelectedFile().getAbsolutePath();
                String str_name = file.getName();
                try {
                    //这里要将取得的文件上传至数据库
                    if (file.exists()) { //文件存在时,读入原文件
                        //先拷贝文件

                        //创建源文件与目的文件
                        File srcFile = new File(str_path);
                        File destFile = new File("src/MediaPlayer/Music/sound/" + str_name);
                        //复制文件
                        InputStream inStream = new FileInputStream(str_path);
                        OutputStream outStream = new FileOutputStream(destFile);
                        byte[] buf = new byte[1024];
                        int len;
                        while ((len = inStream.read(buf)) != -1) {
                            outStream.write(buf, 0, len);
                        }
                        Music music = new Music(str_name, User.userId);
                        Client.upload(music);
                    }
                    System.out.println("成功");
                } catch (Exception ex) {
                    System.out.println("上传头像操作出错");
                    ex.printStackTrace();
                }
            } else {

            }
        }
    }
}


