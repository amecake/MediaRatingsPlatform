-- we don't know how to generate root <with-no-name> (class Root) :(

comment on database postgres is 'default administrative connection database';

create sequence users_id_seq
    as integer;

alter sequence users_id_seq owner to postgres;

create table users
(
    user_id  integer default nextval('users_id_seq'::regclass) not null
        primary key,
    username text                                              not null
        unique,
    password text                                              not null
);

alter table users
    owner to postgres;

alter sequence users_id_seq owned by users.user_id;

create table media_entries
(
    media_id        serial
        primary key,
    creator_id      integer not null
        constraint media_entries_user_id_fkey
            references users,
    title           text    not null,
    description     text,
    type            text    not null,
    release_year    integer,
    genre           text,
    age_restriction boolean
);

alter table media_entries
    owner to postgres;

create table ratings
(
    rating_id  serial
        primary key,
    user_id    integer not null
        references users,
    media_id   integer not null
        references media_entries,
    stars      integer
        constraint ratings_stars_check
            check ((stars >= 1) AND (stars <= 5)),
    comment    text,
    created_at timestamp default CURRENT_TIMESTAMP,
    confirmed  boolean   default false,
    likes      integer   default 0,
    unique (user_id, media_id)
);

alter table ratings
    owner to postgres;

create table favorites
(
    user_id  integer not null
        references users,
    media_id integer not null
        references media_entries,
    primary key (user_id, media_id)
);

alter table favorites
    owner to postgres;

