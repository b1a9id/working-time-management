drop table if exists team;
drop table if exists staff;
drop table if exists work_time;
drop table if exists paid_vacation;
drop table if exists user_role;

create table team (
  id bigint not null auto_increment,
  name varchar(255) not null,
  short_name varchar(255) not null,
  created_at datetime not null,
  created_by varchar(255) not null,
  last_modified_at datetime not null,
  last_modified_by varchar(255) not null,
  primary key (id)
)
engine=InnoDB;

create table staff (
  id bigint not null auto_increment,
  team_id bigint not null,
  name_last varchar(255) not null,
  name_first varchar(255) not null,
  name_last_kana varchar(255) not null,
  name_first_kana varchar(255) not null,
  email varchar(255) not null,
  employment_type varchar(255) not null,
  entered_date date not null,
  telework bit,
  password varchar(255) not null,
  created_at datetime not null,
  created_by varchar(255) not null,
  last_modified_at datetime not null,
  last_modified_by varchar(255) not null,
  primary key (id)
)
engine=InnoDB;

create table paid_vacation (
  id bigint not null auto_increment,
  staff_id bigint,
  days integer not null,
  provide_date date not null,
  disappear_date date not null,
  created_at datetime not null,
  created_by varchar(255) not null,
  last_modified_at datetime not null,
  last_modified_by varchar(255) not null,
  primary key (id)
)
engine=InnoDB;

create table work_time (
  id bigint not null auto_increment,
  staff_id bigint,
  date datetime not null,
  work_type integer not null,
  start_at time not null,
  end_at time not null,
  rest_time integer not null,
  remarks varchar(255),
  created_at datetime not null,
  created_by varchar(255) not null,
  last_modified_at datetime not null,
  last_modified_by varchar(255) not null,
  primary key (id)
)
engine=InnoDB;

create table user_role (
  staff_id bigint not null,
  role varchar(20) not null,
  primary key (staff_id, role)
)
engine=InnoDB;
