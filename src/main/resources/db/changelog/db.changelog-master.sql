--liquibase formatted sql
--changeset akriuchk:1

create sequence feature_seq start with 1000 increment by 50;
create sequence image_seq start with 1000 increment by 50;

create table feature (
id                  bigint not null primary key,
uuid                uuid   not null unique,
timestamp           bigint,
begin_viewing_date  bigint,
end_viewing_date    bigint,
mission_name        varchar(255),
full_blob           json
);

create table image (
id          bigint not null primary key,
uuid        uuid   not null references feature (uuid),
content     bytea,
feature_id  bigint not null references feature (id)
);

create unique index feature_uuid_idx on feature(uuid);
create unique index image_feature_id_idx on image(feature_id);