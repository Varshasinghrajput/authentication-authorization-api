package login.Register.loginRegister.security;

import login.Register.loginRegister.Entity.Users;
import login.Register.loginRegister.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String mobileNo) throws UsernameNotFoundException {
        return userRepository.findByMobileNo(mobileNo)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
