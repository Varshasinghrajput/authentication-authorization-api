package login.Register.loginRegister.Dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AgentRequest {
   private String name;
   private String mobileNo;
   private String address;
   private long loanAmount;
   private int durationMonth;
   private float interestRate;
   private LocalDate loanData;



}
