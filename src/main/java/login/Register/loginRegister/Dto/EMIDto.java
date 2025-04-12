package login.Register.loginRegister.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EMIDto {
    private int emiYear;
    private String emiMonth;
    private double emiAmount;
    private boolean isPaidStatus;
}
