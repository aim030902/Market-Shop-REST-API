package uz.aim.marketshop.repository.auth;

import uz.aim.marketshop.domains.auth.AuthRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRoleRepository extends JpaRepository<AuthRole, Long> {
}
