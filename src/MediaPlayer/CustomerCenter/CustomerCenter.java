package MediaPlayer.CustomerCenter;

import MediaPlayer.CustomerCenter.upload.Upload_frame;
import MediaPlayer.CustomerCenter.userInformation.SusWindow;
import MediaPlayer.CustomerCenter.userInformation.User;
import MediaPlayer.Start;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Zhejian Lai
 * @Description //TODO
 * @date 2021-07-07-22:48
 */
public class CustomerCenter extends JPanel {
    //窗口宽度，高度
    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 70;


    //用户界面按钮
    private JButton btn;
    //点击btn后弹出，如果已经登陆，则弹出用户中心按钮
    private SusWindow susWindow;
    //点击btn后弹出，如果未登录，则弹出登陆界面
    private Upload_frame upload_frame;

    public Upload_frame getUpload_frame(){
        return upload_frame;
    }
    public CustomerCenter() {
        setBounds(820, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setBackground(Color.white);

        //设置主题小按钮
        btn = new JButton(new ImageIcon("src/MediaPlayer/res/CustomerCenter.png"));
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setBorder(null);
        add(btn);

        //设置用户中心
        susWindow = new SusWindow();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        susWindow.setLocation(((int) toolkit.getScreenSize().getWidth() - susWindow.getWidth()) / 2, ((int) toolkit.getScreenSize().getHeight() - susWindow.getHeight()) / 2);
        susWindow.setVisible(false);
        susWindow.setModal(true);
        susWindow.setResizable(false);

        //设置登陆界面
        upload_frame = new Upload_frame(Start.frame,"登陆界面",true);

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (User.up_Load_state) {
                    susWindow.setVisible(true);
                } else {
                    //初始化清空窗口内容
                    upload_frame.account.reload();
                    upload_frame.password.reload();
                    upload_frame.label.requestFocus();
                    upload_frame.setVisible(true);
                }
            }
        });
    }

    public SusWindow getSusWindow() {
        return susWindow;
    }
}