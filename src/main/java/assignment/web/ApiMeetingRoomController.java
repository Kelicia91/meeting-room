package assignment.web;

import assignment.domain.MeetingRoom;
import assignment.service.MeetingRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/meeting-rooms")
public class ApiMeetingRoomController {

    @Autowired
    private MeetingRoomService meetingRoomService;

    @GetMapping
    @Cacheable("meetingRooms")
    public ResponseEntity<List<MeetingRoom>> get() {
        return ResponseEntity.ok(meetingRoomService.findByAll());
    }
}
