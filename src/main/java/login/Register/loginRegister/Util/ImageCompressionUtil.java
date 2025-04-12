package login.Register.loginRegister.Util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ImageCompressionUtil {

    public static byte[] convertToWebP(BufferedImage image, float quality) throws IOException {
        //  Save input image as temporary file
        File tempInputFile = File.createTempFile("input", ".png");
        File tempOutputFile = File.createTempFile("output", ".webp");

        //  Save BufferedImage to PNG format (cwebp needs a file as input)
        javax.imageio.ImageIO.write(image, "png", tempInputFile);

        //  Run cwebp command
        ProcessBuilder pb = new ProcessBuilder(
                "cwebp", "-q", String.valueOf((int) (quality * 100)),
                tempInputFile.getAbsolutePath(), "-o", tempOutputFile.getAbsolutePath()
        );
        pb.redirectErrorStream(true);
        Process process = pb.start();
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
            throw new IOException("Process execution interrupted", e);
        }

        //  Check if conversion was successful
        if (!tempOutputFile.exists() || tempOutputFile.length() == 0) {
            throw new IOException("WebP conversion failed! Output file is empty.");
        }

        //  Read WebP bytes
        byte[] webpData = new byte[(int) tempOutputFile.length()];
        try (FileInputStream fis = new FileInputStream(tempOutputFile)) {
            fis.read(webpData);
        }

        //  Cleanup
        tempInputFile.delete();
        tempOutputFile.delete();

        return webpData;
    }
}


