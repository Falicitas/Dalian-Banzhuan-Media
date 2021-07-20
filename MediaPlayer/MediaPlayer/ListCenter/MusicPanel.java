package MediaPlayer.ListCenter;


import MediaPlayer.Client;
import MediaPlayer.CustomerCenter.userInformation.User;
import MediaPlayer.MainSceneFrame;
import MediaPlayer.Music.Music;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;

/**
 * @author Zhejian Lai
 * @Description //TODO
 * @date 2021-07-07-22:47
 */
public class MusicPanel extends JPanel {

    public static final int DEFAULT_WIDTH = 170;
    public static final int DEFAULT_HEIGHT = 670;

    public static ArrayList<MusicListForShow> list = new ArrayList<>();
    private static MusicItemForShow curItem = null;
    public static MusicListForShow curList = null;

    private MusicListDetailPanel musicListDetailPanel = new MusicListDetailPanel();
    private MainSceneFrame parent;
    private JDialog dialog = new JDialog(parent, "添加歌单", true);
    JTextField dialogField = new JTextField(20);


    //歌单项
    public class MusicListForShow extends JPanel implements Cloneable {
        boolean open = false;
        private JLabel label;
        private String listName;
        private int id;
        ArrayList<MusicItemForShow> items = new ArrayList<>();


        MusicListForShow(String listName, int id) {
            setPreferredSize(new Dimension(170, 35));

            //设置歌单项表现样式
            setBorder(BorderFactory.createLineBorder(Color.black));

            //设置歌单项名称
            this.id = id;
            this.listName = listName;
            label = new JLabel(listName);
            add(label);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == 1) {
                        //设置单击代表选中(双击兼容单击)
                        if (e.getClickCount() >= 1) {
                            if (!open) {
                                if (curList != null)
                                    curList.setBorder(BorderFactory.createLineBorder(Color.black));
                                curList = MusicListForShow.this;
                                curList.setBorder(BorderFactory.createLineBorder(new Color(102, 175, 233), 2));
                            }
                        } //设置双击打开/关闭歌单
                        if (e.getClickCount() >= 2) {
                            open = !open;
                            musicListDetailPanel.setVisible(open);

                            if (open) {
                                //将歌单项框设置回黑色
                                curList.setBorder(BorderFactory.createLineBorder(Color.black));
                                try {
                                    musicListDetailPanel.loadList((MusicListForShow) MusicListForShow.this.clone());
                                } catch (CloneNotSupportedException cloneNotSupportedException) {
                                    cloneNotSupportedException.printStackTrace();
                                }
                            } else {
                                curList = null;
                            }
                        }
                    }
                    e.consume();
                }

