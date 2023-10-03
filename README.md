# meeting-room



## problem solving strategy
#### Domain
 - MeetingRoom { name }
 - Reservation { username, meeting-room-id, date, startTime, endTime }
   - 30Minute-by-minute visual verification
   - Verify that the start time is earlier than the end time
#### Service
 - MeetingRoomService
 - ReservationService
   - ReservationDTO { Reservation, repeatPerWeek }
   - One-time/recurring reservation processing
   - Determine whether overlapping reservations exist through query statements(ReservationRepository)
#### API
| Name         | Request                      | Response             |
|--------------|------------------------------|----------------------|
| Conference room inquiry     | GET  /api/meeting-rooms      | 200 List.MeetingRoom |
| Reservation inquiry for a specific day | GET  /api/reservations?date= | 200 List.Reservation |
| Add reservation      | POST /api/reservations       | 200                  |
#### etc
 - Not implemented: When requesting the same reservation at the same time, only one pre-request is processed.



## How to build and run the project
#### Requirement
 - JRE 1.8+
 - Gradle 4+
#### Command
```
git clone https://github.com/Kelicia91/meeting-room.git
cd meeting-room
./gradlew build
java -jar build/libs/meeting-room-0.1.0.jar
```
#### URL connect - localhost:8080
