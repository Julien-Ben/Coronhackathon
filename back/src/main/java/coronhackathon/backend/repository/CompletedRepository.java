package coronhackathon.backend.repository;

import coronhackathon.backend.entity.Challenge;
import coronhackathon.backend.entity.HasCompleted;
import coronhackathon.backend.entity.HasCompletedId;
import coronhackathon.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompletedRepository extends JpaRepository<HasCompleted, HasCompletedId> {

    public List<HasCompleted> findByUser(User user);

    public List<HasCompleted> findByChallenge(Challenge challenge);

    public Optional<HasCompleted> findByUserAndChallenge(User user, Challenge challenge);

    public boolean findByUserAndChallengeContains(User user, Challenge challenge);
}
