drop table if exists team;
drop table if exists staff;
drop table if exists work_time;
drop table if exists paid_vacation;
drop table if exists user_role;

create table team (
  id bigint not null auto_increment,
  name varchar(30) not null,
  short_name varchar(30) not null,
  created_at datetime,
  created_by varchar(30),
  last_modified_at datetime,
  last_modified_by varchar(30),
  primary key (id)
)
engine=InnoDB;

create table staff (
  id bigint not null auto_increment,
  code bigint not null,
  team_id bigint not null,
  name_last varchar(30) not null,
  name_first varchar(30) not null,
  name_last_kana varchar(30) not null,
  name_first_kana varchar(30) not null,
  email varchar(50) not null,
  gender varchar(10) not null,
  employment_type varchar(30) not null,
  entered_date date not null,
  work_time integer,
  flextime bit,
  telework bit,
  disabled bit,
  password varchar(255) not null,
  role varchar(20) not null,
  created_at datetime,
  created_by varchar(30),
  last_modified_at datetime,
  last_modified_by varchar(30),
  primary key (id)
)
engine=InnoDB;

create table staff_history (
  staff_id bigint not null,
  field_name varchar(100),
  before_value varchar(100),
  after_value varchar(100) not null,
  updated_by varchar(30) not null,
  updated_at datetime not null
)
  engine=InnoDB;

create table paid_vacation (
  staff_id bigint not null,
  days integer not null,
  provide_date date not null,
  disappear_date date not null
)
  engine=InnoDB;

create table long_leave (
  id bigint not null auto_increment,
  staff_id bigint not null,
  type varchar(30) not null,
  start_at date not null,
  end_at date,
  remarks varchar(255),
  created_at datetime,
  created_by varchar(255),
  last_modified_at datetime,
  last_modified_by varchar(255),
  primary key (id)
)
  engine=InnoDB;

create table work_time_year_month (
  id bigint not null auto_increment,
  staff_id bigint not null,
  work_year_month int not null,
  completed_at datetime,
  completed_by varchar(30),
  approved1_at datetime,
  approved1_by varchar(30),
  approved2_at datetime,
  approved2_by varchar(30),
  created_at datetime,
  created_by varchar(30),
  last_modified_at datetime,
  last_modified_by varchar(30),
  primary key (id)
)
  engine=InnoDB;

create table work_time (
  work_time_year_month_id bigint not null,
  date date not null,
  work_type varchar(30) not null,
  train_delay bit,
  start_at time,
  end_at time,
  rest_time integer,
  remarks varchar(255)
)
  engine=InnoDB;

create table public_holiday (
  id bigint not null auto_increment,
  year int not null,
  month int not null,
  day_of_month int not null,
  created_at datetime,
  created_by varchar(30),
  last_modified_at datetime,
  last_modified_by varchar(30),
  primary key (id)
)
  engine=InnoDB;



INSERT INTO team (id, name, short_name, created_at, created_by) VALUES (1, 'クリエイティブチーム', 'CR', '2017/07/01 00:00:00', '内立良介');
INSERT INTO team (id, name, short_name, created_at, created_by) VALUES (2, 'セールス＆プロモーションチーム', 'SP', '2017/07/01 00:00:00', '内立良介');
INSERT INTO team (id, name, short_name, created_at, created_by) VALUES (3, '事業推進室', '事推', '2017/07/01 00:00:00', '内立良介');
INSERT INTO team (id, name, short_name, created_at, created_by) VALUES (4, 'マネジメントプランニングチーム', 'MP', '2017/07/01 00:00:00', '内立良介');

