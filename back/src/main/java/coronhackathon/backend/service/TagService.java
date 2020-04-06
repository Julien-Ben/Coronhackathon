package coronhackathon.backend.service;

import coronhackathon.backend.entity.Tag;
import coronhackathon.backend.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public Optional<Tag> getTag(long tagId) {
        return tagRepository.findById(tagId);
    }

    public Optional<Tag> getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    public void addTag(Tag tag) {
        tagRepository.save(tag);
    }

    public List<Tag> allTags() {
        return tagRepository.findAll();
    }
}
