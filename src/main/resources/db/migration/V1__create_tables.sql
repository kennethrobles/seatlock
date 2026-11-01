-- Events that have seats (concerts, theater...)
CREATE TABLE event (
    id   BIGINT       PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Seats of an event, identified by their code (e.g. "A12")
CREATE TABLE seat (
    id       BIGINT      PRIMARY KEY,
    event_id BIGINT      NOT NULL REFERENCES event(id),
    code     VARCHAR(10) NOT NULL,
    UNIQUE (event_id, code)
);

-- Reservations of a seat: PENDING for 10 minutes, then CONFIRMED or EXPIRED
CREATE TABLE reservation (
    id         BIGINT      PRIMARY KEY,
    seat_id    BIGINT      NOT NULL REFERENCES seat(id),
    created_at TIMESTAMPTZ NOT NULL,
    expires_at TIMESTAMPTZ NOT NULL,
    status     VARCHAR(20) NOT NULL,
    CONSTRAINT valid_status CHECK (status IN('PENDING', 'CONFIRMED', 'EXPIRED'))
);

CREATE UNIQUE INDEX one_confirmed_per_seat
ON reservation (seat_id)
WHERE status = 'CONFIRMED';
