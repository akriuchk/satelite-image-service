--liquibase formatted sql
--changeset akriuchk:1

create table feature (
id                  uuid not null,
timestamp           timestamp,
begin_viewing_date  timestamp,
end_viewing_date    timestamp,
mission_name        varchar(255),
full_blob           json,
primary key (id));

create table image (
id      uuid     not null,
content bytea   not null,
primary key (id)
);