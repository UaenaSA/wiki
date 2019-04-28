create database wiki;
use wiki;


create table answer
(
  id                varchar(40)  not null
    primary key,
  problem_id        varchar(40)  null,
  author            varchar(255) null,
  problem_reason    varchar(255) null,
  replay_time       datetime     null
  on update CURRENT_TIMESTAMP,
  advise            varchar(255) null,
  problem_level     varchar(255) null
  comment '0: nonmal 1:warm 2:import',
  answer_append_url varchar(255) null
)
  engine = InnoDB;

create table log
(
  id      varchar(40)  not null
    primary key,
  content varchar(255) null,
  type    int(1)       null
  comment '0:info 2:warn 3:error ',
  time    datetime     null
  on update CURRENT_TIMESTAMP,
  user    varchar(255) null
)
  engine = InnoDB;

create table problem
(
  id                 varchar(40)  not null
    primary key,
  problem_id         varchar(40)  null,
  module             int(1)       null
  comment '0：FDS ,1:MCS 2:IMS 3:TMS',
  type               int(1)       null
  comment '0：COMMON ,1:CASE 2:tree',
  title              varchar(255) null,
  status             int(1)       null
  comment '0：submit ,1:replyed   2:solved   3:closed',
  submit_time       datetime     null
  on update CURRENT_TIMESTAMP,
  last_modify_time   datetime     null
  on update CURRENT_TIMESTAMP,
  problem_append_url varchar(255) null,
  author             varchar(255) null,
  assign_experts     varchar(255) null,
  problem_desc       varchar(255) null
)
  engine = InnoDB;

create table user
(
  id          varchar(40)  not null
    primary key,
  user_name   varchar(255) null,
  password    varchar(255) null,
  create_time datetime     null
  on update CURRENT_TIMESTAMP,
  role_id     int(1)       null
  comment '0:admin 1:normalUser  2:expert'
)
  DEFAULT CHARSET=utf8
  engine = InnoDB;