INSERT INTO staff(id, code, team_id, name_last, name_first, name_last_kana, name_first_kana, email, gender, employment_type, entered_date, work_time, flextime, telework, disabled, password, role, created_at, created_by)
VALUES (1, 1, 1, '田中', '一郎', 'たなか', 'いちろう', 'tanaka_ichiro@waja.jp', 'MAN', 'PERMANENT_STAFF', '2015/01/01', 8, 1, 1, 0, '$2a$10$7FWRtqvPeMcU/J57SrlFN.U0neZpe2hu8qllj7Ajqw2IhNG0C1FPG', 'MANAGER', '2017/07/01 00:00:00', '内立良介');
INSERT INTO staff(id, code, team_id, name_last, name_first, name_last_kana, name_first_kana, email, gender, employment_type, entered_date, work_time, flextime, telework, disabled, password, role, created_at, created_by)
VALUES (2, 2, 1, '佐藤', '健', 'さとう', 'たける', 'sato_takeru@waja.jp', 'MAN', 'PERMANENT_STAFF', '2014/06/11', 8, 0, 0, 0, '$2a$10$7FWRtqvPeMcU/J57SrlFN.U0neZpe2hu8qllj7Ajqw2IhNG0C1FPG', 'STAFF', '2017/07/01 00:00:00', '内立良介');

INSERT INTO staff(id, code, team_id, name_last, name_first, name_last_kana, name_first_kana, email, gender, employment_type, entered_date, work_time, flextime, telework, disabled, password, role, created_at, created_by)
VALUES (3, 3, 2, '田中', '二郎', 'たなか', 'じろう', 'tanaka_jiro@waja.jp', 'MAN', 'PERMANENT_STAFF', '2015/01/01', 8, 1, 1, 0, '$2a$10$7FWRtqvPeMcU/J57SrlFN.U0neZpe2hu8qllj7Ajqw2IhNG0C1FPG', 'MANAGER', '2017/07/01 00:00:00', '内立良介');
INSERT INTO staff(id, code, team_id, name_last, name_first, name_last_kana, name_first_kana, email, gender, employment_type, entered_date, work_time, flextime, telework, disabled, password, role, created_at, created_by)
VALUES (4, 4, 2, '鈴木', '隆', 'すずき', 'たかし', 'suzuki_takashi@waja.jp', 'MAN', 'PERMANENT_STAFF', '2015/01/01', 8, 1, 1, 0, '$2a$10$7FWRtqvPeMcU/J57SrlFN.U0neZpe2hu8qllj7Ajqw2IhNG0C1FPG', 'STAFF', '2017/07/01 00:00:00', '内立良介');

INSERT INTO staff(id, code, team_id, name_last, name_first, name_last_kana, name_first_kana, email, gender, employment_type, entered_date, work_time, flextime, telework, disabled, password, role, created_at, created_by)
VALUES (5, 5, 3, '長澤', 'まさみ', 'ながさわ', 'まさみ', 'nagasawa_masami@waja.jp', 'WOMAN', 'PERMANENT_STAFF', '2015/01/01', 6, 1, 1, 0, '$2a$10$7FWRtqvPeMcU/J57SrlFN.U0neZpe2hu8qllj7Ajqw2IhNG0C1FPG', 'MANAGER', '2017/07/01 00:00:00', '内立良介');
INSERT INTO staff(id, code, team_id, name_last, name_first, name_last_kana, name_first_kana, email, gender, employment_type, entered_date, work_time, flextime, telework, disabled, password, role, created_at, created_by)
VALUES (6, 6, 3, '小安', '光司', 'こやす', 'こうじ', 'koyasu_koji@waja.jp', 'MAN', 'PERMANENT_STAFF', '2015/01/01', 8, 0, 0, 0, '$2a$10$7FWRtqvPeMcU/J57SrlFN.U0neZpe2hu8qllj7Ajqw2IhNG0C1FPG', 'STAFF', '2017/07/01 00:00:00', '内立良介');

