package login.Register.loginRegister.Service;

import login.Register.loginRegister.Dto.RequestData;
import login.Register.loginRegister.Dto.ResponseData;
import login.Register.loginRegister.Entity.Users;
import login.Register.loginRegister.Repository.UserRepository;
import login.Register.loginRegister.security.CustomUserDetailsService;
import login.Register.loginRegister.security.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    // register
    public ResponseEntity<ResponseData> register(RequestData requestData) {

//            Optional<Users> users = userRepository.findByMobileNo(requestData.getMobileNo());
//            if (users.isPresent()) {
//                return new ResponseData("User already present");
//            }
//            Users user = new Users();
//            user.setName(requestData.getName());
//            user.setMobileNo(requestData.getMobileNo());
//            user.setPassword(passwordEncoder.encode(requestData.getPassword()));
//            user.setRole(requestData.getRole());
//            userRepository.save(user);
//            return  new ResponseData("User registered successfully");


        // Validate if the role is correct
        if (requestData.getRole() == null) {
            return new ResponseEntity<>(new ResponseData(null, null, null, false, "Invalid role! Allowed roles: AGENT , USER, ADMIN"),HttpStatus.UNAUTHORIZED);
        }

        // check if user already exists
        Optional<Users> existingUser = userRepository.findByMobileNo(requestData.getMobileNo());
        if (existingUser.isPresent()) {
            Users users = existingUser.get();
            return new ResponseEntity<>(new ResponseData(users.getMobileNo(), users.getName(), users.getRole(), false, "User already exists!"), HttpStatus.CONFLICT);
        }

        Users users = new Users();
        users.setMobileNo(requestData.getMobileNo());
        users.setPinCode(passwordEncoder.encode(requestData.getPinCode()));
        users.setName(requestData.getName());
        users.setRole(requestData.getRole());

        userRepository.save(users);
        return new ResponseEntity<>(new ResponseData(requestData.getMobileNo(), requestData.getName(), requestData.getRole(), true, "Success"),HttpStatus.OK);


    }

    //login
//    public ResponseData login( RequestData requestData) {
//            Optional<Users> users = userRepository.findByMobileNo(requestData.getMobileNo());
//        if (users.isPresent() && passwordEncoder.matches(requestData.getPassword(), users.get().getPassword())) {
//            return new ResponseData("Login successful");
//        }
//
//            return new ResponseData("Login failed");
//    }

    //login
    public ResponseEntity<ResponseData> login(RequestData requestData) {
        try {
            // Fetch user details using UserDetailsService
            UserDetails userDetails = userDetailsService.loadUserByUsername(requestData.getMobileNo());

            if (!userDetails.isEnabled()) {
                return new ResponseEntity<>(new ResponseData(null, null, null, false, "User not found"), HttpStatus.UNAUTHORIZED);
            }

            // Cast UserDetails to Users
            Users user = (Users) userDetails;

            // Verify password using BCryptPasswordEncoder
            if (!passwordEncoder.matches(String.valueOf(requestData.getPinCode()), user.getPinCode())) {
                return new ResponseEntity<>(new ResponseData(user.getMobileNo(), user.getName(), user.getRole(), false, "Invalid Credentials"), HttpStatus.UNAUTHORIZED);
            }

            // Generate JWT Token
            String token = jwtHelper.generateToken(userDetails);

            // Return successful login response
            return new ResponseEntity<>(new ResponseData(user.getMobileNo(), user.getName(), user.getRole(), true, token), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseData(null, null, null, false, "Something went wrong: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
