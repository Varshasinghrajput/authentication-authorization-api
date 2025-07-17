package login.Register.loginRegister.DataAccessController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import login.Register.loginRegister.Entity.Client;
import login.Register.loginRegister.Entity.Users;
import login.Register.loginRegister.Service.DataService;
import login.Register.loginRegister.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
@PreAuthorize("hasAnyRole('CLIENT', 'AGENT', 'ADMIN')")
@SecurityRequirement(name = "bearerAuth")
public class ClientDataController {

    @Autowired
    private DataService dataService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

//    @GetMapping("/profile")
//    public ResponseEntity<Client> getClient(@AuthenticationPrincipal UserDetails userDetails) {
//        return ResponseEntity.ok(dataService.getClientByMobileNo(userDetails.getUsername()));
//    }
    @GetMapping("/client-profile/{clientMobileNo}")
    public ResponseEntity<Users> getClient(@PathVariable String clientMobileNo) {
        return ResponseEntity.ok(dataService.getClientByMobileNo(clientMobileNo));
    }

}
