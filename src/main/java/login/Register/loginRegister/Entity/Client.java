package login.Register.loginRegister.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.aspectj.weaver.loadtime.Agent;

import java.time.LocalDate;
import java.util.List;

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
    private String mobileNo;

    @Column( nullable = false)
    private String address;

    @Column(nullable = false)
    private long loanAmount;

    @Column( nullable = false)
    private int durationMonths;

    @Column( nullable = false)
    private float interestRate;


    private LocalDate loanDate;

    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = false)  // Linking Client to Agent (User) / one agent has multiple clients
    private Users agent;

    @Column(nullable = false)
    private String agentMobileNo;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL) // one client have multiple EMIs
    private List<EMI> emis; //EMI list linked with this client.


    //@OneToMany	One client → many EMIs
    //mappedBy = "client"	Relationship is owned by EMI class via its client field
    //cascade = CascadeType.ALL	Save/delete on client will also save/delete all related EMIs automatically


    // new fields for paid and remaining amount
    @Column(nullable = false)
    private double paidAmount;

    @Column(nullable = false)
    private double remainingAmount;
}
