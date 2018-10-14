package assignment.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query(value = "SELECT COUNT(*) "
            + "FROM reservation AS r "
            + "WHERE r.meeting_room_id = :meetingRoomId "
            + "AND r.date IN :dates "
            + "AND r.start_time < :endTime "
            + "AND r.end_time > :startTime"
            , nativeQuery = true)
    long countOverlapped(
            @Param("meetingRoomId") long meetingRoomId,
            @Param("dates") Collection<LocalDate> dates,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime);
}
