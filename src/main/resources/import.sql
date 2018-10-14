INSERT INTO meeting_room (id, name) VALUES (1, 'A');
INSERT INTO meeting_room (id, name) VALUES (2, 'B');
INSERT INTO meeting_room (id, name) VALUES (3, 'C');
INSERT INTO meeting_room (id, name) VALUES (4, 'D');
INSERT INTO meeting_room (id, name) VALUES (5, 'E');
INSERT INTO meeting_room (id, name) VALUES (6, 'F');

INSERT INTO reservation (id, username, meeting_room_id, date, start_time, end_time) VALUES (1, 'alice', 1, CURRENT_DATE(), '10:00:00', '10:30:00');