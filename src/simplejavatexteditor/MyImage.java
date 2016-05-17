package simplejavatexteditor;

import java.io.IOException;

/**
 * Created by lizarusi on 12.05.16.
 */
public interface MyImage {
    /**
     * method for loading image to text editor
     * @param area
     */
    public PixelImage display(JTextPaneDrop area) throws IOException;

}
