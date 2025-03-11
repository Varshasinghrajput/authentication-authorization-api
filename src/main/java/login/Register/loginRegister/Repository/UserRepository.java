package login.Register.loginRegister.Repository;

import login.Register.loginRegister.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users>findByMobileNo(String mobileNo);
}
