package login.Register.loginRegister.DataAccessController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import login.Register.loginRegister.Dto.ClientDto;
import login.Register.loginRegister.Entity.Client;
import login.Register.loginRegister.Service.DataService;
import login.Register.loginRegister.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agent")
@PreAuthorize("hasAnyRole('AGENT', 'ADMIN')")
@SecurityRequirement(name = "bearerAuth")
public class AgentDataController {

    @Autowired
    private DataService dataService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    //fetch client for logged-in agent
//    @GetMapping("/clients/{}")
//    public ResponseEntity<List<Client>> getClientsForAgent(@AuthenticationPrincipal UserDetails userDetails ) {
//        return ResponseEntity.ok(dataService.getAllClientsByAgent(userDetails.getUsername()));
//    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @GetMapping("/clients")
    public ResponseEntity<List<ClientDto>> getClientsForAgent(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(dataService.getAllClientsByAgent(userDetails.getUsername()));
    }

//    @CrossOrigin(origins = "http://127.0.0.1:5500")
//    @GetMapping("/clients/{agentMobileNo}")
//    public ResponseEntity<List<ClientDto>> getClientsForAgent(@PathVariable String agentMobileNo) {
//        return ResponseEntity.ok(dataService.getAllClientsByAgent(agentMobileNo));
//    }


}
