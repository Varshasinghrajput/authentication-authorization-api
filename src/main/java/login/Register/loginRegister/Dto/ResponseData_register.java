package login.Register.loginRegister.Dto;

import login.Register.loginRegister.Enum.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData_register {
    private String mobileNumber;
    private String name;
    private Roles role;
    private boolean success;
    private String message;
}
