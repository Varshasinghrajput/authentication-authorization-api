package login.Register.loginRegister.DataAccessController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import login.Register.loginRegister.Dto.ClientDto;
import login.Register.loginRegister.Entity.Client;
import login.Register.loginRegister.Entity.Users;
import login.Register.loginRegister.Repository.UserRepository;
import login.Register.loginRegister.Service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@SecurityRequirement(name = "bearerAuth")
public class AdminDataController {

    @Autowired
    private DataService dataService;
    @Autowired
    private UserRepository userRepository;

    //fetch all users
    @GetMapping("/all-users")
    public ResponseEntity<List<Users>> getAllUsers() {

        return ResponseEntity.ok(dataService.getAllUsers());
    }

    // fetch all clients
    @GetMapping("/all-clients")
    public ResponseEntity<List<ClientDto>> getAllClients() {

        return ResponseEntity.ok(dataService.getAllClients());
    }

    // fetch specific user
    @GetMapping("/specific-user/{userMobileNo}")
    public ResponseEntity<Users> getUserById(@PathVariable String userMobileNo) {
        return ResponseEntity.ok(dataService.getUserByMobileNo(userMobileNo));
    }

    // activate a user
    @PutMapping("/activate/{userMobileNo}")
    public ResponseEntity<String> activateUser(@PathVariable String userMobileNo) {
        Users user = userRepository.findByMobileNo(userMobileNo).orElseThrow(() -> new RuntimeException("User not found"));

        user.setActive(true); //activate the user
         userRepository.save(user);

         return ResponseEntity.ok("User activated successfully");
    }

    @PutMapping("/deactivate/{userMobileNo}")
    public ResponseEntity<String> deactivateUser(@PathVariable String userMobileNo) {
        Users user = userRepository.findByMobileNo(userMobileNo).orElseThrow(() -> new RuntimeException("User not found"));

        user.setActive(false); // deactivate the user
        userRepository.save(user);

        return ResponseEntity.ok("User deactivated successfully");
    }

}
