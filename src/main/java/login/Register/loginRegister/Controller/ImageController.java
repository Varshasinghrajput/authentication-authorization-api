package login.Register.loginRegister.Controller;

import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/images")
public class ImageController {
    private static final String upload_dir = "images/";  // folder jisme image sve hogi

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestPart("file") MultipartFile file) {
        try{

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String agentMobileNo = authentication.getName();

            if(agentMobileNo == null || agentMobileNo == ""){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Agent MobileNo");
            }

            // step 1 : ensure "images" folder exist
            File uploadDir = new File(upload_dir);
            if(!uploadDir.exists()){
                uploadDir.mkdir();   // agar folder nahi hai toh isse ban jayega
            }

            //step 2 : file ko "images" folder me save karo
            Path filePath = Paths.get(upload_dir + file.getOriginalFilename());
            file.transferTo(filePath);

            return ResponseEntity.ok("Image uploaded successfully by "+ agentMobileNo+ " : " +file.getOriginalFilename());
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed");
        }
    }

    @GetMapping("/{filename}")
    public ResponseEntity<?> getImage(@PathVariable String filename) {

        try {
            //System.getProperty("user.dir") project root directory return karega.
            //Paths.get(...) se image ka full path generate hoga.
            //UrlResource file ko read karega.
            Path filePath = Paths.get(System.getProperty("user.dir"), "images", filename); // Requested image ka path generate karta hai.
            UrlResource resource = new UrlResource(filePath.toUri()); //File ko readable resource me convert karta hai.

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Image not found: " + filename);
            }

            // Fix MIME type detection
            //MIME ka full form Multipurpose Internet Mail Extensions hai. Ye file ka type (content type) identify karne ka ek standard format hai.
            // MIME type batata hai ki browser ya application kisi file ko kaise handle kare.

            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
                    contentType = "image/jpeg";
                } else if (filename.endsWith(".png")) {
                    contentType = "image/png";
                } else if (filename.endsWith(".gif")) {
                    contentType = "image/gif";
                } else {
                    contentType = "application/octet-stream";
                }
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace(); // Print actual error for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving image: " + e.getMessage());
        }

    }
}
