package login.Register.loginRegister.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "image_data")
public class image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false)
    private String filename;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private Users user;

    @Column(nullable = false)
    private String userMobileNo;


}
