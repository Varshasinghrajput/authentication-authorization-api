package login.Register.loginRegister.Controller;

import jakarta.persistence.GeneratedValue;
import login.Register.loginRegister.Dto.EMIDto;
import login.Register.loginRegister.Entity.EMI;
import login.Register.loginRegister.Service.EMIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/client/emi")
@PreAuthorize("hasRole('AGENT')")
public class EMIController {

    @Autowired
    private EMIService emiService;

    @GetMapping("/{clientMobileNo}")
    public ResponseEntity<List<EMIDto>> getClientEmis(@PathVariable("clientMobileNo") String mobileNo) {
        return ResponseEntity.ok(emiService.getEMIByClientMobileNo(mobileNo));
    }

}
