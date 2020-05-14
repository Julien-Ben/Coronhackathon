package coronhackathon.backend.controller;

import coronhackathon.backend.entity.Tag;
import coronhackathon.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TagController {

    @Autowired
    private TagService tagService;
    @Autowired
    private TagOfChallengeService tagOfChallengeService;

    /**
     * Add a tag given as argument to the database
     *
     * @param tag a tag we want to add
     */
    @PostMapping("/api/addTag")
    public void addTag(@RequestBody Tag tag) {
        tagService.addTag(tag);
    }

    /**
     * Return a tag with a given Id
     *
     * @param tagId the id of a tag
     * @return the tag with this associated Id
     */
    @RequestMapping(path = "/api/getTag/{tagId}", method = RequestMethod.GET)
    public Tag getTag(@PathVariable long tagId) {
        return tagService.getTag(tagId);
    }

    /**
     * Return a tag with a given name
     *
     * @param tagName the name of the tag
     * @return the tag that has a given name
     */
    @RequestMapping(path = "/api/getTagByName/{tagName}", method = RequestMethod.GET)
    public Tag getTagByName(@PathVariable String tagName) {
        return tagService.getTagByName(tagName);
    }


    /**
     * Returns a list with all the tags
     *
     * @param
     * @return a list with all the tags
     */
    @GetMapping("/api/allTags")
    public List<Tag> allTags() {
        return tagService.allTags();
    }

    /**
     * Retrieve all the tag that has a Challenge
     *
     * @param challengeId the id of the challenge of interest
     * @return a list of all the tags that are linked to this challenge
     */
    @RequestMapping(path = "/api/getTagsOfChallenge/{challengeId}", method = RequestMethod.GET)
    public List<Tag> getTagsOfChallenge(@PathVariable long challengeId) {
        return tagOfChallengeService.getTagsOfChallenge(challengeId);
    }

    /**
     * Add a tag to a particular challenge
     *
     * @param tagId       the tag we want to link
     * @param challengeId the challenge we want to link to
     * @return a string if the link was done
     */
    @PostMapping("/api/addTagToChallenge")
    public String addTagToChallenge(@RequestParam long tagId, @RequestParam long challengeId) {
        return tagOfChallengeService.addTagToChallenge(tagId, challengeId);
    }

}