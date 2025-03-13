package login.Register.loginRegister.DataAccessController;

import login.Register.loginRegister.Entity.Users;
import login.Register.loginRegister.Service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('Admin')")
public class AdminDataController {

    @Autowired
    private DataService dataService;

    @GetMapping("/all-users")
    public ResponseEntity<List<Users>> getAllUsers() {
        return ResponseEntity.ok(dataService.getAllUsers());
    }
}
