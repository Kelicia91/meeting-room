package assignment.service;

import assignment.domain.MeetingRoom;
import assignment.domain.Reservation;
import assignment.domain.ReservationRepository;
import assignment.dto.ReservationDTO;
import assignment.support.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private MeetingRoomService meetingRoomService;

    public List<Reservation> add(ReservationDTO reservationDTO) {
        MeetingRoom meetingRoom = meetingRoomService.findById(reservationDTO.getMeetingRoomId());
        List<LocalDate> dates = Utility.GetDates(reservationDTO.getDate(), reservationDTO.getRepeatPerWeek());
        LocalTime startTime = reservationDTO.getStartTime();
        LocalTime endTime = reservationDTO.getEndTime();

        if (isOverlapped(meetingRoom.getId(), dates, startTime, endTime))
            throw new IllegalArgumentException("overlapped reservation");

        return save(reservationDTO.getUserName(), meetingRoom, dates, startTime, endTime);
    }

    private List<Reservation> save(String username, MeetingRoom meetingRoom, List<LocalDate> dates, LocalTime startTime, LocalTime endTime) {
        List<Reservation> reservations = new ArrayList<>();
        dates.stream()
                .forEach(date -> reservations.add(
                        reservationRepository.save(Reservation.of(username, meetingRoom, date, startTime, endTime))
                ));
        return reservations;
    }

    private boolean isOverlapped(long meetingRoomId, List<LocalDate> dates, LocalTime startTime, LocalTime endTime) {
        Long count = reservationRepository.countOverlapped(meetingRoomId, dates, startTime, endTime);
        if (count <= 0)
            return false;
        return true;
    }
}