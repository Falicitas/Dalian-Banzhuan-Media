package MediaPlayer.SearchPanel;

import MediaPlayer.Music.Music;
import MediaPlayer.Tool.GBC;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.Vector;

/**
 * @author Zhejian Lai
 * @Description //TODO
 * @date 2021-07-12-12:46
 */
public class ShowPanel extends JPanel {
    private static final int DEFAULT_WIDTH = 850;
    private static final int DEFAULT_HEIGHT = 480;

    private JLabel titleLabel;
    private JLabel detailLabel;
    private JButton returnBtn;
    private String title;//界面中的标题
    private int total; //界面中歌曲总数目
    private DetailPanel detailPanel;
    private JScrollPane scrollPane;

    public ShowPanel() {
        setBounds(170, 70, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLayout(new GridBagLayout());

        //设置标题界面
        titleLabel = new JLabel();
        titleLabel.setFont(new Font("sans-serif", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        detailLabel = new JLabel();
        detailLabel.setForeground(new Color(0x333333));
        detailLabel.setFont(new Font("sans-serif", Font.PLAIN, 12));
        add(titleLabel, new GBC(0, 0, 1, 2).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL).setWeights(100, 0));
        add(detailLabel, new GBC(0, 2).setAnchor(GBC.WEST).setFill(GBC.HORIZONTAL).setInsets(0, 10, 20, 0));


        //设置返回按钮
        returnBtn = new JButton(new ImageIcon("src/MediaPlayer/res/returnBtn.png"));
        returnBtn.setContentAreaFilled(false);
        returnBtn.setFocusPainted(false);
        returnBtn.setPreferredSize(new Dimension(30, 30));
        returnBtn.addActionListener(e -> {
            setVisible(false);
            System.out.println("关闭搜索界面");
        });
        add(returnBtn, new GBC(0, 1, 1, 3).setAnchor(GBC.NORTHEAST).setInsets(10, 0, 0, 25));

        //添加歌曲导航条
        add(ShowItem.getDefault(), new GBC(0, 3).setFill(GBC.HORIZONTAL));

        //添加歌曲展示
        scrollPane = new JScrollPane();
        //优化滚动事件
        add(scrollPane, new GBC(0, 4).setFill(GBC.BOTH).setWeights(100, 100));
    }

    //导入歌曲到展示面板
    public void loadMusic(Vector<Music> v) {
        detailPanel = new DetailPanel();
        scrollPane.setViewportView(detailPanel);
        for (int i = 0; i < total; ++i) {
            detailPanel.add(new ShowItem(v.get(i)), i);
        }
    }

    public void setTitle(String title) {
        this.title = title;
        titleLabel.setText("搜索" + "“" + title + "”");
    }

    public void setTotal(int total) {
        this.total = total;
        detailLabel.setText("找到" + total + "首歌曲");
    }

}

class DetailPanel extends JPanel {

    DetailPanel() {
        setLayout(new GridBagLayout());
    }

    public void add(ShowItem item, int pos) {
        add(item, new GBC(0, pos).setFill(GBC.HORIZONTAL).setWeights(100, 0).setInsets(5));
    }

}
