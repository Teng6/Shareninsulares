CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       student_id VARCHAR(20) UNIQUE NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       full_name VARCHAR(100) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       campus VARCHAR(50) NOT NULL,
                       role VARCHAR(20) NOT NULL DEFAULT 'CLIENT',
                       share_coins_balance INTEGER NOT NULL DEFAULT 0,
                       reputation_score DECIMAL(3,2) NOT NULL DEFAULT 5.00,
                       is_verified BOOLEAN NOT NULL DEFAULT FALSE,
                       is_restricted BOOLEAN NOT NULL DEFAULT FALSE,
                       created_at TIMESTAMP NOT NULL DEFAULT NOW()
);