package simplejavatexteditor;

import javax.swing.*;

/**
 * Created by lizarusi on 17.05.16.
 */
public abstract class CreateBtnText extends CreateJButton{
    public JButton factoryMethod(String text, JToolBar elem, UI frame){
        JButton btn = new BtnText(text);
        elem.add(btn);
        elem.addSeparator();
        btn.addActionListener(frame);
        return btn;
    }
}
