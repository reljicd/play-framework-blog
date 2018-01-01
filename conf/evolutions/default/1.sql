# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table comment (
  id                            bigint auto_increment not null,
  body                          TEXT,
  post_id                       bigint,
  user_id                       bigint,
  create_date                   timestamp not null,
  constraint pk_comment primary key (id)
);

create table post (
  id                            bigint auto_increment not null,
  title                         varchar(255) not null,
  body                          TEXT,
  user_id                       bigint,
  create_date                   timestamp not null,
  constraint pk_post primary key (id)
);

create table role (
  id                            bigint auto_increment not null,
  role                          varchar(255),
  constraint uq_role_role unique (role),
  constraint pk_role primary key (id)
);

create table user (
  id                            bigint auto_increment not null,
  username                      varchar(255) not null,
  password                      varchar(255) not null,
  email                         varchar(255) not null,
  first_name                    varchar(255),
  last_name                     varchar(255),
  active                        integer not null,
  constraint uq_user_username unique (username),
  constraint uq_user_email unique (email),
  constraint pk_user primary key (id)
);

create table user_role (
  user_id                       bigint not null,
  role_id                       bigint not null,
  constraint pk_user_role primary key (user_id,role_id)
);

alter table comment add constraint fk_comment_post_id foreign key (post_id) references post (id) on delete restrict on update restrict;
create index ix_comment_post_id on comment (post_id);

alter table comment add constraint fk_comment_user_id foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_comment_user_id on comment (user_id);

alter table post add constraint fk_post_user_id foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_post_user_id on post (user_id);

alter table user_role add constraint fk_user_role_user foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_user_role_user on user_role (user_id);

alter table user_role add constraint fk_user_role_role foreign key (role_id) references role (id) on delete restrict on update restrict;
create index ix_user_role_role on user_role (role_id);


# --- !Downs

alter table comment drop constraint if exists fk_comment_post_id;
drop index if exists ix_comment_post_id;

alter table comment drop constraint if exists fk_comment_user_id;
drop index if exists ix_comment_user_id;

alter table post drop constraint if exists fk_post_user_id;
drop index if exists ix_post_user_id;

alter table user_role drop constraint if exists fk_user_role_user;
drop index if exists ix_user_role_user;

alter table user_role drop constraint if exists fk_user_role_role;
drop index if exists ix_user_role_role;

drop table if exists comment;

drop table if exists post;

drop table if exists role;

drop table if exists user;

drop table if exists user_role;

