package login.Register.loginRegister.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "emi_data")
public class EMI {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int emiYear;

    @Column(nullable = false)
    private String emiMonth;

    @Column(nullable = false)
    private double emiAmount;

    @Column(nullable = false)
    private boolean isPaidStatus = false;

    //LINK TO CLIENT
    @ManyToOne
    @JoinColumn(name = "client_id" , nullable = false)
    private Client client;

}
