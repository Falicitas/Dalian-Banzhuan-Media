package MediaPlayer.SearchPanel;

import MediaPlayer.Client;
import MediaPlayer.Music.Music;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.DataOutputStream;
import java.util.Random;
import java.util.Vector;

/**
 * @author Zhejian Lai
 * @Description //TODO
 * @date 2021-07-12-11:08
 */
public class SearchBar extends JPanel {
    private static final int DEFAULT_WIDTH = 650;
    private static final int DEFAULT_HEIGHT = 70;

    private JLabel searchIcon;
    private JTextField edit;
    private JButton check;
    private ShowPanel showPanel;


    public SearchBar() {
        setBounds(170, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLayout(null);

        //载入搜索栏图标
        searchIcon = new JLabel();
        searchIcon.setSize(30, 30);
        searchIcon.setIcon(new ImageIcon("src/MediaPlayer/res/searchIcon.png"));
        searchIcon.setLocation(200, (getHeight() - searchIcon.getHeight()) / 2);
        add(searchIcon);

        //载入搜索栏
        edit = new JTextField();
        edit.setFont(new Font("serif", Font.PLAIN, 16));
        edit.setLocation(searchIcon.getX() + searchIcon.getWidth() + 10, searchIcon.getY());
        edit.setSize(250, 30);
        add(edit);

        //设置搜索栏回车发送请求
        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //空行返回
                if (edit.getText().trim().equals("")) return;

                //待优化：开启一个线程访问服务端
                Vector<Music> v = emit();
                showPanel.setTitle(edit.getText());
                showPanel.setTotal(v.size());
                showPanel.loadMusic(v);
                showPanel.setVisible(true);
            }
        };

        InputMap imap = edit.getInputMap(JComponent.WHEN_FOCUSED);
        imap.put(KeyStroke.getKeyStroke("ENTER"), "emit");
        edit.getActionMap().put("emit", action);

        //设置搜索确认按钮
        check = new JButton(new ImageIcon("src/MediaPlayer/res/checkBtn.png"));
        check.setContentAreaFilled(false);
        check.setFocusPainted(false);
        check.setSize(26, 26);
        check.setLocation(edit.getX() + edit.getWidth() + 5, (getHeight() - check.getHeight()) / 2);
        check.addActionListener(action);
        add(check);

        //对按钮的美化操作
        //check.setBorder(BorderFactory.createLineBorder(new Color(255,255,255,50)));
    }

    //发送搜索栏请求
    Vector<Music> emit() {

        System.out.println("发送信息：" + edit.getText());
        Vector<Music> v = new Vector<>();
        Client.search(edit.getText(),v);
        return v;
    }

    public void setShowPanel(ShowPanel showPanel) {
        this.showPanel = showPanel;
    }


}
