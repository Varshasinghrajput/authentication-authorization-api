package login.Register.loginRegister.Controller;

import login.Register.loginRegister.Dto.AgentRequest;
import login.Register.loginRegister.Dto.AgentResponse;
import login.Register.loginRegister.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/agent")
@PreAuthorize("hasRole('AGENT')")
public class ClientController {

    @Autowired
    private ClientService clientService;


    @PostMapping("/addClient")
    public ResponseEntity<AgentResponse> addClient(@RequestBody AgentRequest agentRequest) {
        return clientService.addClientByAgent(agentRequest);
    }


}
