package MediaPlayer.CustomerCenter.ToolComponent;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static java.lang.Character.isLetterOrDigit;

/**
 * @author Zhejian Lai
 * @Description //TODO
 * @date 2021-07-20-14:24
 */
public class MyPasswordField extends JPasswordField {
    private String defaultString;
    private Border defaultBorder;

    public MyPasswordField(String defaultString, Border border) {
        this.defaultString = defaultString;
        setFont(new Font("Serif", Font.PLAIN, 16));
        //获取初始数据
        char defaultChar = getEchoChar();
        defaultBorder = getBorder();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c = e.getKeyChar();
                if (!isLetterOrDigit(c) && c != '_' && c != '?' && c != '.' && c != '\n' && c != '\b') {
                    JOptionPane.showMessageDialog(MyPasswordField.this, "只能输入A-Z,a-z,0-9,_.?", "提示", JOptionPane.INFORMATION_MESSAGE);
                    String total = new String(getPassword());
                    total = total.substring(0, -1);
                    setText(total);
                }
            }
        });
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(getPassword()).trim().equals(defaultString)) {
                    setText("");
                    setForeground(Color.black);
                    setEchoChar(defaultChar);
                }
                setBorder(border);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (new String(getPassword()).trim().equals("")) {
                    setText(defaultString);
                    setForeground(Color.gray);
                    setEchoChar('\0');
                }
                setBorder(defaultBorder);
            }
        });
    }

    public void reload(){
        setText(defaultString);
        setForeground(Color.gray);
        setEchoChar('\0');
    }
}
