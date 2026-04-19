CREATE TABLE ratings (
                         id BIGSERIAL PRIMARY KEY,
                         booking_id BIGINT UNIQUE NOT NULL REFERENCES bookings(id),
                         reviewer_id BIGINT NOT NULL REFERENCES users(id),
                         reviewed_id BIGINT NOT NULL REFERENCES users(id),
                         score INTEGER NOT NULL CHECK (score >= 1 AND score <= 5),
                         comment TEXT,
                         created_at TIMESTAMP NOT NULL DEFAULT NOW()
);