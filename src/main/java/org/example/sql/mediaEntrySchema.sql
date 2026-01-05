CREATE TABLE media_entries (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    title TEXT NOT NULL,
    description TEXT,
    media_type TEXT NOT NULL,
    release_year INTEGER,
    genre TEXT,
    age_restriction BOOLEAN,

    FOREIGN KEY (user_id) REFERENCES users(id)
);

ALTER TABLE media_entries
    RENAME COLUMN id TO media_id;
