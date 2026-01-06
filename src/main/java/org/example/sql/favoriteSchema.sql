CREATE TABLE favorites (
   user_id INTEGER NOT NULL,
   media_id INTEGER NOT NULL,

   PRIMARY KEY (user_id, media_id),

   FOREIGN KEY (user_id) REFERENCES users(user_id) ,
   FOREIGN KEY (media_id) REFERENCES media_entries(media_id)
);
