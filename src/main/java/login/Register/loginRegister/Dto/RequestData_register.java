package login.Register.loginRegister.Dto;

import login.Register.loginRegister.Enum.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestData_register {
    private String name;
    private String mobileNo;
    private String pinCode;
    private Roles role;

}
