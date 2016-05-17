package simplejavatexteditor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

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
    public PixelImage (ColorModel cm,
                       WritableRaster raster,
                       boolean isRasterPremultiplied,
                       Hashtable<?,?> properties) {
        super(cm, raster, isRasterPremultiplied, properties);
    }
    public  PixelImage resizeImage(BufferedImage image, int width, int height) {
        ColorModel cm = image.getColorModel();
        WritableRaster raster = cm.createCompatibleWritableRaster(width, height);
        boolean isRasterPremultiplied = cm.isAlphaPremultiplied();

        PixelImage target = new PixelImage(cm, raster, isRasterPremultiplied, null);
        Graphics2D g2 = target.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        double scalex = (double) target.getWidth()/ image.getWidth();
        double scaley = (double) target.getHeight()/ image.getHeight();

        AffineTransform xform = AffineTransform.getScaleInstance(scalex, scaley);
        g2.drawRenderedImage(image, xform);
        g2.dispose();
        return target;

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
    public PixelImage display(JTextPaneDrop area)  throws IOException{
        PixelImage temp = this;
        Dimension screenSize = area.getSize();
        //change size of image
        if (temp.getWidth() > temp.getHeight()) {
            double koef = (double) temp.getWidth() / temp.getHeight();
            temp =  temp.resizeImage(this, (int)screenSize.getWidth() , (int) ((screenSize.getWidth()) / koef ));
        } else {
            double koef = (double) temp.getHeight() / temp.getWidth();

            temp = temp.resizeImage(this, (int) ((screenSize.getHeight())/ koef), (int)screenSize.getHeight());
        }

        return temp;
    }
}
