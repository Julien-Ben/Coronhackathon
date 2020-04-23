package coronhackathon.backend.repository;

import coronhackathon.backend.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TagOfChallengeRepository extends JpaRepository<TagOfChallenge, TagOfChallengeId> {

    public List<TagOfChallenge> findByChallenge(Challenge challenge);

    public List<TagOfChallenge> findByTag(Tag tag);
}
