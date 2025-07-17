package login.Register.loginRegister.Service;

import login.Register.loginRegister.Dto.EMIDto;
import login.Register.loginRegister.Entity.Client;
import login.Register.loginRegister.Entity.EMI;
import login.Register.loginRegister.Repository.ClientRepository;
import login.Register.loginRegister.Repository.EMIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EMIService {

    @Autowired
    private EMIRepository emiRepository;

    @Autowired
    private ClientRepository clientRepository;


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
            emi.setClientMobile(client.getMobileNo());

            emisList.add(emi);
        }

        return emiRepository.saveAll(emisList);
    }


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

    public String markEmiAsPaid(String mobileNo, String month, int year) {
        List<EMI> emis = emiRepository.findByClient_MobileNo(mobileNo);
        for (EMI emi : emis) {
            if (emi.getEmiMonth().equals(month) && emi.getEmiYear() == year) {
                if(emi.isPaidStatus()) {
                    return "EMI already paid for this month and year";
                }
                else{
                    // mark emi as paid
                    emi.setPaidStatus(true);
                    emiRepository.save(emi);

                    // when paid -> update paid and remaining
                    Client client = emi.getClient();
                    double newPaid = client.getPaidAmount()+emi.getEmiAmount();
                    double newRemainingAmount = client.getRemainingAmount()-emi.getEmiAmount();

                    client.setPaidAmount(Math.round(newPaid * 100.0) / 100.0);
                    client.setRemainingAmount(Math.round(newRemainingAmount * 100.0) / 100.0);

                    clientRepository.save(client); // save updated client

                    //whenever emi is paid , we have to auto update ->EmiStatus
                    //emiStatusService.CalculateEmiStatus(emi.getClient().getMobileNo());
                    return "EMI paid for this month and year";
                }
            }
        }
        return "No EMI found for the given month and year.";
    }

    public String CloseEmi (String mobileNo) {
//        List<EMI> emis = emiRepository.findByClient_MobileNo(mobileNo);
//        if (emis.isEmpty()) {
//            return "Client not found or has no EMIs.";
//        } else {
//            for (EMI emi : emis) {
//                if (emi.isPaidStatus()) {
//                    continue;
//                } else {
//                    emi.setPaidStatus(true);
//                    totalNewPaid +=
//                    emiRepository.save(emi);
//
//                    //Update paid and remaining in client
//                    Client client = emis.get(0).getClient();
//                    client.setPaidAmount(client.getPaidAmount() + totalNewPaid);
//                    client.setRemainingAmount(client.getRemainingAmount() - totalNewPaid);
//                    clientRepository.save(client);
//                }
//            }
//            return "closed emi";
//        }
//    }


        List<EMI> emis = emiRepository.findByClient_MobileNo(mobileNo);
        if (emis.isEmpty()) return "Client not found or has no EMIs.";

        double totalNewPaid = 0;

        for (EMI emi : emis) {
            if (!emi.isPaidStatus()) {
                emi.setPaidStatus(true);
                totalNewPaid += emi.getEmiAmount();
                emiRepository.save(emi);
            }
        }

        //  Update paid and remaining in client
        Client client = emis.get(0).getClient();
        client.setPaidAmount(client.getPaidAmount() + totalNewPaid);
        client.setRemainingAmount(client.getRemainingAmount() - totalNewPaid);
        clientRepository.save(client);

        return "All EMIs marked as paid.";


    }
}
