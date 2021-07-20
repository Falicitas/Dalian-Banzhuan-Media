package MediaPlayer.CustomerCenter.ToolComponent;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * @author Zhejian Lai
 * @Description //TODO
 * @date 2021-07-20-14:30
 */
public class MyTextField extends JTextField {
    private String defaultString;
    private Border defaultBorder;

    public MyTextField(String defaultString, Border border){
        this.defaultString = defaultString;

        setFont(new Font("Serif", Font.PLAIN, 16));
        reload();
        defaultBorder = getBorder();

        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().trim().equals(defaultString)) {
                    setText("");
                    setForeground(Color.black);
                }
                setBorder(border);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().trim().equals("")) {
                    setText(defaultString);
                    setForeground(Color.gray);
                }
                setBorder(defaultBorder);
            }
        });
    }

    public void reload(){
        setText(defaultString);
        setForeground(Color.gray);
    }
}
