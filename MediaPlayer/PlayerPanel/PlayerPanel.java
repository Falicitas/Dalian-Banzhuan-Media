
package MediaPlayer.PlayerPanel;

import MediaPlayer.Music.Music;
import MediaPlayer.Music.MusicPlayThread;
import MediaPlayer.Start;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.multi.MultiScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Random;

/**
 * @Author 宋佳俊
 * @create 2021/7/8 9:25
 */
public class PlayerPanel extends JPanel {
    private static final int DEFAULT_WIDTH = 850;
    private static final int DEFAULT_HEIGHT = 120;

    private JLabel songNameLabel;            //歌名
    private int songNameLabelWight;
    private int songNameLabelHeight;
    private JLabel singerNameLabel;          //歌手名字
    private int singerNameLabelWight;
    private int singerNameLabelHeight;

    private JButton playModeButton;           //音乐播放模式，单曲循环、列表循环、随机等
    private ImageIcon[] playModeIconList = new ImageIcon[4];        //0代表列表循环，1代表单曲循环，2随机播放，3代表顺序播放
    private int playModeState = 0;                              //初始化为列表循环

    private JButton lastSongButton;           //切换上一首按钮
    private JButton nextSongButton;           //切换下一首按钮

    private JButton suspendOrStartButton;                       //暂停按钮
    private ImageIcon[] musicModeIconList = new ImageIcon[2];   //音乐状态，暂停或者开始
    private int musicModeState = 0;                             //初始化为暂停

    private JLabel currentTimeLabel;         //音乐当前时间
    private JLabel totalTimeLabel;           //音乐总时间
    private JProgressBar musicProgressBar;   //音乐进度条

    private JButton volumeButton;             //音量按钮，按一下
    private JProgressBar volumeProgressBar;   //音量大小进度条

    private JButton musicListButton;           //播放列表按钮
    public static MusicList musicList;               //播放列表按钮
    public static Music nowMusic;                     //当前播放的音乐
    private JDialog musicListDialog;           //播放列表弹窗
    private boolean dialogIsOpen;              //弹窗是否可见

    public JDialog getMusicListDialog() {
        return musicListDialog;
    }

    public MusicList getMusicList() {
        return musicList;
    }

    public PlayerPanel() {
        init();
        setPanel();
    }

