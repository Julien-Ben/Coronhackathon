package coronhackathon.backend.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TagOfChallengeId implements Serializable {

    private static final long serialVersionUID = 1L;

    private long challengeId;
    private long tagId;

    public TagOfChallengeId() {
    }

    public TagOfChallengeId(long challengeId, long tagId) {
        super();
        this.tagId = tagId;
        this.challengeId = challengeId;
    }

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long userId) {
        this.tagId = tagId;
    }

    public long getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(long challengeId) {
        this.challengeId = challengeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagOfChallengeId)) return false;
        TagOfChallengeId that = (TagOfChallengeId) o;
        return tagId == that.tagId &&
                challengeId == that.challengeId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagId, challengeId);
    }
}

