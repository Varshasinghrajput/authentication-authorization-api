package login.Register.loginRegister.Controller;

import login.Register.loginRegister.Dto.AgentRequest;
import login.Register.loginRegister.Dto.AgentResponse;
import login.Register.loginRegister.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agent")
public class ClientController {

    @Autowired
    private ClientService clientService;


    @PostMapping("/addClient")
    public ResponseEntity<AgentResponse> addClient(@RequestBody AgentRequest agentRequest) {
        return clientService.addClientByAgent(agentRequest);
    }


}