    private void musicListSetting() {    //音乐列表设置
        //音乐列表按钮显示设置
        musicListButton = new JButton();
        musicListButton.setFocusPainted(false);
        musicListButton.setContentAreaFilled(false);
        musicListButton.setBorder(null);
        musicListButton.setBounds(780, 47, 21, 21);
        musicListButton.setIcon(Data.playListIcon);
        add(musicListButton);
        musicListButton.setToolTipText("播放列表");
        //音乐列表设置
        musicList = new MusicList("我喜欢的音乐");
        /*File soundDir = new File("src\\MediaPlayer\\Music\\sound");
        File[] paths = soundDir.listFiles();
        for (int i = 0; i < paths.length; i++)
            musicList.addMusic(new Music(paths[i].getName()));*/
        //弹窗设置
        //音乐列表按钮功能设置，点击弹出弹窗，每个弹窗中拥有所有歌曲的按钮
        musicListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //弹窗设置
                musicListDialog = new JDialog(Start.frame, "播放列表", true);
                Container container = musicListDialog.getContentPane();
                dialogIsOpen = false;               //默认为不显示
                dialogIsOpen = !dialogIsOpen;
                musicModeState = 1;
                for (int i = 0; i < musicList.getAllMusicButtons().size(); i++) {
                    MusicButton mb = musicList.getMusicButton(i);
                    Music music = musicList.getMusic(i);
                    mb.setText(music.getSongName());
                    mb.setVisible(dialogIsOpen);
                }
                for (MusicButton musicButton : musicList.getAllMusicButtons()) {
                    container.add(musicButton);           //向弹窗中添加音乐按钮
                }
                musicListDialog.setLayout(new GridLayout(musicList.getMusics().size(), 1));
                System.out.println(getMusicList().size());
                musicListDialog.setSize(200, 30 + 50 * musicList.getMusics().size());
                System.out.println(musicListDialog.getHeight());
                musicListDialog.setLocationRelativeTo(Start.frame.getCenterPanel());
                musicListDialog.setVisible(dialogIsOpen);
            }
        });
    }

    public void init() {
        //播放模式
        //0代表列表循环，1代表单曲循环，2随机播放，3代表顺序播放
        playModeState = 0;                              //默认为列表循环
        playModeIconList[0] = Data.listCycleIcon;       //列表循环
        playModeIconList[1] = Data.singleCycleIcon;     //单曲循环
        playModeIconList[2] = Data.randomIcon;          //随机播放
        playModeIconList[3] = Data.sequenceIcon;        //顺序播放

        musicModeState = 0;                             //初始化为暂停
        musicModeIconList[0] = Data.startIcon;          //暂停
        musicModeIconList[1] = Data.suspendIcon;          //开始

    }

    private void singerNameLabelSetting() {
        singerNameLabel = new JLabel();
        singerNameLabel.setText("");
        singerNameLabel.setFont(new Font("楷体", Font.BOLD, 15));
        singerNameLabelWight = currentTimeLabel.getX() - 15;
        singerNameLabelHeight = 50;
        singerNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        singerNameLabel.setBounds(-10, 35, singerNameLabelWight, singerNameLabelHeight);
        add(singerNameLabel);
    }

    private void songNameLabelSetting() {
        songNameLabel = new JLabel();
        songNameLabel.setText("");
        songNameLabel.setFont(new Font("楷体", Font.BOLD, 15));
        songNameLabelWight = currentTimeLabel.getX() - 15;
        songNameLabelHeight = 50;
        songNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        songNameLabel.setBounds(-9, 0, songNameLabelWight, songNameLabelHeight);
        add(songNameLabel);
    }

    public void setSuspendIcon(int i) {
        musicModeState = i;
        suspendOrStartButton.setIcon(musicModeIconList[i]);
    }

    private void suspendOrStartButtonSetting() {
        suspendOrStartButton = new JButton();
        suspendOrStartButton.setContentAreaFilled(false);
        suspendOrStartButton.setFocusPainted(false);
        suspendOrStartButton.setBorder(null);
        suspendOrStartButton.setBounds(420, 10, 20, 20);
        suspendOrStartButton.setIcon(musicModeIconList[musicModeState]);
        add(suspendOrStartButton);

        suspendOrStartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Music.nowThread != null) {
                    if (musicModeState == 1) {
                        suspendOrStartButton.setToolTipText("暂停");
                        Music.nowThread.suspend();
                        musicModeState = 0;
                        suspendOrStartButton.setIcon(musicModeIconList[musicModeState]);
                    } else {
                        suspendOrStartButton.setToolTipText("播放");
                        Music.nowThread.resume();
                        musicModeState = 1;
                        suspendOrStartButton.setIcon(musicModeIconList[musicModeState]);
                    }
                }
            }
        });
    }


    private void playModeButtonSetting() {
        playModeButton = new JButton();
        playModeButton.setContentAreaFilled(false);
        playModeButton.setFocusPainted(false);
        playModeButton.setBorder(null);
        playModeButton.setBounds(320, 10, 20, 20);
        playModeButton.setIcon(playModeIconList[playModeState]);
        add(playModeButton);

        playModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playModeState = (playModeState + 1) % 4;
                playModeButton.setIcon(playModeIconList[playModeState]);
                //0代表列表循环，1代表单曲循环，2随机播放，3代表顺序播放
                if (playModeState == 0) {
                    playModeButton.setToolTipText("列表循环");
                } else if (playModeState == 1) {
                    playModeButton.setToolTipText("单曲循环");
                } else if (playModeState == 2) {
                    playModeButton.setToolTipText("随机播放");
                } else {
                    playModeButton.setToolTipText("顺序播放");
                }
            }
        });
    }

    private void lastSongButtonSetting() {
        lastSongButton = new JButton();
        lastSongButton.setContentAreaFilled(false);
        lastSongButton.setFocusPainted(false);
        lastSongButton.setBorder(null);
        lastSongButton.setBounds(370, 10, 20, 20);
        lastSongButton.setIcon(Data.lastSongIcon);
        add(lastSongButton);
        lastSongButton.setToolTipText("上一首");

        lastSongButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Music.nowThread == null)
                    return;
                Music toBePlayed = musicList.getMusic(0);
                if (playModeState == 2) {
                    Random random = new Random();
                    int index = (int) (random.nextDouble() * musicList.size());
                    toBePlayed = musicList.getMusic(index);
                } else {
                    if (musicList.getMusic(0).equals(nowMusic)) {
                        toBePlayed = musicList.getMusic(musicList.size() - 1);
                    } else {
                        for (int i = 1; i < musicList.size(); i++) {
                            if (musicList.getMusic(i).equals(nowMusic)) {
                                toBePlayed = musicList.getMusic(i - 1);
                                break;
                            }
                        }
                    }
                }
                setSuspendIcon(1);
                if (!Music.isRunning) {
                    Music.nowThread = new MusicPlayThread();
                    Music.nowThread.setMusic(toBePlayed);
                    Music.nowThread.start();
                    Music.isRunning = true;
                } else {
                    Music.lastThread = Music.nowThread;
                    Music.nowThread = new MusicPlayThread();
                    Music.lastThread.stop();
                    Music.nowThread.setMusic(toBePlayed);
                    Music.nowThread.start();
                }
                nowMusic = Music.nowThread.getMusic();
                setLabels();
            }
        });
    }

    private void nextSongButtonSetting() {
        nextSongButton = new JButton();
        nextSongButton.setContentAreaFilled(false);
        nextSongButton.setFocusPainted(false);
        nextSongButton.setBorder(null);
        nextSongButton.setBounds(470, 10, 20, 20);
        nextSongButton.setIcon(Data.nextSongIcon);
        add(nextSongButton);
        nextSongButton.setToolTipText("下一首");

        nextSongButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Music.nowThread == null)
                    return;
                Music toBePlayed = musicList.getMusic(0);
                if (playModeState == 2) {
                    Random random = new Random();
                    int index = (int) (random.nextDouble() * musicList.size());
                    toBePlayed = musicList.getMusic(index);
                } else {
                    if (musicList.getMusic(musicList.size() - 1).equals(nowMusic)) {
                        toBePlayed = musicList.getMusic(0);
                    } else {
                        for (int i = 0; i < musicList.size() - 1; i++) {
                            if (musicList.getMusic(i).equals(nowMusic)) {
                                toBePlayed = musicList.getMusic(i + 1);
                                break;
                            }
                        }
                    }
                }
                setSuspendIcon(1);
                toBePlayed.play();
            }
        });
    }

    public void currentTimeLabelSetting() {
        currentTimeLabel = new JLabel();
        currentTimeLabel.setText("00:00");
        currentTimeLabel.setFont(new Font("微软雅黑", Font.PLAIN, 9));
        currentTimeLabel.setBounds(170, 43, 30, 30);
        add(currentTimeLabel);
    }

    public void musicProgressBarSetting() {
        musicProgressBar = new JProgressBar();
        musicProgressBar.setBounds(200, 56, 400, 5);
        add(musicProgressBar);
    }

    public void totalTimeLabelSetting() {
        totalTimeLabel = new JLabel();
        totalTimeLabel.setText("00:00");
        totalTimeLabel.setFont(new Font("微软雅黑", Font.PLAIN, 9));
        totalTimeLabel.setBounds(605, 43, 30, 30);
        add(totalTimeLabel);
    }

    public void resetCurrentTimeLabel(String currentTimeLabelStr) {
        currentTimeLabel.setText(currentTimeLabelStr);
    }

    public void resetTotalTimeLabel(String totalTimeLabelStr) {
        totalTimeLabel.setText(totalTimeLabelStr);
    }

    //设置面板参数
    public void setPanel() {
        setBounds(170, 550, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setBackground(Color.WHITE);                         //背景颜色设置为白色

        setBorder(new EtchedBorder(EtchedBorder.RAISED));   //画出边界
        setLayout(null);                                    //绝对布局

        //当前时间设置
        currentTimeLabelSetting();
        //音乐进度条设置
        musicProgressBarSetting();
        //总时间设置
        totalTimeLabelSetting();
        //音乐盒右部分设置
        //音量设置
        volumeButton = new JButton();
        volumeButton.setFocusPainted(false);
        volumeButton.setContentAreaFilled(false);
        volumeButton.setBorder(null);
        volumeButton.setBounds(645, 47, 21, 21);
        volumeButton.setIcon(Data.labaIcon);
        add(volumeButton);

        //音量进度条设置
        volumeProgressBar = new JProgressBar();
        volumeProgressBar.setBounds(670, 56, 80, 5);
        add(volumeProgressBar);

        //音乐列表按钮
        musicListSetting();

        //添加歌手、歌名标签组件，并设置参数

        //歌手参数设置
        singerNameLabelSetting();
        //歌名参数设置
        songNameLabelSetting();

        //音乐栏中间部分设置

        //播放模式按钮设置
        playModeButtonSetting();
        //上一首按钮设置
        lastSongButtonSetting();
        //下一首按钮设置
        nextSongButtonSetting();
        //开始、停止按钮设置
        suspendOrStartButtonSetting();
    }

    public void setLabels() {
        songNameLabel.setText(nowMusic.getSongName());
        singerNameLabel.setText(nowMusic.getSingerName() == "null" ? "未知歌手" : nowMusic.getSingerName());
        resetCurrentTimeLabel("00:00");
        resetTotalTimeLabel(nowMusic.getTotalTime());
        repaint();
    }
}


