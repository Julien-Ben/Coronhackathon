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
    private TagRepository tagRepository;
    @Autowired
    private ChallengeRepository challengeRepository;
    @Autowired
    private TagOfChallengeRepository tagOfChallengeRepository;


    public List<Challenge> getChallengesOfTag(long tagId) {
        List<Challenge> l = new ArrayList<>();
        Optional<Tag> ot = tagRepository.findById(tagId);
        if (!ot.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "tag with id : " + tagId + " not found");
        for (TagOfChallenge toc : tagOfChallengeRepository.findByTag(ot.get())) {
            l.add(toc.getChallenge());
        }
        return l;
    }

    public List<Tag> getTagsOfChallenge(long challengeId) {
        List<Tag> l = new ArrayList<>();
        Optional<Challenge> oc = challengeRepository.findById(challengeId);
        if (!oc.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "challenge with id : " + challengeId + " not found");
        for (TagOfChallenge toc : tagOfChallengeRepository.findByChallenge(oc.get())) {
            l.add(toc.getTag());
        }
        return l;
    }

    public String addTagToChallenge(long tagId, long challengeId) {
        Optional<Tag> ot = tagRepository.findById(tagId);
        Optional<Challenge> oc = challengeRepository.findById(challengeId);
        if (!ot.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "tag with id : " + tagId + " not found");
        if (!oc.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "challenge with id : " + challengeId + " not found");
        TagOfChallenge tagOfChallenge = new TagOfChallenge();
        tagOfChallenge.setChallenge(oc.get());
        tagOfChallenge.setTag(ot.get());
        tagOfChallengeRepository.save(tagOfChallenge);
        return "Tag " + ot.get().getName() + " is a tag of challenge " + oc.get().getName();
    }


}
