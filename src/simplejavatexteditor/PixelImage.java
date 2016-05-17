package simplejavatexteditor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by lizarusi on 12.05.16.
 * class wich implements MyImage interface
 */
public class PixelImage extends BufferedImage implements MyImage {
    public PixelImage(int width,
                      int height,
                      int imageType){
        super(width, height, imageType);
    }
    public static PixelImage load(File file)throws IOException
    {

        final BufferedImage buf = ImageIO.read(file);

        if (buf == null)
        {
            throw new IOException("File did not contain a valid image: " + file);
        }

        final PixelImage image =
                new PixelImage(buf.getWidth(), buf.getHeight(), BufferedImage.TYPE_INT_RGB);
        final Graphics g = image.getGraphics();
        g.drawImage(buf, 0, 0, null);
        return image;
    }
    @Override
    public PixelImage display(JTextTest area)  throws IOException{
        PixelImage temp = this;
        return temp;
    }
}
