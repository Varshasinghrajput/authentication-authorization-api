package login.Register.loginRegister.Service;

import login.Register.loginRegister.Dto.EmiStatusDto;
import login.Register.loginRegister.Entity.Client;
import login.Register.loginRegister.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmiStatusService {

    @Autowired
    private ClientRepository clientRepository;

    public ResponseEntity<EmiStatusDto> getEmiStatus(String mobile) {

        Optional<Client> client = clientRepository.findByMobileNo(mobile);
        if (client.isPresent()) {
            EmiStatusDto emiStatusDto = new EmiStatusDto();
            emiStatusDto.setPaidAmount(client.get().getPaidAmount());
            emiStatusDto.setRemainAmount(client.get().getRemainingAmount());

            return ResponseEntity.ok(emiStatusDto);
        }
        return ResponseEntity.notFound().build();
    }



    public ResponseEntity<EmiStatusDto> getEmiStatus_Agent(String mobileNo) {
        //Client agent  = clientRepository.findByAgentMobileNo(mobileNo).orElseThrow(() -> new RuntimeException("Agent not found"));
            Optional<List<Client>> list = clientRepository.findAllClientByAgentMobileNo(mobileNo);
            if (list.isPresent()) {
                double newPaidAmount = 0;
                double newRemainingAmount = 0;
                for (Client c : list.get()) {
                    newPaidAmount += c.getPaidAmount();
                    newRemainingAmount += c.getRemainingAmount();
                }
                //System.out.println(newPaidAmount);
                //System.out.println(newRemainingAmount);
                EmiStatusDto emiStatusDto = new EmiStatusDto(newPaidAmount, newRemainingAmount);
                return ResponseEntity.ok(emiStatusDto);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


}


