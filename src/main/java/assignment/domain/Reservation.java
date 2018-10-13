package assignment.domain;

import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Reservation extends AbstractEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_reservation_user_id"))
    private User user;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_reservation_meeting_room_id"))
    private MeetingRoom meetingRoom;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    public Reservation(User user, MeetingRoom meetingRoom, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.user = user;
        this.meetingRoom = meetingRoom;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        validate();
    }

    private void validate() {
        if (!isValidMinute(startTime)) throw new IllegalArgumentException();
        if (!isValidMinute(endTime)) throw new IllegalArgumentException();
        if (!isEndTimeLaterThanStartTime()) throw new IllegalArgumentException();
    }

    private boolean isValidMinute(LocalTime time) {
        int minute = time.getMinute();
        if (minute == 0 || minute == 30) return true;
        return false;
    }

    private boolean isEndTimeLaterThanStartTime() {
        if (endTime.isAfter(startTime)) return true;
        return false;
    }

    public User getUser() {
        return user;
    }

    public MeetingRoom getMeetingRoom() {
        return meetingRoom;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
