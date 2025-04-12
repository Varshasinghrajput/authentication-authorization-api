package login.Register.loginRegister.Repository;

import login.Register.loginRegister.Entity.EMI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EMIRepository extends JpaRepository<EMI, Integer> {
    List<EMI> findByClient_MobileNo(String clientMobileNo);
}
