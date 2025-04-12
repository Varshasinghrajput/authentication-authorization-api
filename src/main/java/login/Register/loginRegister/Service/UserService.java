package login.Register.loginRegister.Service;

import login.Register.loginRegister.Dto.RequestData_register;
import login.Register.loginRegister.Dto.ResponseData_register;
import login.Register.loginRegister.Entity.Users;
import login.Register.loginRegister.Repository.UserRepository;
import login.Register.loginRegister.security.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtHelper jwtHelper;

    public ResponseEntity<ResponseData_register> register(RequestData_register requestData) {
        if (requestData.getRole() == null) {
            return new ResponseEntity<>(new ResponseData_register(null, null, null, false, "Invalid role!"), HttpStatus.UNAUTHORIZED);
        }

        Optional<Users> existingUser = userRepository.findByMobileNo(requestData.getMobileNo());
        if (existingUser.isPresent()) {
            Users users = existingUser.get();
            return new ResponseEntity<>(new ResponseData_register(users.getMobileNo(), users.getName(), users.getRole(), false, "User already exists!"), HttpStatus.CONFLICT);
        }

        Users users = new Users();
        users.setMobileNo(requestData.getMobileNo());
        users.setPinCode(passwordEncoder.encode(requestData.getPinCode()));  // Encrypt password
        users.setName(requestData.getName());
        users.setRole(requestData.getRole());
//        users.setActive(false); // set inactive by default

        // If the user is Admin, activate them automatically
        if("ADMIN".equals(String.valueOf(requestData.getRole()))) {
            users.setActive(true);
        }
        else {
            users.setActive(false);
        }

        userRepository.save(users);
        return new ResponseEntity<>(new ResponseData_register(
                requestData.getMobileNo(),
                requestData.getName(),
                requestData.getRole(),
                true,
                "Success"
        ), HttpStatus.OK);
    }

    public ResponseEntity<ResponseData_register> login(RequestData_register requestData) {
        try {
            Users user = userRepository.findByMobileNo(requestData.getMobileNo())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if(!user.isActive()){
                return new ResponseEntity<>(new ResponseData_register(null, null, null, false, "User is not activated . please contact admin!"), HttpStatus.FORBIDDEN);
            }

            if (!passwordEncoder.matches(requestData.getPinCode(), user.getPinCode())) {
                return new ResponseEntity<>(new ResponseData_register(null, null, null, false, "Invalid Credentials"), HttpStatus.UNAUTHORIZED);
            }

            String token = jwtHelper.generateToken(user);
            return new ResponseEntity<>(new ResponseData_register(user.getMobileNo(), user.getName(), user.getRole(), true, token), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseData_register(null, null, null, false, "Something went wrong: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

