-- Sample events

INSERT INTO event (id, name)
VALUES (1, 'Concierto'),
       (2, 'Teatro');

-- Sample seats: three for the concert, two for the theater

INSERT INTO seat (id, event_id, code)
VALUES (1, 1, 'A1'),
       (2, 1, 'A2'),
       (3, 1, 'A3'),
       (4, 2, 'A1'),
       (5, 2, 'A2');