package login.Register.loginRegister.DataAccessController;

import login.Register.loginRegister.Entity.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
@PreAuthorize("hasRole('Client')")
public class ClientDataController {

}
