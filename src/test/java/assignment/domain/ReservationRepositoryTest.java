package assignment.domain;

import assignment.support.Utility;
import org.apache.tomcat.jni.Local;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReservationRepositoryTest {

    @Autowired
    private MeetingRoomRepository meetingRoomRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    private MeetingRoom meetingRoom;
    private LocalDate date;
    private LocalTime savedStartTime;
    private LocalTime savedEndTime;
    private List<LocalDate> dates;

    @Before
    public void setUp() throws Exception {
        String username = "apeach";
        meetingRoom = meetingRoomRepository.findById(1L).orElseThrow(EntityNotFoundException::new);
        date = LocalDate.now().minusYears(1);
        savedStartTime = LocalTime.of(10, 0);
        savedEndTime = savedStartTime.plusHours(2); // 테스트할 시간이 저장된 시간내에 포함되는 경우도 고려해서 충분하게 간격을 둔다
        reservationRepository.save(Reservation.of(username, meetingRoom, date, savedStartTime, savedEndTime));
        dates = Utility.GetDates(date, 0);
    }

    @Test
    public void countOverlapped_겹침_반복주기날짜() {
        LocalDate reqDate = date.minusWeeks(1);
        List<LocalDate> reqDates = Utility.GetDates(reqDate, 1);
        LocalTime reqStartTime = savedStartTime;
        LocalTime reqEndTime = savedEndTime;
        assertThat(reservationRepository.countOverlapped(meetingRoom.getId(), reqDates, reqStartTime, reqEndTime)).isEqualTo(1);
    }

    @Test
    public void countOverlapped_안겹침_요청종료가_저장된시작보다_빠름() {
        // given
        LocalTime reqEndTime = savedStartTime.minusMinutes(30);
        LocalTime reqStartTime = reqEndTime.minusMinutes(30);
        // when
        // then
        assertThat(reservationRepository.countOverlapped(meetingRoom.getId(), dates, reqStartTime, reqEndTime)).isEqualTo(0);
    }

    @Test
    public void countOverlapped_안겹침_요청종료가_저장된시작과_같음() {
        // given
        LocalTime reqEndTime = savedStartTime;
        LocalTime reqStartTime = reqEndTime.minusMinutes(30);
        // when
        // then
        assertThat(reservationRepository.countOverlapped(meetingRoom.getId(), dates, reqStartTime, reqEndTime)).isEqualTo(0);
    }

    @Test
    public void countOverlapped_안겹침_요청시작이_저장된종료와_같음() {
        // given
        LocalTime reqStartTime = savedEndTime;
        LocalTime reqEndTime = reqStartTime.plusMinutes(30);
        // when
        // then
        assertThat(reservationRepository.countOverlapped(meetingRoom.getId(), dates, reqStartTime, reqEndTime)).isEqualTo(0);
    }

    @Test
    public void countOverlapped_안겹침_요청시작이_저장된종료보다_느림() {
        // given
        LocalTime reqStartTime = savedEndTime.plusMinutes(30);
        LocalTime reqEndTime = reqStartTime.plusMinutes(30);
        // when
        // then
        assertThat(reservationRepository.countOverlapped(meetingRoom.getId(), dates, reqStartTime, reqEndTime)).isEqualTo(0);
    }

    @Test
    public void countOverlapped_겹침_요청시작이_저장된시작보다_빠름__요청종료가_저장된시작보다_느리고_저장된종료보다_빠름() {
        // given
        LocalTime reqStartTime = savedStartTime.minusMinutes(30);
        LocalTime reqEndTime = reqStartTime.plusHours(1);
        assertThat(reqEndTime).isBefore(savedEndTime);
        // when
        // then
        assertThat(reservationRepository.countOverlapped(meetingRoom.getId(), dates, reqStartTime, reqEndTime)).isEqualTo(1);
    }

    @Test
    public void countOverlapped_겹침_요청시작이_저장된시작보다_빠름__요청종료가_저장된종료와_같음() {
        // given
        LocalTime reqStartTime = savedStartTime.minusMinutes(30);
        LocalTime reqEndTime = savedEndTime;
        // when
        // then
        assertThat(reservationRepository.countOverlapped(meetingRoom.getId(), dates, reqStartTime, reqEndTime)).isEqualTo(1);
    }

    @Test
    public void countOverlapped_겹침_요청시작이_저장된시작보다_빠름__요청종료가_저장된종료보다_느림() {
        // given
        LocalTime reqStartTime = savedStartTime.minusMinutes(30);
        LocalTime reqEndTime = savedEndTime.plusMinutes(30);
        // when
        // then
        assertThat(reservationRepository.countOverlapped(meetingRoom.getId(), dates, reqStartTime, reqEndTime)).isEqualTo(1);
    }

    @Test
    public void countOverlapped_겹침_요청시작이_저장된시작과_같음__요청종료가_저장된종료보다_빠름() {
        // given
        LocalTime reqStartTime = savedStartTime;
        LocalTime reqEndTime = reqStartTime.plusMinutes(30);
        assertThat(reqEndTime).isBefore(savedEndTime);
        // when
        // then
        assertThat(reservationRepository.countOverlapped(meetingRoom.getId(), dates, reqStartTime, reqEndTime)).isEqualTo(1);
    }

    @Test
    public void countOverlapped_겹침_요청시작이_저장된시작과_같음__요청종료가_저장된종료와_같음() {
        // given
        LocalTime reqStartTime = savedStartTime;
        LocalTime reqEndTime = savedEndTime;
        // when
        // then
        assertThat(reservationRepository.countOverlapped(meetingRoom.getId(), dates, reqStartTime, reqEndTime)).isEqualTo(1);
    }

    @Test
    public void countOverlapped_겹침_요청시작이_저장된시작과_같음__요청종료가_저장된종료보다_느림() {
        // given
        LocalTime reqStartTime = savedStartTime;
        LocalTime reqEndTime = savedEndTime.plusMinutes(30);
        // when
        // then
        assertThat(reservationRepository.countOverlapped(meetingRoom.getId(), dates, reqStartTime, reqEndTime)).isEqualTo(1);
    }

    @Test
    public void countOverlapped_겹침_요청시작이_저장된시작보다_느리고_저장된종료보다_빠름__요청종료가_저장된종료보다_빠름() {
        // given
        LocalTime reqStartTime = savedStartTime.plusMinutes(30);
        assertThat(reqStartTime).isBefore(savedEndTime);
        LocalTime reqEndTime = reqStartTime.plusMinutes(30);
        assertThat(reqEndTime).isBefore(savedEndTime);
        // when
        // then
        assertThat(reservationRepository.countOverlapped(meetingRoom.getId(), dates, reqStartTime, reqEndTime)).isEqualTo(1);
    }

    @Test
    public void countOverlapped_겹침_요청시작이_저장된시작보다_느리고_저장된종료보다_빠름__요청종료가_저장된종료와_같음() {
        // given
        LocalTime reqStartTime = savedStartTime.plusMinutes(30);
        assertThat(reqStartTime).isBefore(savedEndTime);
        LocalTime reqEndTime = savedEndTime;
        // when
        // then
        assertThat(reservationRepository.countOverlapped(meetingRoom.getId(), dates, reqStartTime, reqEndTime)).isEqualTo(1);
    }

    @Test
    public void countOverlapped_겹침_요청시작이_저장된시작보다_느리고_저장된종료보다_빠름__요청종료가_저장된종료보다_느림() {
        // given
        LocalTime reqStartTime = savedStartTime.plusMinutes(30);
        assertThat(reqStartTime).isBefore(savedEndTime);
        LocalTime reqEndTime = savedEndTime.plusMinutes(30);
        // when
        // then
        assertThat(reservationRepository.countOverlapped(meetingRoom.getId(), dates, reqStartTime, reqEndTime)).isEqualTo(1);
    }
}