                public void mouseEntered(MouseEvent e) {
                    if (!open && curList == null) {
                        MusicListForShow.this.setBorder(BorderFactory.createLineBorder(new Color(102, 175, 233)));
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (!open && curList == null)
                        MusicListForShow.this.setBorder(BorderFactory.createLineBorder(Color.black));
                }
            });

        }

        public void loadMusic() {
            //测试

            Vector<Music> v = new Vector<>();
            //导入歌单项
            Client.AlbumMusic(id, v);

            for (Music i : v) {
                items.add(new MusicItemForShow(i));
            }
        }

        public void add(Music music, Boolean flag) {
            MusicItemForShow item = new MusicItemForShow(music);
            items.add(item);
            //只有当前打开的列表 添加歌曲 才需要在界面中进行实时添加控间
            if (flag) {
                musicListDetailPanel.add(item);
                parent.pack();
            }
        }

        public String getListName() {
            return listName;
        }

        public int getId() {
            return id;
        }
    }

    //歌曲项
    class MusicItemForShow extends JPanel {
        private JLabel label;
        private Music music;
        private JPopupMenu menu;
        private JMenuItem item;

        public Music getMusic() {
            return music;
        }

        public void resetPlayerPanelMusicList() {
            ArrayList<Music> musics = new ArrayList<>();
            for (int i = 0; i < curList.items.size(); i++) {
                musics.add(curList.items.get(i).getMusic());
            }
            //设置当前播放列表名称
            Music.panel.getMusicList().setMusicListName(curList.getListName());
            //导入当前列表歌曲
            Music.panel.getMusicList().resetList(musics);
        }

        MusicItemForShow(Music music) {
            setPreferredSize(new Dimension(150, 35));

            //设置样式
            setBorder(BorderFactory.createEtchedBorder());
            this.music = music;

            label = new JLabel(music.getSongName());
            add(label);

            menu = new JPopupMenu();
            item = new JMenuItem("删除");
            menu.add(item);

            item.addActionListener(e->{
                curList.items.remove(MusicPanel.MusicItemForShow.this);

                parent.pack();
            });

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //设置单击代表选中
                    if (e.getClickCount() >= 1) {
                        if (curItem != null) {
                            curItem.setBorder(BorderFactory.createEtchedBorder());
                        }
                        curItem = MusicItemForShow.this;
                        curItem.setBorder(BorderFactory.createLineBorder(new Color(102, 175, 233), 2));
                    } //设置双击打开/关闭歌单
                    if (e.getClickCount() >= 2) {
                        //打开歌曲
                        music.play();
                        System.out.println(label.getText() + "正在播放");
                        resetPlayerPanelMusicList();
                        e.consume();
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (e.isPopupTrigger()) {

                        //menu.show(getParent(), e.getX() + 16, getY() + 11 + e.getY());
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    if (curItem == null)
                        MusicItemForShow.this.setBorder(BorderFactory.createLineBorder(new Color(102, 175, 233)));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (curItem == null)
                        MusicItemForShow.this.setBorder(BorderFactory.createEtchedBorder());
                }
            });
        }


    }


    public MusicPanel(MainSceneFrame parent) {
        this.parent = parent;
        setBounds(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 2));

        //加载歌单项
        //loadLists();

        //添加子显示面板
        parent.add(musicListDetailPanel);
        musicListDetailPanel.setVisible(false);

        //添加歌单展示
        initDialog();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == 3 && User.getUserId() != -1) {
                    dialogField.setText("请输入歌单名");
                    dialogField.setForeground(Color.gray);

                    //居中显示
                    Toolkit toolkit = Toolkit.getDefaultToolkit();
                    dialog.setLocation(((int) toolkit.getScreenSize().getWidth() - dialog.getWidth()) / 2, ((int) toolkit.getScreenSize().getHeight() - dialog.getHeight()) / 2);
                    dialog.setVisible(true);
                }
            }
        });
    }

    void initDialog() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        dialog.setContentPane(panel);
        dialog.setSize(300, 100);
        //设置文本域
        panel.add(dialogField);
        //设置UI
        dialogField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (dialogField.getText().trim().equals("请输入歌单名")) {
                    dialogField.setForeground(Color.black);
                    dialogField.setText("");
                }
            }
        });

        dialogField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (dialogField.getText().trim().equals("请输入歌单名")) {
                    dialogField.setForeground(Color.black);
                    dialogField.setText("");
                    if (e.getKeyChar() == '\b')
                        e.consume();
                }
            }
        });

        //设置按钮

        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
                //添加歌单列表
                if (dialogField.getText().trim().equals("")) return;
                int albumId = Client.createAlbum(User.getUserId(), dialogField.getText());
                addMusicList(new MusicListForShow(dialogField.getText(), albumId));
                System.out.println("添加歌单 " + dialogField.getText());
            }
        };

        //确认
        JButton checkButton = new JButton("确定");
        checkButton.setFocusPainted(false);
        checkButton.addActionListener(action);
        panel.add(checkButton);

        InputMap imap = dialogField.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        imap.put(KeyStroke.getKeyStroke("ENTER"), "check");
        dialogField.getActionMap().put("check", action);

        //取消
        JButton cancelButton = new JButton("取消");
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(e -> dialog.setVisible(false));
        panel.add(cancelButton);
    }

    //添加音乐歌单
    void addMusicList(MusicListForShow musicListForShow) {

        //显示歌单
        add(musicListForShow);
        //当前打开歌单添加
        list.add(musicListForShow);
        parent.pack();
    }

    public void loadLists() {
        //测试

        Vector<Integer> v_id = new Vector<>();
        Vector<String> v_name = new Vector<>();

        Client.getAlbum(User.getUserId(), v_id, v_name);

        for (int i = 0; i < v_id.size(); ++i) {
            MusicListForShow li = new MusicListForShow(v_name.get(i), v_id.get(i));
            list.add(li);
            //parent.validate();
            //显示
            add(li);
            //调入该歌单所有歌曲
            li.loadMusic();
        }

        parent.pack();
    }


}


