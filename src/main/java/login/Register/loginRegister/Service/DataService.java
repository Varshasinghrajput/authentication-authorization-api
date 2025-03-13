package login.Register.loginRegister.Service;

import login.Register.loginRegister.Entity.Client;
import login.Register.loginRegister.Entity.Users;
import login.Register.loginRegister.Repository.ClientRepository;
import login.Register.loginRegister.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClientRepository clientRepository;

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

}
