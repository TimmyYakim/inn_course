drop table if exists usr;
CREATE TABLE usr
(
    id          serial primary key,
    name        VARCHAR(255) not null,
    birthday    DATE,
    login_ID    INTEGER      not null,
    city        VARCHAR(255),
    email       VARCHAR(255),
    description VARCHAR(1024)
);


drop table if exists role;
create table role
(
    id          serial primary key,
    name        VARCHAR(255),
    description VARCHAR(1024)
);


drop table if exists user_role;
create table user_role
(
    id      serial primary key,
    user_id INTEGER REFERENCES usr (id) ON UPDATE CASCADE ON DELETE CASCADE,
    role_id INTEGER REFERENCES role (id) ON UPDATE CASCADE ON DELETE CASCADE
);

drop table if exists LOGS;
create table LOGS
(
    ID        serial primary key,
    DATE      TIMESTAMP,
    LOG_LEVEL VARCHAR(1024),
    MESSAGE   VARCHAR(1024),
    EXCEPTION VARCHAR(1024)
);
