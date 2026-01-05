CREATE TABLE ratings (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id),
    media_id INTEGER NOT NULL REFERENCES media_entries(id),
    stars INTEGER CHECK (stars BETWEEN 1 AND 5),
    comment TEXT,
    confirmed BOOLEAN DEFAULT false,
    UNIQUE (user_id, media_id)
);
