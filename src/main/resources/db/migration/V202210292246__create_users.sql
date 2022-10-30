CREATE SEQUENCE user_id_sequence;

CREATE TABLE IF NOT EXISTS users (
    id bigint not null default nextval('user_id_sequence'),
    username varchar(20) not null,
    email varchar(50) not null,
    password varchar(120) not null,
    primary key (id),
    constraint uniq_user_username unique (username),
    constraint uniq_user_email unique (email)
);