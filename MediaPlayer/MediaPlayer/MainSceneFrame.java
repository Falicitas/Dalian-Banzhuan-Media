package MediaPlayer;

import MediaPlayer.CenterBlock.CenterPanel;
import MediaPlayer.CustomerCenter.CustomerCenter;
import MediaPlayer.ListCenter.MusicPanel;
import MediaPlayer.Music.Music;
import MediaPlayer.PlayerPanel.PlayerPanel;
import MediaPlayer.SearchPanel.ShowPanel;
import MediaPlayer.SearchPanel.SearchBar;

import javax.swing.*;
import javax.swing.plaf.multi.MultiScrollBarUI;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

/**
 * @author Zhejian Lai
 * @Description //TODO
 * @date 2021-07-07-22:45
 */
public class MainSceneFrame extends JFrame {

    private static final int SCREEN_WIDTH = 1020;
    private static final int SCREEN_HEIGHT = 670;

    public static MusicPanel musicPanel;
    private SearchBar searchBar;
    private CustomerCenter customerCenter;
    private ToolBlock toolBlock;
    private CenterPanel centerPanel;
    private PlayerPanel musicPlayer;
    private ShowPanel showPanel;

    public MainSceneFrame() {

        //窗口大小及居中显示
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        pack();
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("src/MediaPlayer/res/icon.png").getImage());
        setLayout(null);

        //添加底部播放器
        musicPlayer = new PlayerPanel();
        //让所有音乐可以访问播放器
        Music.panel = musicPlayer;
        add(musicPlayer);

        //添加音乐清单栏
        musicPanel = new MusicPanel(this);
        musicPanel.setBackground(Color.darkGray);
        JScrollPane scrollPane = new JScrollPane(musicPanel);
        scrollPane.setBounds(0, 0, MusicPanel.DEFAULT_WIDTH, MusicPanel.DEFAULT_HEIGHT);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(musicPanel);

        //添加搜索栏
        searchBar = new SearchBar();
        searchBar.setBackground(Color.WHITE);
        add(searchBar);

        //添加用户中心
        customerCenter = new CustomerCenter();
        add(customerCenter);

        //添加功能块
        toolBlock = new ToolBlock();
        toolBlock.setBackground(Color.WHITE);
        add(toolBlock);

        //添加展示模块
        showPanel = new ShowPanel();
        //与搜索栏关联
        searchBar.setShowPanel(showPanel);
        showPanel.setVisible(false);
        add(showPanel);

        //添加中心模块
        centerPanel = new CenterPanel();
        centerPanel.setBackground(Color.CYAN);
        add(centerPanel);

        pack();


        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosed(e);
                deleteDir(new File("src\\MediaPlayer\\Music\\sound"), false);
            }
        });
    }

    public CenterPanel getCenterPanel() {
        return centerPanel;
    }

    boolean deleteDir(File dir, boolean flag) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]), true);
                if (!success) {
                    return false;
                }
                if (flag) {
                    dir.delete();
                }
            }
        }
        // 目录此时为空，可以删除
        if (flag)
            return dir.delete();
        else
            return true;
    }

    public CustomerCenter getCustomerCenter() {
        return customerCenter;
    }
}
