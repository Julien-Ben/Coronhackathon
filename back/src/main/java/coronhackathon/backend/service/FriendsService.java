package coronhackathon.backend.service;

import coronhackathon.backend.repository.FriendsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendsService {
    @Autowired
    private FriendsRepository friendsRepository;
}
