package coronhackathon.backend.repository;

import coronhackathon.backend.entity.Friends;
import coronhackathon.backend.entity.FriendsId;
import coronhackathon.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendsRepository extends JpaRepository<Friends, FriendsId> {
    //We can use that putting user2 the same as user1
    public List<Friends> findByUser1AndUser2(User user1, User user2);

    public List<Friends> findByUser1(User user1);
    public List<Friends> findByUser2(User user2);
}
