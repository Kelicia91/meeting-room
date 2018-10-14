package assignment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class MeetingRoom extends AbstractEntity {

    @Column(nullable = false)
    private String name;

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
