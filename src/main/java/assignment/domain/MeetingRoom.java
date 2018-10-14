package assignment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class MeetingRoom extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String name;

    public MeetingRoom() {
        this("");
    }

    public MeetingRoom(String name) {
        this(0L, name);
    }

    public MeetingRoom(long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
