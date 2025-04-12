package login.Register.loginRegister.Service;


import jakarta.transaction.Transactional;
import login.Register.loginRegister.Entity.Users;
import login.Register.loginRegister.Entity.image;
import login.Register.loginRegister.Repository.ImageRepository;
import login.Register.loginRegister.Repository.UserRepository;
import login.Register.loginRegister.Util.ImageCompressionUtil;
import login.Register.loginRegister.Util.ZstdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

@Service
public class ImageService {

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Map<String, Object> uploadImage(String filename, InputStream fileInputStream , Users user , String userMobileNo ) throws IOException {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists() && !uploadDir.mkdirs()) {
            throw new IOException("Failed to create upload directory!");
        }

        byte[] originalData = fileInputStream.readAllBytes();
        if (originalData.length == 0) {
            return Map.of("error", "Uploaded file is empty!");
        }

        ByteArrayInputStream imageStream = new ByteArrayInputStream(originalData);
        BufferedImage image = ImageIO.read(imageStream);

        if (image == null) {
            return Map.of("error", "Invalid image format!");
        }

        byte[] webpData = ImageCompressionUtil.convertToWebP(image, 0.8f);
        byte[] compressedData = ZstdUtil.compressImage(webpData);

        File compressedFile = new File(UPLOAD_DIR, filename + ".webp.zst");
        try (FileOutputStream fos = new FileOutputStream(compressedFile)) {
            fos.write(compressedData);
        }


        image img = new image();
        img.setFilename(filename);
        img.setUser(user);
        img.setUserMobileNo(userMobileNo);
        imageRepository.save(img);


        Map<String, Object> response = new HashMap<>();
//        response.put("message", "Image uploaded successfully!");
//        response.put("originalSizeKB", originalData.length / 1024.0 + "KB");
//        response.put("webpSizeKB", webpData.length / 1024.0 + "KB");
//        response.put("compressedSizeKB", compressedData.length / 1024.0 + "KB");
//        response.put("filename", compressedFile.getName());
        response.put("message", "Image uploaded successfully!");
        response.put("originalSizeKB", String.format("%.2f KB", originalData.length / 1024.0));
        response.put("webpSizeKB", String.format("%.2f KB", webpData.length / 1024.0));
        response.put("compressedSizeKB", String.format("%.2f KB", compressedData.length / 1024.0));
        response.put("filename", compressedFile.getName());


        return response;
    }

    public byte[] retrieveImage(String filename) throws IOException {
        File compressedFile = new File(UPLOAD_DIR, filename);
        if (!compressedFile.exists()) {
            return null;
        }

        byte[] compressedData = Files.readAllBytes(compressedFile.toPath());
        return ZstdUtil.decompressImage(compressedData);
    }
}

