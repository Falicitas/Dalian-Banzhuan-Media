package MediaPlayer.SearchPanel;

import MediaPlayer.Client;
import MediaPlayer.ListCenter.MusicPanel;
import MediaPlayer.Music.Music;
import MediaPlayer.PlayerPanel.PlayerPanel;
import MediaPlayer.Start;
import MediaPlayer.Tool.GBC;
import javazoom.jl.player.advanced.PlaybackEvent;
import org.jaudiotagger.test.TestAudioTagger;

import javax.imageio.stream.IIOByteBuffer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * @author Zhejian Lai
 * @Description //TODO
 * @date 2021-07-13-20:01
 */
public class ShowItem extends JPanel {
    private JLabel songLabel;
    private JLabel singerLabel;
    private JLabel albumLabel;
    private JLabel timeLabel;

    private JPopupMenu popupMenu;
    private JMenuItem m_score;
    private JMenu m_addList;
    private JDialog scoreDialog;
    private JLabel score;
    private JLabel shortComment;
    private JLabel[] icons = new JLabel[5];
    private int cntStar;

    private Music music;

    private static int fontSize = 14;

    void setPopupMenu() {
        //设置弹出菜单
        popupMenu = new JPopupMenu();
        m_addList = new JMenu("添加到歌单");
        m_score = new JMenuItem("打分");
        popupMenu.add(m_score);
        popupMenu.add(m_addList);

        //设置打分按钮弹出窗口
        scoreDialog = new JDialog(Start.frame, "打分", true);
        scoreDialog.setSize(500, 90);
        scoreDialog.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        score = new JLabel("0星");
        shortComment = new JLabel("不喜欢");

        //为弹出窗口初始化
        for (int i = 0; i < 5; ++i) {
            icons[i] = new JLabel(new ImageIcon("src/MediaPlayer/res/favor0.png"));
            icons[i].setSize(40, 40);
        }

        //为打分窗口添加动画
        scoreDialog.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                cntStar = 0;
                for (JLabel i : icons) {
                    i.setIcon(new ImageIcon("src/MediaPlayer/res/favor" + (i.getX() + 5 <= e.getX() ? 1 : 0) + ".png"));
                    cntStar += (i.getX() + 5 <= e.getX() ? 1 : 0);
                }

                score.setText(cntStar + "星");
                switch (cntStar) {
                    case 0:
                        shortComment.setText("不喜欢");
                        break;
                    case 1:
                        shortComment.setText("勉勉强强");
                        break;
                    case 2:
                        shortComment.setText("半拉咔叽");
                        break;
                    case 3:
                        shortComment.setText("有点妙");
                        break;
                    case 4:
                        shortComment.setText("宇宙无敌妙！");
                        break;
                    case 5:
                        shortComment.setText("天籁之音！！！！tql orz");
                        break;
                }
            }
        });

        //添加组件
        scoreDialog.add(score);
        for (int i = 4; i >= 0; --i)
            scoreDialog.add(icons[i]);
        scoreDialog.add(shortComment);

        //打分窗口显示
        m_score.addActionListener(e -> {
            //居中显示
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            scoreDialog.setLocation((int) (toolkit.getScreenSize().getWidth() - scoreDialog.getWidth()) / 2, (int) (toolkit.getScreenSize().getHeight() - scoreDialog.getHeight()) / 2);

            //初始化组件
            for (JLabel i : icons) {
                i.setIcon(new ImageIcon("src/MediaPlayer/res/favor0.png"));
            }
            score.setText("0星");
            shortComment.setText("不喜欢");
            scoreDialog.setVisible(true);
        });
    }

    public ShowItem(int mode) {
        setSize(850, 20);
        setBackground(new Color(230, 227, 220, 50));

        songLabel = new JLabel();
        songLabel.setForeground(new Color(0x333333));
        songLabel.setFont(new Font("Sans-serif", Font.PLAIN, fontSize));
        songLabel.setPreferredSize(new Dimension(425, 20));
        singerLabel = new JLabel();
        singerLabel.setForeground(new Color(0x333333));
        singerLabel.setFont(new Font("Sans-serif", Font.PLAIN, fontSize));
        singerLabel.setPreferredSize(new Dimension(175, 20));
        albumLabel = new JLabel();
        albumLabel.setForeground(new Color(0x333333));
        albumLabel.setFont(new Font("Sans-serif", Font.PLAIN, fontSize));
        albumLabel.setPreferredSize(new Dimension(140, 20));
        timeLabel = new JLabel();
        timeLabel.setForeground(new Color(0x333333));
        timeLabel.setFont(new Font("Sans-serif", Font.PLAIN, fontSize));
        timeLabel.setPreferredSize(new Dimension(155, 20));
        if (mode == 1) {
            timeLabel.setPreferredSize(new Dimension(70, 20));
            setPopupMenu();
            add(songLabel);
            add(singerLabel);
            add(albumLabel);
            add(timeLabel);
        }
    }

    public ShowItem(Music music) {
        this(1);
        this.music = music;
        songLabel.setText(music.getSongName());
        singerLabel.setText(music.getSingerName().equals("null") ? "未知歌手" : music.getSingerName());
        albumLabel.setText("《" + (music.getAlbum().equals("null") ? "未知专辑" : music.getSingerName()) + "》");
        timeLabel.setText(music.getTotalTime());

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (e.getClickCount() >= 2) {
                        music.play();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {

                    m_addList.removeAll();

                    //将当前打开的用户歌单加入到添加列表
                    for (MusicPanel.MusicListForShow i : MusicPanel.list) {
                        //右键组件添加歌单  载入歌单
                        JMenuItem item = new JMenuItem(i.getListName());
                        m_addList.add(item);

                        item.addActionListener(ee -> {
                            System.out.println(music.getSongName() + "已成功添加到" + i.getListName());

                            Client.addtoAlbum(i.getId(), music.getId());
                            i.add(music, MusicPanel.curList != null && i.getListName().equals(MusicPanel.curList.getListName()));

                            if (i.getListName().equals(PlayerPanel.musicList.getMusicListName())) {
                                PlayerPanel.musicList.addMusic(music);
                            }
                        });
                    }

                    //scoreDialog.add

                    popupMenu.show(getParent(), e.getX() + 16, getY() + 11 + e.getY());
                }
            }
        });


        scoreDialog.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    int uid = 0;
                    Client.score(uid, music.getId(), cntStar);
                    cntStar = 0;
                    System.out.println(music.getSongName() + "的评分为" + Client.getMusicScore(music.getId()));
                    scoreDialog.setVisible(false);
                }
            }
        });
    }

    public static ShowItem getDefault() {
        ShowItem defaultItem = new ShowItem(0);
        defaultItem.setLayout(new GridBagLayout());
        defaultItem.music = null;
        defaultItem.songLabel.setText("歌曲名");
        defaultItem.singerLabel.setText("歌手名");
        defaultItem.albumLabel.setText("专辑名");
        defaultItem.timeLabel.setText("播放时长");
        defaultItem.add(defaultItem.songLabel, new GBC(0, 0, 10, 1).setAnchor(GBC.WEST).setWeights(58.82, 0).setInsets(0, 10, 0, 0));
        defaultItem.add(defaultItem.singerLabel, new GBC(10, 0, 3, 1).setAnchor(GBC.WEST).setWeights(21.04, 0));
        defaultItem.add(defaultItem.albumLabel, new GBC(13, 0, 3, 1).setAnchor(GBC.WEST).setWeights(15.64, 0));
        defaultItem.add(defaultItem.timeLabel, new GBC(16, 0).setAnchor(GBC.WEST).setWeights(4.5, 0));
        return defaultItem;
    }
}
