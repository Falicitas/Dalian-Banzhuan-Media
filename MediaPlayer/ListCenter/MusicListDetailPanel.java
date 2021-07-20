package MediaPlayer.ListCenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Zhejian Lai
 * @Description //TODO
 * @date 2021-07-14-14:40
 */
public class MusicListDetailPanel extends JPanel {
    private static final int DEFAULT_WIDTH = 170;
    private static final int DEFAULT_HEIGHT = 670;

    MusicListDetailPanel() {
        setBounds(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 2));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                e.consume();
            }
        });
    }

    //用于单击歌单项时加载详情内容
    void loadList(MusicPanel.MusicListForShow list) {
        //进入时初始化
        removeAll();

        //添加组件
        add(list);
        for (MusicPanel.MusicItemForShow i : list.items)
            add(i);
    }

}
