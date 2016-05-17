package simplejavatexteditor;

import javax.swing.*;

/**
 * Created by lizarusi on 17.05.16.
 */
public class BtnIcon extends JButton {
    public BtnIcon(ImageIcon icon, String text){
        super(icon);
        setToolTipText(text);
    }
}
