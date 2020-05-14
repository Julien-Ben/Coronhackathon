package coronhackathon.backend.service;

import coronhackathon.backend.entity.Tag;
import coronhackathon.backend.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public Tag getTag(long tagId) {
        return checkTagExists(tagRepository.findById(tagId), "tagId", ""+tagId);
    }

    public Tag getTagByName(String name) {
        return checkTagExists(tagRepository.findByName(name), "name", ""+name);
    }

    public void addTag(Tag tag) {
        tagRepository.save(tag);
    }

    public List<Tag> allTags() {
        return tagRepository.findAll();
    }

    private Tag checkTagExists(Optional<Tag> ot, String name, String value){
        if(!ot.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "tag with "+name+" : " + value + " not found");
        }
        return ot.get();
    }
}
