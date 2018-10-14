package assignment.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query(value = "SELECT COUNT(*) "
            + "FROM reservation as r "
            + "WHERE r.meeting_room = :meetingRoom "
            + "AND r.date IN :dates "
            + "AND r.start_time <= :startTime "
            + "AND r.end_time >= :endTime"
            , nativeQuery = true)
    long countOverlapped(
            @Param("meetingRoom") MeetingRoom meetingRoom,
            @Param("dates") Collection<LocalDate> dates,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime);
}
