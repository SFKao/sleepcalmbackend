package omelcam934.sleepcalmbackend.repository;

import omelcam934.sleepcalmbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailLikeIgnoreCase(String email);

    Optional<User> findByUsernameLikeIgnoreCase(String username);

    boolean existsByUsernameLikeIgnoreCase(String username);

    boolean existsByEmailLikeIgnoreCase(String email);

    Optional<User> findByUsernameLikeIgnoreCaseOrEmailLikeIgnoreCase(String username, String email );

    boolean existsByUsernameLikeIgnoreCaseOrEmailLikeIgnoreCase(String username, String email);

}