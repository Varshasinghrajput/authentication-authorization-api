package login.Register.loginRegister.Service;

import login.Register.loginRegister.Dto.AgentRequest;
import login.Register.loginRegister.Dto.AgentResponse;
import login.Register.loginRegister.Entity.Client;
import login.Register.loginRegister.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ClientService {

    @Autowired
    public ClientRepository ClientRepository;

    public AgentResponse addClientByAgent(AgentRequest agentRequest) {
        Client client = new Client();
        client.setName(agentRequest.getName());
        client.setMobileNo(agentRequest.getMobileNo());
        client.setAddress(agentRequest.getAddress());
        client.setLoanAmount(agentRequest.getLoanAmount());
        client.setDurationMonths(agentRequest.getDurationMonth());
        client.setLoanDate(LocalDate.now());
        ClientRepository.save(client);
        return new AgentResponse(agentRequest.getName(),agentRequest.getMobileNo(),agentRequest.getAddress(),
                                    agentRequest.getLoanAmount(), agentRequest.getDurationMonth(), agentRequest.getInterestRate(),
                                    LocalDate.now(), true, "client added successfully");
    }
}
