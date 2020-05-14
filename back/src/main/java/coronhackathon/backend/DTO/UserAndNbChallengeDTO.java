package coronhackathon.backend.DTO;

public class UserAndNbChallengeDTO {
    private long userId;
    private String username;
    private long nbChall;
    public UserAndNbChallengeDTO(long userId, String username, long nbChall){
        this.userId = userId;
        this.username = username;
        this.nbChall = nbChall;
    }

    public long getNbChall() {
        return nbChall;
    }

    public long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}