INSERT INTO staff(id, code, team_id, name_last, name_first, name_last_kana, name_first_kana, email, gender, employment_type, entered_date, work_time, flextime, telework, disabled, password, role, created_at, created_by)
VALUES (7, 7, 4, '管理者', '太郎', 'かんりしゃ', 'たろう', 'kanrisya_taro@waja.jp', 'WOMAN', 'PERMANENT_STAFF', '2016/10/01', 8, 1, 1, 0, '$2a$10$7FWRtqvPeMcU/J57SrlFN.U0neZpe2hu8qllj7Ajqw2IhNG0C1FPG', 'ADMIN', '2017/07/01 00:00:00', '内立良介');
INSERT INTO staff(id, code, team_id, name_last, name_first, name_last_kana, name_first_kana, email, gender, employment_type, entered_date, work_time, flextime, telework, disabled, password, role, created_at, created_by)
VALUES (8, 8, 4, '利用者', '花子', 'りしょうしゃ', 'はなこ', 'riyousya_hanako@waja.jp', 'MAN', 'PERMANENT_STAFF', '2017/02/04', 6, 0, 0, 0, '$2a$10$7FWRtqvPeMcU/J57SrlFN.U0neZpe2hu8qllj7Ajqw2IhNG0C1FPG', 'STAFF', '2017/07/01 00:00:00', '内立良介');

INSERT INTO paid_vacation (staff_id, days, provide_date, disappear_date) VALUES (1, 10, '2017/07/01', '2019/06/30');
INSERT INTO paid_vacation (staff_id, days, provide_date, disappear_date) VALUES (2, 13, '2017/07/01', '2019/06/30');
INSERT INTO paid_vacation (staff_id, days, provide_date, disappear_date) VALUES (3, 12, '2017/07/01', '2019/06/30');
INSERT INTO paid_vacation (staff_id, days, provide_date, disappear_date) VALUES (4, 10, '2017/07/01', '2019/06/30');
INSERT INTO paid_vacation (staff_id, days, provide_date, disappear_date) VALUES (5, 10, '2017/07/01', '2019/06/30');
INSERT INTO paid_vacation (staff_id, days, provide_date, disappear_date) VALUES (6, 10, '2017/07/01', '2019/06/30');
INSERT INTO paid_vacation (staff_id, days, provide_date, disappear_date) VALUES (7, 21, '2015/08/01', '2017/07/31');
INSERT INTO paid_vacation (staff_id, days, provide_date, disappear_date) VALUES (8, 10, '2015/04/01', '2017/03/30');

INSERT INTO work_time_year_month (id, staff_id, work_year_month, created_at, created_by) VALUES (1, 1, 20178, '2017/07/01 00:00:00', '内立良介');

INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (1, '2017/08/01', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (1, '2017/08/02', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (1, '2017/08/03', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (1, '2017/08/04', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (1, '2017/08/05', 'LEGAL_VACATION', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (1, '2017/08/06', 'LEGAL_VACATION', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (1, '2017/08/07', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (1, '2017/08/08', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (1, '2017/08/09', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (1, '2017/08/10', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (1, '2017/08/11', 'LEGAL_VACATION', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (1, '2017/08/12', 'LEGAL_VACATION', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (1, '2017/08/13', 'LEGAL_VACATION', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (1, '2017/08/14', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (1, '2017/08/15', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (1, '2017/08/1', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (1, '2017/08/17', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (1, '2017/08/18', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (1, '2017/08/19', 'LEGAL_VACATION', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (1, '2017/08/20', 'LEGAL_VACATION', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (1, '2017/08/21', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (1, '2017/08/22', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (1, '2017/08/23', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (1, '2017/08/24', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (1, '2017/08/25', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (1, '2017/08/26', 'LEGAL_VACATION', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (1, '2017/08/27', 'LEGAL_VACATION', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (1, '2017/08/28', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (1, '2017/08/29', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (1, '2017/08/30', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (1, '2017/08/30', 'NORMAL', '');

INSERT INTO public_holiday (id, year, month, day_of_month, created_at, created_by) VALUES (1, 2017, 1, 1, '2017/07/01 00:00:00', '内立良介');
INSERT INTO public_holiday (id, year, month, day_of_month, created_at, created_by) VALUES (2, 2017, 1, 2, '2017/07/01 00:00:00', '内立良介');
INSERT INTO public_holiday (id, year, month, day_of_month, created_at, created_by) VALUES (3, 2017, 1, 3, '2017/07/01 00:00:00', '内立良介');
INSERT INTO public_holiday (id, year, month, day_of_month, created_at, created_by) VALUES (4, 2017, 1, 9, '2017/07/01 00:00:00', '内立良介');
INSERT INTO public_holiday (id, year, month, day_of_month, created_at, created_by) VALUES (5, 2017, 2, 11, '2017/07/01 00:00:00', '内立良介');
INSERT INTO public_holiday (id, year, month, day_of_month, created_at, created_by) VALUES (6, 2017, 3, 20, '2017/07/01 00:00:00', '内立良介');
INSERT INTO public_holiday (id, year, month, day_of_month, created_at, created_by) VALUES (7, 2017, 4, 29, '2017/07/01 00:00:00', '内立良介');
INSERT INTO public_holiday (id, year, month, day_of_month, created_at, created_by) VALUES (8, 2017, 5, 3, '2017/07/01 00:00:00', '内立良介');
INSERT INTO public_holiday (id, year, month, day_of_month, created_at, created_by) VALUES (9, 2017, 5, 4, '2017/07/01 00:00:00', '内立良介');
INSERT INTO public_holiday (id, year, month, day_of_month, created_at, created_by) VALUES (10, 2017, 5, 5, '2017/07/01 00:00:00', '内立良介');
INSERT INTO public_holiday (id, year, month, day_of_month, created_at, created_by) VALUES (11, 2017, 7, 17, '2017/07/01 00:00:00', '内立良介');
INSERT INTO public_holiday (id, year, month, day_of_month, created_at, created_by) VALUES (12, 2017, 8, 11, '2017/07/01 00:00:00', '内立良介');
INSERT INTO public_holiday (id, year, month, day_of_month, created_at, created_by) VALUES (13, 2017, 9, 18, '2017/07/01 00:00:00', '内立良介');
INSERT INTO public_holiday (id, year, month, day_of_month, created_at, created_by) VALUES (14, 2017, 9, 23, '2017/07/01 00:00:00', '内立良介');
INSERT INTO public_holiday (id, year, month, day_of_month, created_at, created_by) VALUES (15, 2017, 10, 9, '2017/07/01 00:00:00', '内立良介');
INSERT INTO public_holiday (id, year, month, day_of_month, created_at, created_by) VALUES (16, 2017, 11, 3, '2017/07/01 00:00:00', '内立良介');
INSERT INTO public_holiday (id, year, month, day_of_month, created_at, created_by) VALUES (17, 2017, 11, 23, '2017/07/01 00:00:00', '内立良介');
INSERT INTO public_holiday (id, year, month, day_of_month, created_at, created_by) VALUES (18, 2017, 12, 23, '2017/07/01 00:00:00', '内立良介');
INSERT INTO public_holiday (id, year, month, day_of_month, created_at, created_by) VALUES (19, 2018, 1, 1, '2017/07/01 00:00:00', '内立良介');
INSERT INTO public_holiday (id, year, month, day_of_month, created_at, created_by) VALUES (20, 2018, 1, 2, '2017/07/01 00:00:00', '内立良介');
INSERT INTO public_holiday (id, year, month, day_of_month, created_at, created_by) VALUES (21, 2018, 1, 3, '2017/07/01 00:00:00', '内立良介');

alter table staff add constraint UK_9iyphxhcq6d836q8vrykuw6vx unique (code);
alter table staff add constraint UK_pvctx4dbua9qh4p4s3gm3scrh unique (email);
alter table paid_vacation add constraint FKjk3lekaqd3mnn5432w6lsxtys foreign key (staff_id) references staff (id);
alter table staff add constraint FKmdoqnc1maraqd1w8ptjpk1i9r foreign key (team_id) references team (id);
alter table long_leave add constraint FKi4hrwnaesljcq8p801vlfvr78 foreign key (staff_id) references staff (id);
alter table work_time add constraint FKewu2afn7kycb1qkdyd3njg9mk foreign key (work_time_year_month_id) references work_time_year_month (id);
alter table work_time_year_month add constraint FKhl16odltapeepn6bbvvtwgjuu foreign key (staff_id) references staff (id);
alter table staff_history add constraint FKrfgodmtowgdcdpps3cqahwqeo foreign key (staff_id) references staff (id);
