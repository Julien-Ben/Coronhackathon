package coronhackathon.backend.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FriendsId implements Serializable {
    private long user1Id;
    private long user2Id;

    public FriendsId() {
    }

    public FriendsId(long user1Id, long user2Id) {
        super();
        this.user1Id = user1Id;
        this.user2Id = user2Id;
    }

    public long getUser1Id() {
        return user1Id;
    }

    public long getUser2Id() {
        return user2Id;
    }

    public void setUser1Id(long user1Id) {
        this.user1Id = user1Id;
    }

    public void setUser2Id(long user2Id) {
        this.user2Id = user2Id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FriendsId)) return false;
        FriendsId friendsId = (FriendsId) o;
        return user1Id == friendsId.user1Id &&
                user2Id == friendsId.user2Id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user1Id, user2Id);
    }
}
