package coronhackathon.backend.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "isA")
    Set<Challenge> isA;

    /* ----getters and setters---- */

    public long getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public void setId(long tagId) {
        this.Id = tagId;
    }

    public void setName(String name) {
        this.name = name;
    }

}
