--liquibase formatted sql
--changeset akriuchk:1

create sequence feature_seq start with 1000 increment by 50;
create sequence image_seq start with 1000 increment by 50;

create table feature (
id                  bigint not null,
uuid                uuid   not null,
timestamp           bigint,
begin_viewing_date  bigint,
end_viewing_date    bigint,
mission_name        varchar(255),
full_blob           json,
primary key (id));

create table image (
id      bigint not null,
uuid    uuid   not null,
content bytea,
primary key (id)
);