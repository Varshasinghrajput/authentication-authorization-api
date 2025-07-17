package login.Register.loginRegister.Controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import login.Register.loginRegister.Entity.Users;
import login.Register.loginRegister.Repository.UserRepository;
import login.Register.loginRegister.Service.ImageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping("/images")
@PreAuthorize("hasRole('ADMIN')")
@SecurityRequirement(name = "bearerAuth")
public class ImageController {

    private final ImageService imageService;
    private final UserRepository userRepository;


    public ImageController(ImageService imageService, UserRepository userRepository) {
        this.imageService = imageService;
        this.userRepository = userRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadImage(@RequestParam("file") MultipartFile file) {
        //  Authenticate User
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userMobileNo = authentication.getName();

        if (userMobileNo == null || userMobileNo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid Agent MobileNo"));
        }

        //fetch user from database
        Users user = userRepository.findByMobileNo(userMobileNo).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "user not found"));
        }

        try (InputStream inputStream = file.getInputStream()) {
            Map<String, Object> response = imageService.uploadImage(file.getOriginalFilename(), inputStream , user, userMobileNo);

            if (response.containsKey("error")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Image upload failed: " + e.getMessage()));
        }
    }

    @GetMapping("/{filename}")
    public ResponseEntity<?> retrieveImage(@PathVariable String filename) {
        //  Authenticate User
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userMobileNo = authentication.getName();

        if (userMobileNo == null || userMobileNo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid Agent MobileNo"));
        }

        try {
            byte[] imageData = imageService.retrieveImage(filename);

            if (imageData == null || imageData.length == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Image not found or corrupted"));
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("image/webp"));
            headers.setContentLength(imageData.length);

            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to retrieve image: " + e.getMessage()));
        }
    }
}















//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.core.io.Resource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.imageio.IIOImage;
//import javax.imageio.ImageIO;
//import javax.imageio.ImageWriteParam;
//import javax.imageio.ImageWriter;
//import javax.imageio.stream.ImageOutputStream;
//import java.awt.image.BufferedImage;
//import java.io.*;
//import java.util.Iterator;
//
//@RestController
//@RequestMapping("/images")
//public class ImageController {
//    private static final String upload_dir = "images/";
//
//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadImage(@RequestPart("file") MultipartFile file) {
//        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String userMobileNo = authentication.getName();
//
//            if (userMobileNo == null || userMobileNo.isEmpty()) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Agent MobileNo");
//            }
//
//            // ensure images folder exists
//            File uploadDir = new File(upload_dir);
//            if (!uploadDir.exists()) {
//                uploadDir.mkdir();
//            }
//
//            // Validate image format
//            String originalFilename = file.getOriginalFilename();
//            if (originalFilename == null) {
//                return ResponseEntity.badRequest().body("Invalid file name");
//            }
//
//            String fileExtension = getFileExtension(originalFilename);
//            if (!fileExtension.matches("jpg|jpeg|png|gif")) {
//                return ResponseEntity.badRequest().body("Only JPG, JPEG, PNG, and GIF formats are allowed");
//            }
//
//            // Convert multipart image to BufferedImage
//            BufferedImage originalImage = ImageIO.read(file.getInputStream());
//
//            // Save compressed image (JPG) or store PNG/GIF as is
//            String compressedFilePath = upload_dir + originalFilename;
//            File compressedFile = new File(compressedFilePath);
//            long originalSize = file.getSize(); // Original size in bytes
//
//            // Apply compression only if it's JPG/JPEG
//            if (fileExtension.equals("jpg") || fileExtension.equals("jpeg")) {
//                float compressionQuality = 0.5f; // Lower value = higher compression
//                compressImage(originalImage, compressedFile, "jpg", compressionQuality);
//            } else {
//                // Directly save PNG & GIF without compression
//                ImageIO.write(originalImage, fileExtension, compressedFile);
//            }
//
//            long compressedSize = compressedFile.length(); // Compressed size in bytes
//
//            // Calculate size reduction percentage
//            double sizeReduction = ((double) (originalSize - compressedSize) / originalSize) * 100;
//
//            return ResponseEntity.status(HttpStatus.OK)
//                    .body("Image Uploaded Successfully. \nOriginal Size: " + originalSize / 1024 + " KB" +
//                            "\nFinal Size: " + compressedSize / 1024 + " KB" +
//                            "\nSize Reduced By: " + String.format("%.2f", sizeReduction) + "%");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed");
//        }
//    }
//
//    private void compressImage(BufferedImage image, File file, String format, float quality) throws IOException {
//        OutputStream os = new FileOutputStream(file);
//        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(format);
//
//        if (!writers.hasNext()) {
//            throw new IllegalStateException("No writers found for format: " + format);
//        }
//
//        ImageWriter writer = writers.next();
//        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
//        writer.setOutput(ios);
//
//        ImageWriteParam param = writer.getDefaultWriteParam();
//        if (param.canWriteCompressed()) {
//            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//            param.setCompressionQuality(quality);
//        }
//
//        writer.write(null, new IIOImage(image, null, null), param);
//        ios.close();
//        writer.dispose();
//    }
//
//    @GetMapping("/{filename}")
//    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
//        try {
//            File compressedFile = new File(upload_dir + filename);
//            if (!compressedFile.exists()) {
//                return ResponseEntity.notFound().build();
//            }
//
//            // Read stored file and return as a response
//            BufferedImage decompressedImage = ImageIO.read(compressedFile);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            String fileExtension = getFileExtension(filename);
//            ImageIO.write(decompressedImage, fileExtension, baos); // Convert BufferedImage to byte array
//
//            byte[] imageBytes = baos.toByteArray();
//            ByteArrayResource resource = new ByteArrayResource(imageBytes);
//
//            return ResponseEntity.status(HttpStatus.OK)
//                    .contentType(getMediaType(fileExtension))
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
//                    .body(resource);
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().build();
//        }
//    }
//
//    // Utility method to get file extension
//    private String getFileExtension(String filename) {
//        int lastDotIndex = filename.lastIndexOf('.');
//        if (lastDotIndex == -1 || lastDotIndex == filename.length() - 1) {
//            return "";
//        }
//        return filename.substring(lastDotIndex + 1).toLowerCase();
//    }
//
//    // Utility method to determine media type
//    private MediaType getMediaType(String extension) {
//        switch (extension) {
//            case "png":
//                return MediaType.IMAGE_PNG;
//            case "gif":
//                return MediaType.IMAGE_GIF;
//            default:
//                return MediaType.IMAGE_JPEG;
//        }
//    }
//}
