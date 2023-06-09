package uz.aim.marketshop.config.security;

import uz.aim.marketshop.domains.auth.AuthUser;
import uz.aim.marketshop.repository.auth.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class CronJob {

    private final AuthUserRepository repository;

    @Scheduled(fixedRate = 2, timeUnit = TimeUnit.MINUTES)
    public void scheduleFixedDelayTask() {
        List<AuthUser> authUsers = repository.getAllBlocked().orElse(new ArrayList<>());
        authUsers.stream().filter(authUser -> Duration.between(authUser.getLastLoginTime(), LocalDateTime.now()).getSeconds() >= 120)
                .forEach(authUser -> {
                    authUser.setStatus(AuthUser.Status.ACTIVE);
                    authUser.setLoginTryCount(0);
                    authUser.setLastLoginTime(null);
                });
        repository.saveAll(authUsers);
        System.out.println("Blocked users are activated!");
    }
}
