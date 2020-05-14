package coronhackathon.backend.service;

import coronhackathon.backend.entity.Challenge;
import coronhackathon.backend.entity.HasCompleted;
import coronhackathon.backend.entity.User;
import coronhackathon.backend.repository.ChallengeRepository;
import coronhackathon.backend.repository.CompletedRepository;
import coronhackathon.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.*;

@Service
public class ChallengeService {
    @Autowired
    private ChallengeRepository challengeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private CompletedService completedService;


    public void addChallenge(Challenge challenge) {
        challengeRepository.save(challenge);
    }

    public List<Challenge> getAllChallenges() {
        return challengeRepository.findAll();
    }

    public Challenge getChallenge(long id) {
        return checkChallengeExists(challengeRepository.findById(id),"id", ""+id);
    }

    public Challenge getChallengeByName(String name) {
        return checkChallengeExists(challengeRepository.findByName(name),"name", ""+name);
    }

    public List<Challenge> getChallengeByCategory(long categoryId) {
        return challengeRepository.findByCategoryId(categoryId);
    }

    public List<Challenge> getChallengeByCategory(String name) {
        return challengeRepository.findByCategoryId(categoryService.getIdFromName(name));
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
        return all.subList(0, Math.min(9, all.size()));
    }

    public List<Boolean> getNineBoolean(String username, List<Challenge> lc){
        List<Boolean> lb = new ArrayList<>();
        User u = userService.getUserByUsername(username);
        List<Challenge> cc = completedService.getCompletedChallenges(username);
        // creates the lists of booleans corresponding to the given list of challenges
        for(Challenge c : lc) {
            lb.add(cc.contains(c)); 
        }
        return lb;
    }

    public List<Boolean> getChallengeBool(String username, long categoryId){
        List<Boolean> l = new ArrayList<>();
        User u = userService.getUserByUsername(username);
        List<Challenge> completed = completedService.getCompletedChallengesByCategory(u.getId(),categoryId);
        List<Challenge> cs = challengeRepository.findByCategoryId(categoryId);
        // creates the lists of booleans corresponding to the given list of challenges cs
        for (Challenge c : cs) {
            l.add(completed.contains(c));
        }
        return l;
    }

    public Map<String, Object> getAllChallengesBool(String username) {
        List<Boolean> l = new ArrayList<>();
        Optional<User> ou = userRepository.findByUsername(username);
        if (!ou.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user with name : " + username + " not found");
        List<Challenge> completed = completedService.getCompletedChallenges(ou.get().getId());
        List<Challenge> cs = challengeRepository.findAll();
        // creates the lists of booleans corresponding to the given list of challenges cs
        for (Challenge c : cs) {
            l.add(completed.contains(c));
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("Challenge", cs);
        map.put("Completed", l);
        return map;
    }

    private Challenge checkChallengeExists(Optional<Challenge> oc, String name, String value){
        if(!oc.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "challenge with "+name+" : " + value + " not found");
        }
        return oc.get();
    }
}
