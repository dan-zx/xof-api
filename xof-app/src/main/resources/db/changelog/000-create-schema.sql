--liquibase formatted sql

--changeset dan_zx:1 dbms:postgresql
create table xof_user (
    user_id bigserial,
    avatar_image_url varchar(255) not null,
    join_date timestamp not null,
    last_name varchar(255) not null,
    name varchar(255) not null,
    username varchar(255) not null,
    primary key (user_id)
)

--changeset dan_zx:2 dbms:postgresql
create table post (
    post_id bigserial,
    content text not null,
    created_date timestamp not null,
    title varchar(255) not null,
    updated_date timestamp not null,
    user_id bigint not null,
    primary key (post_id)
)

--changeset dan_zx:3 dbms:postgresql
create table post_vote (
    post_id bigint not null,
    user_id bigint not null,
    direction varchar(255) not null,
    primary key (post_id, user_id)
)

--changeset dan_zx:4 dbms:postgresql
create table comment (
    comment_id bigserial,
    content text not null,
    created_date timestamp not null,
    updated_date timestamp not null,
    parent_comment_id bigint,
    post_id bigint not null,
    user_id bigint not null,
    primary key (comment_id)
)

--changeset dan_zx:5 dbms:postgresql
create table comment_vote (
    comment_id bigserial,
    user_id bigint not null,
    direction varchar(255) not null,
    primary key (comment_id, user_id)
)

--changeset dan_zx:6 dbms:postgresql
alter table xof_user add constraint xof_user_uk unique (username)

--changeset dan_zx:7 dbms:postgresql
alter table post add constraint post_uk unique (created_date, user_id)

--changeset dan_zx:8 dbms:postgresql
alter table comment add constraint comment_uk unique (created_date, user_id)

--changeset dan_zx:9 dbms:postgresql
alter table post add constraint post_user_fk foreign key (user_id) references xof_user on delete cascade

--changeset dan_zx:10 dbms:postgresql
alter table post_vote add constraint post_vote_post_fk foreign key (post_id) references post on delete cascade

--changeset dan_zx:11 dbms:postgresql
alter table post_vote add constraint post_vote_user_fk foreign key (user_id) references xof_user on delete cascade

--changeset dan_zx:12 dbms:postgresql
alter table comment add constraint comment_parent_fk foreign key (parent_comment_id) references comment on delete cascade

--changeset dan_zx:13 dbms:postgresql
alter table comment add constraint comment_post_fk foreign key (post_id) references post on delete cascade

--changeset dan_zx:14 dbms:postgresql
alter table comment add constraint comment_user_fk foreign key (user_id) references xof_user on delete cascade

--changeset dan_zx:15 dbms:postgresql
alter table comment_vote add constraint comment_vote_comment_fk foreign key (comment_id) references comment on delete cascade

--changeset dan_zx:16 dbms:postgresql
alter table comment_vote add constraint comment_vote_user_fk foreign key (user_id) references xof_user on delete cascade
