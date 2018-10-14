package assignment.service;

import assignment.domain.MeetingRoom;
import assignment.domain.MeetingRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class MeetingRoomService {

    @Autowired
    private MeetingRoomRepository meetingRoomRepository;

    public MeetingRoom findById(Long id) {
        return meetingRoomRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
