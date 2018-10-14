package assignment.service;

import assignment.domain.MeetingRoom;
import assignment.domain.Reservation;
import assignment.domain.ReservationRepository;
import assignment.dto.ReservationDTO;
import assignment.support.Utility;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private MeetingRoomService meetingRoomService;

    @InjectMocks
    private ReservationService reservationService;

    private final long MEETING_ROOM_ID = 1L;
    private MeetingRoom meetingRoom;
    private ReservationDTO dto;

    @Before
    public void setUp() throws Exception {
        String username = "apeach";
        meetingRoom = new MeetingRoom(MEETING_ROOM_ID, "A");
        LocalDate date = LocalDate.now();
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(10, 30);
        int repeatPerWeek = 1;

        dto = new ReservationDTO();
        dto.setUsername(username);
        dto.setMeetingRoomId(meetingRoom.getId());
        dto.setDate(date);
        dto.setStartTime(startTime);
        dto.setEndTime(endTime);
        dto.setRepeatPerWeek(repeatPerWeek);
    }

    @Test
    public void add_성공() {
        // given
        when(meetingRoomService.findById(meetingRoom.getId())).thenReturn(meetingRoom);

        List<LocalDate> dates = Utility.GetDates(dto.getDate(), dto.getRepeatPerWeek());
        when(reservationRepository.countOverlapped(meetingRoom.getId(), dates, dto.getStartTime(), dto.getEndTime()))
                .thenReturn(0L);

        // when
        List<Reservation> reservations = reservationService.add(dto);

        // then
        final int EXPECTED = dto.getRepeatPerWeek() + 1;
        verify(reservationRepository, times(EXPECTED)).save(any());
        assertThat(reservations.size()).isEqualTo(EXPECTED);
    }

    @Test(expected = EntityNotFoundException.class)
    public void add_실패_not_found_meetingRoom() {
        // given
        when(meetingRoomService.findById(MEETING_ROOM_ID)).thenThrow(new EntityNotFoundException());

        // when
        // then
        reservationService.add(dto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void add_실패_overlapped() throws Exception {
        // given
        when(meetingRoomService.findById(meetingRoom.getId())).thenReturn(meetingRoom);

        List<LocalDate> dates = Utility.GetDates(dto.getDate(), dto.getRepeatPerWeek());
        when(reservationRepository.countOverlapped(meetingRoom.getId(), dates, dto.getStartTime(), dto.getEndTime()))
                .thenReturn(1L);

        // when
        // then
        reservationService.add(dto);
    }
}
