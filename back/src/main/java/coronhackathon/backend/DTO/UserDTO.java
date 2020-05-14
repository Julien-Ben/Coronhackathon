package coronhackathon.backend.DTO;

import coronhackathon.backend.entity.User;

public class UserDTO {
    private long id;
    private String username;
    public UserDTO(User u ){
        this.id = u.getId();
        this.username = u.getUsername();
    }
    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
