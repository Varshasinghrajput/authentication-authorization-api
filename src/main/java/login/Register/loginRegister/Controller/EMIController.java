package login.Register.loginRegister.Controller;

import jakarta.persistence.GeneratedValue;
import login.Register.loginRegister.Dto.EMIDto;
import login.Register.loginRegister.Entity.EMI;
import login.Register.loginRegister.Service.EMIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client/emi")
@PreAuthorize("hasRole('AGENT')")
public class EMIController {

    @Autowired
    private EMIService emiService;

    // specific client's emis
    @GetMapping("/{clientMobileNo}")
    public ResponseEntity<List<EMIDto>> getClientEmis(@PathVariable("clientMobileNo") String mobileNo) {
        return ResponseEntity.ok(emiService.getEMIByClientMobileNo(mobileNo));
    }

    //to pay emi of specific client by providing month and year
    @PutMapping("/pay/{clientMobileNo}/{month}/{year}")
    public ResponseEntity<String> payEmi(@PathVariable ("clientMobileNo")String MobileNo , @PathVariable ("month") String month, @PathVariable("year") int year) {
        String msg = emiService.markEmiAsPaid(MobileNo, month, year);
        return ResponseEntity.ok(msg);
    }

    // to close all emi of client
    @PutMapping("/close/{clientMobileNo}")
    public ResponseEntity<String> closeEmi(@PathVariable ("clientMobileNo") String MobileNo) {
        String msg = emiService.CloseEmi(MobileNo);
        return ResponseEntity.ok(msg);
    }

}
