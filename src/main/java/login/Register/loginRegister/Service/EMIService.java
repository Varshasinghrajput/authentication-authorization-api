package login.Register.loginRegister.Service;

import login.Register.loginRegister.Dto.EMIDto;
import login.Register.loginRegister.Entity.Client;
import login.Register.loginRegister.Entity.EMI;
import login.Register.loginRegister.Repository.EMIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EMIService {

    @Autowired
    private EMIRepository emiRepository;


    public List<EMI> generateEMI(Client client) {
        List<EMI> emisList = new ArrayList<>();
        int duration = client.getDurationMonths();
        double principal = client.getLoanAmount();
        float annualInterestRate = client.getInterestRate();

        double monthlyRate = annualInterestRate / (12 * 100); // R
        double emiAmount;

        if (monthlyRate == 0) {
            emiAmount = principal / duration; // no interest
        } else {
            emiAmount = (principal * monthlyRate * Math.pow(1 + monthlyRate, duration)) /
                    (Math.pow(1 + monthlyRate, duration) - 1);
        }

        LocalDate loanDate = client.getLoanDate();

        for (int i = 0; i < duration; i++) {
            LocalDate emiDate = loanDate.plusMonths(i);

            EMI emi = new EMI();
            emi.setEmiYear(emiDate.getYear());
            emi.setEmiMonth(emiDate.getMonth().toString());
            emi.setEmiAmount(Math.round(emiAmount * 100.0) / 100.0); // round to 2 decimals
            emi.setPaidStatus(false);
            emi.setClient(client);

            emisList.add(emi);
        }

        return emiRepository.saveAll(emisList);
    }



//    public List<EMI> generateEMI(Client client) {
//        List<EMI> emisList = new ArrayList<EMI>();
//        int duration = client.getDurationMonths();
//        double amount = client.getLoanAmount();
//        double monthlyEMI = amount/duration;
//
//        LocalDate loanDate = client.getLoanDate();
//
//        for(int i = 0; i<duration; i++){
//
//            LocalDate emidate = loanDate.plusMonths(i);
//
//            EMI emi = new EMI();
//            emi.setEmiYear(emidate.getYear());
//            //emi.setEmiMonth(emidate.getMonthValue());
//            emi.setEmiMonth(emidate.getMonth().toString());
//            emi.setPaidStatus(false);
//            emi.setEmiAmount(monthlyEMI);
//            emi.setClient(client);
//
//            emisList.add(emi);
//        }
//        return emiRepository.saveAll(emisList);
//    }

    public List<EMIDto> getEMIByClientMobileNo(String mobileNo) {
        List<EMI> emis = emiRepository.findByClient_MobileNo(mobileNo);

        //convert each emi to EMIDto
        List<EMIDto> dtoList = new ArrayList<>();
        for (EMI emi : emis) {
            EMIDto dto = new EMIDto(
                    emi.getEmiYear(),
                    emi.getEmiMonth(),
                    emi.getEmiAmount(),
                    emi.isPaidStatus()
            );
            dtoList.add(dto);
        }
        return dtoList;
    }

}
