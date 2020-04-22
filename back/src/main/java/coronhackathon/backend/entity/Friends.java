package coronhackathon.backend.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class Friends {
    @EmbeddedId
    private FriendsId id = new FriendsId();

    @ManyToOne
    @MapsId("user1Id")
    private User user1;

    @ManyToOne
    @MapsId("user2Id")
    private User user2;

    private boolean completed;

    public Friends(User user1, User user2, boolean completed){
        this.user1 = user1;
        this.user2 = user2;
        this.completed = completed;
    }

    public FriendsId getId() {
        return id;
    }

    public User getUser1() {
        return user1;
    }

    public User getUser2() {
        return user2;
    }

    public boolean getCompleted(){
        return completed;
    }

    public void setId(FriendsId id) {
        this.id = id;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }
}

