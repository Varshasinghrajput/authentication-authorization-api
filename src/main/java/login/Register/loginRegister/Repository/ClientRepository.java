package login.Register.loginRegister.Repository;

import login.Register.loginRegister.Entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Optional<Client> findByMobileNo(String mobileNo);
}
