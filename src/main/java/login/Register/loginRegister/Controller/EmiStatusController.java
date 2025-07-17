package login.Register.loginRegister.Controller;

import login.Register.loginRegister.Dto.EmiStatusDto;
import login.Register.loginRegister.Service.EmiStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
public class EmiStatusController {

    @Autowired
    private EmiStatusService emiStatusService;

    // total paid and remaining amount of client
    @GetMapping("/client/{mobileNo}")
    public ResponseEntity<EmiStatusDto> EmiStatus(@PathVariable ("mobileNo")String mobile){
        return emiStatusService.getEmiStatus(mobile);
    }

    // total paid and remaining Amount of Agent-> ke all clients ki
    @GetMapping("/Agent/{mobileNo}")
    private ResponseEntity<EmiStatusDto> Agent(@PathVariable("mobileNo") String mobile){

        return emiStatusService.getEmiStatus_Agent(mobile);

    }
}
