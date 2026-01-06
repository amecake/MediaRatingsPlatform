CREATE TABLE ratings (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id),
    media_id INTEGER NOT NULL REFERENCES media_entries(media_id),
    stars INTEGER CHECK (stars BETWEEN 1 AND 5),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    confirmed BOOLEAN DEFAULT false,
    UNIQUE (user_id, media_id)
);

ALTER TABLE ratings
    ADD COLUMN likes INTEGER default 0;

DROP TABLE ratings;