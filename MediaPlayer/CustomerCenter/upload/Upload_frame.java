package MediaPlayer.CustomerCenter.upload;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetterOrDigit;

import MediaPlayer.Client;
import MediaPlayer.CustomerCenter.CustomerCenter;
import MediaPlayer.CustomerCenter.ToolComponent.MyPasswordField;
import MediaPlayer.CustomerCenter.ToolComponent.MyTextField;
import MediaPlayer.CustomerCenter.userInformation.User;
import MediaPlayer.MainSceneFrame;
import MediaPlayer.Start;
import MediaPlayer.Tool.GBC;

/**
 * @author Zhejian Lai
 * @Description //TODO
 * @date 2021-07-02-18:09
 */

public class Upload_frame extends JDialog {
    //窗口说明标签
    public JLabel label;
    //用户名文本输入框
    public MyTextField account;
    //用户密码文本输入框
    public MyPasswordField password;
    //下次自动登录选择框
    private JCheckBox autoLogin;
    //登录按钮
    private JButton login;
    //注册按钮
    private JButton register;
    //注册窗口
    private JDialog registerWindow;
    private MyTextField registerAccount;
    private MyPasswordField registerPassword;
    private MyPasswordField registerPasswordAssure;
    private JButton registerButton;
    //默认文本
    private static final String intro_acc = "账户名称";
    private static final String intro_pwd = "密码";
    private static final String regis_acc = "注册账户名称";
    private static final String regis_pwd = "请再次输入密码";

    //数据库数据查找
    public Search_Calculate search_calculate = new Search_Calculate();

    public Upload_frame(JFrame owner, String title, boolean modal) {
        super(owner, title, modal);
        //初始化登陆对话框
        init();
    }

