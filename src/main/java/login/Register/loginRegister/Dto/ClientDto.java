package login.Register.loginRegister.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    private String name;
    private String mobileNo;
    private String address;
    private long loanAmount;
    private int durationMonth;
    private float interestRate;
    private LocalDate loanData;
    private String AgentMobileNo;
}
