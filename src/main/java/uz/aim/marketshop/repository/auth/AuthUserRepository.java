package uz.aim.marketshop.repository.auth;

import uz.aim.marketshop.domains.auth.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> findByUsername(String username);

    @Query(value = "from AuthUser where status = 'BLOCKED'")
    Optional<List<AuthUser>> getAllBlocked();
}