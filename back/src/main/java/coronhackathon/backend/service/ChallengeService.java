package coronhackathon.backend.service;

import coronhackathon.backend.entity.Challenge;
import coronhackathon.backend.repository.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ChallengeService {
    @Autowired
    private ChallengeRepository challengeRepository;
    @Autowired
    private CategoryService categoryService;

    public void addChallenge(Challenge challenge) {
        challengeRepository.save(challenge);
    }

    public List<Challenge> getAllChallenges() {
        return challengeRepository.findAll();
    }

    public Optional<Challenge> getChallenge(long id) {
        return challengeRepository.findById(id);
    }

    public List<Challenge> getChallengeByCategory(long categoryId) {
        return challengeRepository.findByCategoryId(categoryId);
    }

    public List<Challenge> getChallengeByCategory(String name) {
        return challengeRepository.findByCategoryId(categoryService.getIdFromName(name));
    }

    public Optional<Challenge> getChallengeByName(String name) {
        return challengeRepository.findByName(name);
    }

    public long numberOfChallenges() {
        return challengeRepository.count();
    }

    public Long numberOfChallengesByCategoryId(long category) {
        return challengeRepository.countByCategoryId(category);
    }

    public Long numberOfChallengesByCategoryName(String name) {
        return challengeRepository.countByCategoryId((categoryService.getIdFromName(name)));
    }

    public List<Challenge> getNineChallenges() {
        List<Challenge> all = getAllChallenges();
        Collections.shuffle(all);
        return all.subList(0,Math.min(9,all.size()));
    }
}
