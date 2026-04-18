CREATE TABLE listings (
                          id BIGSERIAL PRIMARY KEY,
                          title VARCHAR(100) NOT NULL,
                          description TEXT NOT NULL,
                          price DECIMAL(10,2) NOT NULL,
                          category VARCHAR(50) NOT NULL,
                          type VARCHAR(20) NOT NULL DEFAULT 'SELL',
                          status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
                          campus VARCHAR(50) NOT NULL,
                          image_url VARCHAR(255),
                          seller_id BIGINT NOT NULL REFERENCES users(id),
                          created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                          updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);