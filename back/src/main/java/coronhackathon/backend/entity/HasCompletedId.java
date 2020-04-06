package coronhackathon.backend.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class HasCompletedId implements Serializable {

    private static final long serialVersionUID = 1L;

    private long userId;
    private long challengeId;

    public HasCompletedId() {
    }

    public HasCompletedId(long userId, long challengeId) {
        super();
        this.userId = userId;
        this.challengeId = challengeId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
        if (!(o instanceof HasCompletedId)) return false;
        HasCompletedId that = (HasCompletedId) o;
        return userId == that.userId &&
                challengeId == that.challengeId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, challengeId);
    }
}

