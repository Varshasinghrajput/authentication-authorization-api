package login.Register.loginRegister.Controller;

import login.Register.loginRegister.Dto.RequestData_register;
import login.Register.loginRegister.Dto.ResponseData_register;
import login.Register.loginRegister.Service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@NoArgsConstructor
@AllArgsConstructor

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    public UserService userService;
//    @Autowired
//    public ClientService clientService;
//    @Autowired
//    public  AuthenticationManager authenticationManager;

//    public UserController(AuthenticationManager authenticationManager, UserService userService , ClientService clientService) {
//        this.authenticationManager = authenticationManager;
//        this.userService = userService;
//        this.clientService = clientService;
//    }

    @GetMapping("/all")
    public ResponseEntity<String> getAll(){
        return ResponseEntity.ok("All Users");
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseData_register> register(@RequestBody RequestData_register requestData) {

        return userService.register(requestData);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseData_register> login(@RequestBody RequestData_register requestData) {

        return userService.login(requestData);
    }


}
