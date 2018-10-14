INSERT INTO meeting_room (id, name) VALUES (1, 'A');
INSERT INTO meeting_room (id, name) VALUES (2, 'B');
INSERT INTO meeting_room (id, name) VALUES (3, 'C');
INSERT INTO meeting_room (id, name) VALUES (4, 'D');
INSERT INTO meeting_room (id, name) VALUES (5, 'E');
INSERT INTO meeting_room (id, name) VALUES (6, 'F');

INSERT INTO reservation (id, username, meeting_room_id, date, start_time, end_time) VALUES (1, 'ryan', 1, CURRENT_DATE(), '10:00:00', '10:30:00');
INSERT INTO reservation (id, username, meeting_room_id, date, start_time, end_time) VALUES (2, 'apeach', 2, CURRENT_DATE(), '12:30:00', '14:00:00');
INSERT INTO reservation (id, username, meeting_room_id, date, start_time, end_time) VALUES (3, 'frodo', 2, CURRENT_DATE(), '16:30:00', '18:00:00');
INSERT INTO reservation (id, username, meeting_room_id, date, start_time, end_time) VALUES (4, 'neo', 3, CURRENT_DATE(), '11:30:00', '13:00:00');