    public void init() {
        //设置窗口大小
        dialogSetting();

        //窗口说明标签设置
        labelSetting();

        //用户名文本框设置
        accountAndPasswordSetting();

        //自动登陆选择按钮设置
        autoLoginSetting();

        //登陆按钮设置
        loginSetting();

        //注册按钮设置
        registerSetting();

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                registerWindow.setVisible(false);
            }
        });

        this.setTitle("共享音乐登陆");
        //this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(false);
    }

    public void dialogSetting() {        //窗口全局设置函数
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setLocation(screenSize.width / 4, screenSize.height / 4);
        setPreferredSize(new Dimension(screenSize.width / 2, screenSize.height / 2));
        pack();

        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(238, 255, 255));
    }

    public void registerSetting() {
        //注册按钮设置
        register = new JButton("没有账户？请先注册");
        register.setFont(new Font("Serif", Font.PLAIN, 16));
        register.setBackground(new Color(51, 122, 183));
        register.setForeground(Color.white);

        /**
         *添加监视器
         */
        register.addActionListener(e -> {

            //清空原有数据
            registerAccount.reload();
            registerPassword.reload();
            registerPasswordAssure.reload();

            //注册按钮
            registerButton.requestFocus();

            registerWindow.setVisible(true);
            //未注册则写入数据库，或者根据已注册用户名 登陆
            //然后写上当前用户的用户名
            //data.nickname=查找到的昵称或当前注册的;
            //data.up_Load_state=true;  /*修改登陆状态*/
            //之后查结果
        });

        add(register, new GBC(0, 5).
                setInsets(0, getWidth() * 10 / 27, 10, getWidth() * 10 / 27).
                setFill(GBC.HORIZONTAL));

        //注册窗口
        registerWindow = new JDialog(this, "注册窗口");
        registerWindow.setPreferredSize(new Dimension(500,500));
        registerWindow.pack();
        registerWindow.setLayout(new GridBagLayout());
        registerWindow.setLocationRelativeTo(null);

        Border border = BorderFactory.
                createLineBorder(new Color(102, 175, 233), 2, true);
        registerAccount = new MyTextField(regis_acc, border);
        registerWindow.add(registerAccount, new GBC(0, 0).setInsets(0, registerWindow.getWidth() / 4, 10, registerWindow.getWidth() / 4)
                .setFill(GBC.BOTH));

        registerPassword = new MyPasswordField(intro_pwd, border);
        registerWindow.add(registerPassword, new GBC(0, 1).setInsets(0, registerWindow.getWidth() / 4, 10, registerWindow.getWidth() / 4)
                .setFill(GBC.HORIZONTAL).setWeights(100, 0));

        registerPasswordAssure = new MyPasswordField(regis_pwd, border);
        registerWindow.add(registerPasswordAssure, new GBC(0, 2).setInsets(0, registerWindow.getWidth() / 4, 10, registerWindow.getWidth() / 4)
                .setFill(GBC.HORIZONTAL).setWeights(100, 0));

        registerButton = new JButton("注册");
        registerButton.setFont(new Font("Serif", Font.PLAIN, 16));
        registerButton.setBackground(new Color(51, 122, 183));
        registerButton.setForeground(Color.white);
        registerButton.setFocusPainted(false);
        /**
         *添加监视器
         */

        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = registerAccount.getText();
                String password = String.valueOf(registerPassword.getPassword());
                String passwordAssure = String.valueOf(registerPasswordAssure.getPassword());
                if(passwordAssure.equals(password)){
                    int state = Client.regist(userName,password,userName);
                    if(state == 1){
                        JDialog isRegister = new JDialog();
                        isRegister.setSize(200,100);
                        isRegister.add(new JLabel("注册成功"));
                        isRegister.setModal(true);
                        isRegister.setLocationRelativeTo(null);
                        isRegister.setVisible(true);
                        System.out.println("注册成功");
                    }else{
                        JDialog isRegister = new JDialog();
                        isRegister.setSize(200,100);
                        isRegister.add(new JLabel("注册失败（用户名已经存在）"));
                        isRegister.setModal(true);
                        isRegister.setLocationRelativeTo(null);
                        isRegister.setVisible(true);
                        System.out.println("注册失败（用户名已经存在）");
                    }
                }else{
                    JDialog isRegister = new JDialog();
                    isRegister.setSize(200,100);
                    isRegister.add(new JLabel("两次输入密码不一致"));
                    isRegister.setModal(true);
                    isRegister.setLocationRelativeTo(null);
                    isRegister.setVisible(true);
                    System.out.println("两次输入密码不一致");
                }
            }
        };

        registerButton.addActionListener(action);
        InputMap imap = login.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        imap.put(KeyStroke.getKeyStroke("ENTER"), "register");
        registerButton.getActionMap().put("register", action);

        registerWindow.add(registerButton, new GBC(0, 3).
                setInsets(0, registerWindow.getWidth() / 4, 10, registerWindow.getWidth() / 4)
                .setFill(GBC.HORIZONTAL).setWeights(100, 0));
        registerWindow.pack();
    }

    public void labelSetting() {     //窗口说明标签设置
        label = new JLabel("登录窗口");
        label.setFont(new Font("微软雅黑", Font.PLAIN, 26));
        add(label, new GBC(0, 0).
                setInsets(0, getWidth() * 10 / 27,
                        0, getWidth() * 10 / 27)
                .setAnchor(GBC.WEST).setWeights(100, 0));
    }

    public void accountAndPasswordSetting() {       //用户名文本框设置

        Border border = BorderFactory.
                createLineBorder(new Color(102, 175, 233), 2, true);

        account = new MyTextField(intro_acc, border);
        add(account, new GBC(0, 1).
                setInsets(10, getWidth() * 10 / 27, 5, getWidth() * 10 / 27).
                setFill(GBC.BOTH));

        password = new MyPasswordField(intro_pwd, border);
        add(password, new GBC(0, 2).
                setInsets(0, getWidth() * 10 / 27, 10, getWidth() * 10 / 27).
                setFill(GBC.HORIZONTAL));
    }

    public void autoLoginSetting() {     //自动登录按钮设置
        autoLogin = new JCheckBox("下次自动登录");
        //添加监视器
//        autoLogin.addActionListener();
        autoLogin.setBackground(new Color(238, 255, 255));
        add(autoLogin, new GBC(0, 3).
                setInsets(0, getWidth() * 10 / 27, 10, getWidth() * 10 / 27).
                setAnchor(GBC.WEST));
    }

    public void loginSetting() {     //登陆按钮设置
        login = new JButton("登录");
        login.setFont(new Font("Serif", Font.PLAIN, 16));
        login.setBackground(new Color(51, 122, 183));
        login.setForeground(Color.white);
        login.setFocusPainted(false);
        /**
         *添加监视器
         */

        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nowAccount = account.getText();
                String nowPassword = String.valueOf(password.getPassword());

                //用户不存在返回-1，密码错误返回-2
                int state = Client.login(nowAccount, nowPassword);
                if (state == -1) {
                    System.out.println("用户不存在");
                } else if (state == -2) {
                    System.out.println("密码错误");
                    password.setText("");
                } else {
                    System.out.println("登陆成功");
                    User.up_Load_state = true;
                    User.nowUser = new User(state);
                    User.setUserId(state);
                    setVisible(false);
                    MainSceneFrame.musicPanel.loadLists();
                }
            }
        };

        login.addActionListener(action);
        InputMap imap = login.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        imap.put(KeyStroke.getKeyStroke("ENTER"), "login");
        login.getActionMap().put("login", action);

        add(login, new GBC(0, 4).
                setInsets(0, getWidth() * 10 / 27, 10, getWidth() * 10 / 27).
                setFill(GBC.HORIZONTAL));
        pack();
        label.requestFocus();
    }
}
