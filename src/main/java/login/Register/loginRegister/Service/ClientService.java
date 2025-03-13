package login.Register.loginRegister.Service;

import jakarta.transaction.Transactional;
import login.Register.loginRegister.Dto.AgentRequest;
import login.Register.loginRegister.Dto.AgentResponse;
import login.Register.loginRegister.Entity.Client;
import login.Register.loginRegister.Entity.Users;
import login.Register.loginRegister.Repository.ClientRepository;
import login.Register.loginRegister.Repository.UserRepository;
import login.Register.loginRegister.security.JwtHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ClientService {

    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;



    @Transactional
    public ResponseEntity<AgentResponse> addClientByAgent(AgentRequest agentRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String agentMobileNo = authentication.getName();

        Users agent = userRepository.findByMobileNo(agentMobileNo)
                .orElseThrow(() -> new RuntimeException("Agent not found"));

        // If user is not an Agent, return HTTP 403 (Forbidden)
        if (!agent.getRole().equals(login.Register.loginRegister.Enum.Roles.Agent)) {
            logger.error("Unauthorized Access: User {} is not an Agent", agent.getMobileNo());
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new AgentResponse(null, null, null, 0, 0, 0, null, false, "Unauthorized access: This user is not an Agent"));
        }

        //  Continue if the user is an Agent
        Client client = new Client();
        client.setName(agentRequest.getName());
        client.setMobileNo(agentRequest.getMobileNo());
        client.setAddress(agentRequest.getAddress());
        client.setLoanAmount(agentRequest.getLoanAmount());
        client.setDurationMonths(agentRequest.getDurationMonth());
        client.setInterestRate(agentRequest.getInterestRate());
        client.setLoanDate(LocalDate.now());
        client.setAgent(agent);

        Client savedClient = clientRepository.save(client);

        return ResponseEntity.status(HttpStatus.CREATED) // 🟢 201 Created
                .body(new AgentResponse(
                        savedClient.getName(),
                        savedClient.getMobileNo(),
                        savedClient.getAddress(),
                        savedClient.getLoanAmount(),
                        savedClient.getDurationMonths(),
                        savedClient.getInterestRate(),
                        savedClient.getLoanDate(),
                        true,
                        "Client added successfully"
                ));
    }




//    public AgentResponse addClientByAgent(AgentRequest agentRequest) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String agentMobileNo = authentication.getName();  //  Get logged-in agent's mobile number
//
//       // logger.info("Authenticated Agent Mobile Number: {}", agentMobileNo);
//
//        // Fetch agent from database using logged-in mobile number
//        Users agent = userRepository.findByMobileNo(agentMobileNo)
//                .orElseThrow(() -> {
//                    logger.error("Agent not found for mobile number: {}", agentMobileNo);
//                    return new RuntimeException("Agent not found");
//                });
//
//        //logger.info("Fetched Agent Details: Name = {}, Role = {}", agent.getName(), agent.getRole());
//
//        // Ensure user has the correct role
//        if (!agent.getRole().equals(login.Register.loginRegister.Enum.Roles.Agent)) {
//            //logger.error("Unauthorized Access: User {} is not an Agent", agent.getMobileNo());
//            return new AgentResponse(null,null,null,0,0,0,null,false,"Unathorized access : this user is not Agent");
//        }
//
//        // Log received client details
//        //logger.info("Received Client Data: {}", agentRequest);
//
//        // Create Client object
//        Client client = new Client();
//        client.setName(agentRequest.getName());
//        client.setMobileNo(agentRequest.getMobileNo());
//        client.setAddress(agentRequest.getAddress());
//        client.setLoanAmount(agentRequest.getLoanAmount());
//        client.setDurationMonths(agentRequest.getDurationMonth());
//        client.setInterestRate(agentRequest.getInterestRate());
//        client.setLoanDate(LocalDate.now());
//        client.setAgent(agent);
//
//        try {
//            logger.info("Saving Client to Database: {}", client);
//            Client savedClient = clientRepository.save(client);
//            logger.info("Client successfully saved with ID: {}", savedClient.getId());
//            logger.info("Client saved successfully!");
//        } catch (Exception e) {
//            logger.error("Error while saving client: {}", e.getMessage(), e);
//            throw new RuntimeException("Failed to add client");
//        }
//
//        return new AgentResponse(
//                client.getName(),
//                client.getMobileNo(),
//                client.getAddress(),
//                client.getLoanAmount(),
//                client.getDurationMonths(),
//                client.getInterestRate(),
//                client.getLoanDate(),
//                true,
//                "Client added successfully"
//        );
//    }

}
