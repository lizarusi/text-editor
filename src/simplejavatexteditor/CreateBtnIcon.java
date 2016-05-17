package simplejavatexteditor;

import javax.swing.*;

/**
 * Created by lizarusi on 17.05.16.
 */
public class CreateBtnIcon {
    public JButton factoryMethod(ImageIcon icon, String text, JToolBar elem, UI frame){
        JButton btn = new BtnIcon(icon, text);
        elem.add(btn);
        elem.addSeparator();
        btn.addActionListener(frame);
        return btn;
    }
}
