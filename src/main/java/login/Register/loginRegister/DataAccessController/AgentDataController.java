package login.Register.loginRegister.DataAccessController;

import login.Register.loginRegister.Entity.Client;
import login.Register.loginRegister.Service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/agent")
@PreAuthorize("hasAnyRole('Agent', 'Admin')")
public class AgentDataController {

    @Autowired
    private DataService dataService;

    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getClientsForAgent() {
        return ResponseEntity.ok(dataService.getAllClients());
    }
}
