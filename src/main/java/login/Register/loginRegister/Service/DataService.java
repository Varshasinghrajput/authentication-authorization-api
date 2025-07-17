package login.Register.loginRegister.Service;

import login.Register.loginRegister.Dto.ClientDto;
import login.Register.loginRegister.Entity.Client;
import login.Register.loginRegister.Entity.Users;
import login.Register.loginRegister.Enum.Roles;
import login.Register.loginRegister.Repository.ClientRepository;
import login.Register.loginRegister.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClientRepository clientRepository;

    // fetch all users (admin)
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    //fetch Specific user
    public Users getUserByMobileNo(String mobileNo) {
        return userRepository.findByMobileNo(mobileNo).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

//    public List<Users> getClientsAndAgents() {
//        return userRepository.findAll()
//                .stream()
//                .filter(users -> users.getRole() == Roles.AGENT || users.getRole() == Roles.CLIENT)
//                .toList();
//    }

    // fetch all clients
    public List<ClientDto> getAllClients() {

        List<Client> clients = clientRepository.findAll();
        List<ClientDto> dtoList = new ArrayList<>();
        for (Client client : clients) {
            ClientDto dto = new ClientDto(
                    client.getName(),
                    client.getMobileNo(),
                    client.getAddress(),
                    client.getLoanAmount(),
                    client.getDurationMonths(),
                    client.getInterestRate(),
                    client.getLoanDate(),
                    client.getAgentMobileNo()

            );
            dtoList.add(dto);
        }
        return dtoList;
    }

//    // fetch client for a specific Agent
//    public List<Client> getAllClientsByAgent(String agentUsername) {
//        Users agent = userRepository.findByMobileNo(agentUsername)
//                .orElseThrow(() -> new UsernameNotFoundException("User " + agentUsername + " not found"));
//        return clientRepository.findAll().stream()
//                .filter(client -> client.getAgent().equals(agent))
//                .toList();
//    }


    public List<ClientDto> getAllClientsByAgent(String agentUsername) {
        Users agent = userRepository.findByMobileNo(agentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User " + agentUsername + " not found"));

        return clientRepository.findAll().stream()
                .filter(client -> client.getAgent().equals(agent))
                .map(client -> {
                    ClientDto dto = new ClientDto();
                    dto.setName(client.getName());
                    dto.setMobileNo(client.getMobileNo());
                    dto.setAddress(client.getAddress());
                    dto.setLoanAmount(client.getLoanAmount());
                    dto.setDurationMonth(client.getDurationMonths());
                    dto.setInterestRate(client.getInterestRate());
                    dto.setLoanData(client.getLoanDate());
                    dto.setAgentMobileNo(client.getAgent().getMobileNo()); // from Users object

                    return dto;
                })
                .toList();
    }



    // fetch a client's own profile
    public Users getClientByMobileNo(String mobileNo) {
        return userRepository.findByMobileNo(mobileNo)
                .orElseThrow(()-> new UsernameNotFoundException("Client not found"));

    }

}
