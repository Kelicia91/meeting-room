# meeting-room



## 문제해결전략
#### Domain
 - MeetingRoom { name }
 - Reservation { username, meeting-room-id, date, startTime, endTime }
   - 30분 단위 시각 검증
   - 시작시각이 종료시각보다 빠른지 검증
#### Service
 - MeetingRoomService
 - ReservationService
   - ReservationDTO { Reservation, repeatPerWeek }
   - 1회성/반복 예약 처리
   - 쿼리문 통해서 중첩된 예약이 존재하는지 판단(ReservationRepository)
#### API
| Name         | Request                      | Response             |
|--------------|------------------------------|----------------------|
| 회의실 조회     | GET  /api/meeting-rooms      | 200 List.MeetingRoom |
| 특정일 예약 조회 | GET  /api/reservations?date= | 200 List.Reservation |
| 예약 추가      | POST /api/reservations       | 200                  |
#### 기타
 - 미구현: 동시 동일예약 요청시, 선요청 1개만 처리



## 프로젝트 빌드 및 실행방법
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
#### URL 접속 - localhost:8080
