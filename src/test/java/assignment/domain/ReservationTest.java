package assignment.domain;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ReservationTest {

    private String username;
    private MeetingRoom meetingRoom;
    private LocalDate date;
    private LocalTime startTime;

    @Before
    public void setUp() throws Exception {
        username = "bob";
        meetingRoom = new MeetingRoom("A");
        date = LocalDate.now();
        startTime = LocalTime.of(10, 0);
    }

    @Test
    public void isValidMinute() {
        LocalTime endTime = startTime.plusMinutes(30);
        Reservation reservation = new Reservation(username, meetingRoom, date, startTime, endTime);
        assertThat(reservation).isNotNull();
    }

    @Test(expected = IllegalArgumentException.class)
    public void isValidMinute_not_0_and_not_30() {
        LocalTime endTime = LocalTime.of(0, 1);
        new Reservation(username, meetingRoom, date, startTime, endTime);
    }

    @Test
    public void isEndTimeLaterThanStartTime() {
        LocalTime endTime = startTime.plusMinutes(30);
        Reservation reservation = new Reservation(username, meetingRoom, date, startTime, endTime);
        assertThat(reservation).isNotNull();
    }

    @Test(expected = IllegalArgumentException.class)
    public void isEndTimeLaterThanStartTime_same() {
        LocalTime endTime = startTime;
        new Reservation(username, meetingRoom, date, startTime, endTime);
    }

    @Test(expected = IllegalArgumentException.class)
    public void isEndTimeLaterThanStartTime_earlier() {
        LocalTime endTime = startTime.minusMinutes(30);
        new Reservation(username, meetingRoom, date, startTime, endTime);
    }
}
