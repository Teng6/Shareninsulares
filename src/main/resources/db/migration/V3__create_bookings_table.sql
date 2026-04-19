CREATE TABLE bookings (
                          id BIGSERIAL PRIMARY KEY,
                          listing_id BIGINT NOT NULL REFERENCES listings(id),
                          buyer_id BIGINT NOT NULL REFERENCES users(id),
                          status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
                          message TEXT,
                          created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                          updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);