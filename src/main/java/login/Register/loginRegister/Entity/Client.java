package login.Register.loginRegister.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "client_data")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column( nullable = false)
    private String name;

    @Column(unique = true)
    private String MobileNo;

    @Column( nullable = false)
    private String address;

    @Column(nullable = false)
    private long loanAmount;

    @Column( nullable = false)
    private int durationMonths;

    @Column( nullable = false)
    private float interestRate;


    private LocalDate loanDate;
}
