package simplejavatexteditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by lizarusi on 12.05.16.
 */
public class PixelImageProxy implements MyImage{
    PixelImage image;
    File file_;
    JDialog dialog;
    public PixelImageProxy(File file){
        file_ = file;
    }
    @Override
    public PixelImage display(JTextTest area) throws IOException{
        if(image != null) {
            // modify the image
            image = image.display(area);
        }
        else {
            JOptionPane.showMessageDialog(null, "The program use lazy load, your picture will be load after 2 seconds," +
                                          " press 'OK' to continue");
            //load the real image
            image = PixelImage.load(file_);
            try {
                // to delay setting icon for lazy load
                Thread.currentThread().sleep(2000);

                area.insertIcon(new ImageIcon(image));

            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        return image;
    }
    private ActionListener closeJDialog = new ActionListener() {

        public void actionPerformed(ActionEvent e) {

            if (dialog.isShowing()) {
                dialog.dispose();
                System.out.println("dialog disposed");
            }
        }
    };

}
