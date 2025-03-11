package login.Register.loginRegister.Dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AgentResponse {
    private String name;
    private String mobileNo;
    private String address;
    private long loanAmount;
    private int durationMonth;
    private float interestRate;
    private LocalDate loanData;
    private boolean success;
    private String message;


}
