package common;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by green on 6/17/2019.
 */
public class ImageUtils {

    public static int PROFILE_IMG_SIZE = 150;
    public static int THUMBNAIL_IMG_SIZE = 150;

    public byte[] createThumbnail(byte[] img, int height, int width) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(img);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bufferedImage.createGraphics().drawImage(ImageIO.read(bis).getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", baos);
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
        return imageInByte;
    }


    public byte[] getDefaultProfileImg() throws IOException{
        File f = new File( "resources/1.jpg");
        return createThumbnail(Files.readAllBytes(f.toPath()), PROFILE_IMG_SIZE, PROFILE_IMG_SIZE);

    }

    public byte[] getDefaultAlbumImage() throws IOException {
        File f = new File("resources/default.png");
        return createThumbnail(Files.readAllBytes(f.toPath()), THUMBNAIL_IMG_SIZE, THUMBNAIL_IMG_SIZE);
    }


}
