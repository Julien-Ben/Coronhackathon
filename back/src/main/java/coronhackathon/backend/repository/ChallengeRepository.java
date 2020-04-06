package coronhackathon.backend.repository;

import coronhackathon.backend.entity.Category;
import coronhackathon.backend.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    public Optional<Challenge> findById(long id);
    public Optional<Challenge> findByName(String name);
    public List<Challenge> findByCategoryId(long categoryId);
    public long count();
    public Long countByCategoryId(long categoryId);
}
