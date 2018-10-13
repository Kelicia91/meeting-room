package assignment.domain;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ReservationTest {

    @Test
    public void isValidMinute() {
        User user = null;
        MeetingRoom meetingRoom = null;
        LocalDate date = null;
        LocalTime startTime = LocalTime.of(0, 0); // DateTimeException
        LocalTime endTime = LocalTime.of(0, 30);
        Reservation reservation = new Reservation(user, meetingRoom, date, startTime, endTime);
        assertThat(reservation).isNotNull();
    }

    @Test(expected = IllegalArgumentException.class)
    public void isValidMinute_not_0_and_not_30() {
        User user = null;
        MeetingRoom meetingRoom = null;
        LocalDate date = null;
        LocalTime startTime = LocalTime.of(0, 1);
        LocalTime endTime = LocalTime.of(0, 1);
        new Reservation(user, meetingRoom, date, startTime, endTime);
    }

    @Test
    public void isEndTimeLaterThanStartTime_same_hour_diff_min() {
        User user = null;
        MeetingRoom meetingRoom = null;
        LocalDate date = null;
        LocalTime startTime = LocalTime.of(0, 0);
        LocalTime endTime = LocalTime.of(0, 30);
        Reservation reservation = new Reservation(user, meetingRoom, date, startTime, endTime);
        assertThat(reservation).isNotNull();
    }

    @Test
    public void isEndTimeLaterThanStartTime_diff_hour_same_min() {
        User user = null;
        MeetingRoom meetingRoom = null;
        LocalDate date = null;
        LocalTime startTime = LocalTime.of(0, 0);
        LocalTime endTime = LocalTime.of(1, 0);
        Reservation reservation = new Reservation(user, meetingRoom, date, startTime, endTime);
        assertThat(reservation).isNotNull();
    }

    @Test(expected = IllegalArgumentException.class)
    public void isEndTimeLaterThanStartTime_same() {
        User user = null;
        MeetingRoom meetingRoom = null;
        LocalDate date = null;
        LocalTime startTime = LocalTime.of(0, 0);
        LocalTime endTime = LocalTime.of(0, 0);
        new Reservation(user, meetingRoom, date, startTime, endTime);
    }

    @Test(expected = IllegalArgumentException.class)
    public void isEndTimeLaterThanStartTime_earlier() {
        User user = null;
        MeetingRoom meetingRoom = null;
        LocalDate date = null;
        LocalTime startTime = LocalTime.of(1, 0);
        LocalTime endTime = LocalTime.of(0, 0);
        new Reservation(user, meetingRoom, date, startTime, endTime);
    }
}
