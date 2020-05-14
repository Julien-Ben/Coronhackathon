package coronhackathon.backend.service;

import coronhackathon.backend.entity.*;
import coronhackathon.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagOfChallengeService {
    @Autowired
    private TagService tagService;
    @Autowired
    private ChallengeService challengeService;
    @Autowired
    private TagOfChallengeRepository tagOfChallengeRepository;


    public List<Challenge> getChallengesOfTag(long tagId) {
        List<Challenge> l = new ArrayList<>();
        Tag t = tagService.getTag(tagId);
        for (TagOfChallenge toc : tagOfChallengeRepository.findByTag(t)) {
            l.add(toc.getChallenge());
        }
        return l;
    }

    public List<Tag> getTagsOfChallenge(long challengeId) {
        List<Tag> l = new ArrayList<>();
        Challenge c = challengeService.getChallenge(challengeId);
        for (TagOfChallenge toc : tagOfChallengeRepository.findByChallenge(c)) {
            l.add(toc.getTag());
        }
        return l;
    }

    public String addTagToChallenge(long tagId, long challengeId) {
        Tag t = tagService.getTag(tagId);
        Challenge c = challengeService.getChallenge(challengeId);
        TagOfChallenge tagOfChallenge = new TagOfChallenge();
        tagOfChallenge.setChallenge(c);
        tagOfChallenge.setTag(t);
        tagOfChallengeRepository.save(tagOfChallenge);
        return "Tag " + t.getName() + " is a tag of challenge " + c.getName();
    }


}
