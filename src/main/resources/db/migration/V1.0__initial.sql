drop table if exists team;
drop table if exists staff;
drop table if exists work_time;
drop table if exists paid_vacation;
drop table if exists user_role;

create table team (
  id bigint not null auto_increment,
  name varchar(255) not null,
  short_name varchar(255) not null,
  created_at datetime,
  created_by varchar(255),
  last_modified_at datetime,
  last_modified_by varchar(255),
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
  gender varchar(10) not null,
  employment_type varchar(255) not null,
  entered_date date not null,
  telework bit,
  password varchar(255) not null,
  created_at datetime,
  created_by varchar(255),
  last_modified_at datetime,
  last_modified_by varchar(255),
  primary key (id)
)
engine=InnoDB;

create table paid_vacation (
  id bigint not null auto_increment,
  staff_id bigint,
  days integer not null,
  provide_date date not null,
  disappear_date date not null,
  created_at datetime,
  created_by varchar(255),
  last_modified_at datetime,
  last_modified_by varchar(255),
  primary key (id)
)
engine=InnoDB;

create table work_time (
  id bigint not null auto_increment,
  staff_id bigint,
  date date not null,
  work_type varchar(50) not null,
  start_at time,
  end_at time,
  rest_time integer,
  remarks varchar(255),
  created_at datetime,
  created_by varchar(255),
  last_modified_at datetime,
  last_modified_by varchar(255),
  primary key (id)
)
engine=InnoDB;

create table user_role (
  staff_id bigint not null,
  role varchar(20) not null,
  primary key (staff_id, role)
)
engine=InnoDB;

INSERT INTO team (id, name, short_name, created_at, created_by) VALUES (1, 'クリエイティブチーム', 'CR', '2017/07/01 00:00:00', '内立良介');
INSERT INTO team (id, name, short_name, created_at, created_by) VALUES (2, 'セールス＆プロモーションチーム', 'SP', '2017/07/01 00:00:00', '内立良介');
INSERT INTO team (id, name, short_name, created_at, created_by) VALUES (3, '事業推進室', '事推', '2017/07/01 00:00:00', '内立良介');
INSERT INTO team (id, name, short_name, created_at, created_by) VALUES (4, 'フルフィルメントチーム', 'FF', '2017/07/01 00:00:00', '内立良介');
INSERT INTO team (id, name, short_name, created_at, created_by) VALUES (5, 'マネジメントプランニングチーム', 'MP', '2017/07/01 00:00:00', '内立良介');

INSERT INTO staff(id, team_id, name_last, name_first, name_last_kana, name_first_kana, email, gender, employment_type, entered_date, telework, password, created_at, created_by)
VALUES (6, 1, '田中', '一郎', 'たなか', 'いちろう', 'tanaka_taro@waja.jp', 'MAN', 'PERMANENT_STAFF', '2015/01/01', 1, 'wajawaja', '2017/07/01 00:00:00', '内立良介');
INSERT INTO staff(id, team_id, name_last, name_first, name_last_kana, name_first_kana, email, gender, employment_type, entered_date, telework, password, created_at, created_by)
VALUES (7, 2, '田中', '二郎', 'たなか', 'じろう', 'tanaka_jiro@waja.jp', 'MAN', 'CREW_TWO', '2015/01/01', 1, 'wajawaja', '2017/07/01 00:00:00', '内立良介');
INSERT INTO staff(id, team_id, name_last, name_first, name_last_kana, name_first_kana, email, gender, employment_type, entered_date, telework, password, created_at, created_by)
VALUES (8, 3, '長澤', 'まさみ', 'ながさわ', 'まさみ', 'nagasawa_masami@waja.jp', 'WOMAN', 'CREW_THREE', '2015/01/01', 1, 'wajawaja', '2017/07/01 00:00:00', '内立良介');
INSERT INTO staff(id, team_id, name_last, name_first, name_last_kana, name_first_kana, email, gender, employment_type, entered_date, telework, password, created_at, created_by)
VALUES (9, 4, '田中', '四郎', 'たなか', 'しろう', 'tanaka_shiro@waja.jp', 'MAN', 'CREW_FOUR', '2015/01/01', 1, 'wajawaja', '2017/07/01 00:00:00', '内立良介');
INSERT INTO staff(id, team_id, name_last, name_first, name_last_kana, name_first_kana, email, gender, employment_type, entered_date, telework, password, created_at, created_by)
VALUES (10, 5, '鈴木', '花子', 'すずき', 'はなこ', 'suzuki_hanako@waja.jp', 'WOMAN', 'PERMANENT_STAFF', '2015/01/01', 1, 'wajawaja', '2017/07/01 00:00:00', '内立良介');

INSERT INTO paid_vacation (id, staff_id, days, provide_date, disappear_date, created_at, created_by) VALUES (11, 6, 10, '2017/07/01', '2019/06/30', '2017/07/01 00:00:00', '内立良介');
INSERT INTO paid_vacation (id, staff_id, days, provide_date, disappear_date, created_at, created_by) VALUES (12, 7, 21, '2015/08/01', '2017/07/31', '2017/07/01 00:00:00', '内立良介');
INSERT INTO paid_vacation (id, staff_id, days, provide_date, disappear_date, created_at, created_by) VALUES (13, 10, 10, '2015/04/01', '2017/03/30', '2017/07/01 00:00:00', '内立良介');
INSERT INTO paid_vacation (id, staff_id, days, provide_date, disappear_date, created_at, created_by) VALUES (14, 10, 11, '2016/04/01', '2018/03/30', '2017/07/01 00:00:00', '内立良介');
INSERT INTO paid_vacation (id, staff_id, days, provide_date, disappear_date, created_at, created_by) VALUES (15, 10, 12, '2017/04/01', '2019/03/30', '2017/07/01 00:00:00', '内立良介');

INSERT INTO work_time (id, staff_id, date, work_type, remarks, created_at, created_by) VALUES (16, 6, '2017/07/01', 'LEGAL_VACATION', '', '2017/07/01 00:00:00', '内立良介');
INSERT INTO work_time (id, staff_id, date, work_type, remarks, created_at, created_by) VALUES (17, 6, '2017/07/02', 'LEGAL_VACATION', '', '2017/07/01 00:00:00', '内立良介');
INSERT INTO work_time (id, staff_id, date, work_type, start_at, end_at, rest_time, remarks, created_at, created_by) VALUES (19, 6, '2017/07/03', 'NORMAL', '9:00:00', '18:00:00', '60', '', '2017/07/01 00:00:00', '内立良介');
INSERT INTO work_time (id, staff_id, date, work_type, start_at, end_at, rest_time, remarks, created_at, created_by) VALUES (20, 6, '2017/07/04', 'NORMAL', '9:00:00', '18:00:00', '60', '', '2017/07/01 00:00:00', '内立良介');
INSERT INTO work_time (id, staff_id, date, work_type, start_at, end_at, rest_time, remarks, created_at, created_by) VALUES (22, 6, '2017/07/05', 'NORMAL', '9:00:00', '18:00:00', '60', '', '2017/07/01 00:00:00', '内立良介');
INSERT INTO work_time (id, staff_id, date, work_type, start_at, end_at, rest_time, remarks, created_at, created_by) VALUES (23, 6, '2017/07/06', 'NORMAL', '9:00:00', '18:00:00', '60', '', '2017/07/01 00:00:00', '内立良介');
INSERT INTO work_time (id, staff_id, date, work_type, start_at, end_at, rest_time, remarks, created_at, created_by) VALUES (24, 6, '2017/07/07', 'NORMAL', '9:00:00', '18:00:00', '60', '', '2017/07/01 00:00:00', '内立良介');
INSERT INTO work_time (id, staff_id, date, work_type, remarks, created_at, created_by) VALUES (25, 6, '2017/07/08', 'LEGAL_VACATION', '', '2017/07/01 00:00:00', '内立良介');
INSERT INTO work_time (id, staff_id, date, work_type, remarks, created_at, created_by) VALUES (26, 6, '2017/07/09', 'LEGAL_VACATION', '', '2017/07/01 00:00:00', '内立良介');
INSERT INTO work_time (id, staff_id, date, work_type, start_at, end_at, rest_time, remarks, created_at, created_by) VALUES (27, 6, '2017/07/10', 'NORMAL', '9:00:00', '18:00:00', '60', '', '2017/07/01 00:00:00', '内立良介');
INSERT INTO work_time (id, staff_id, date, work_type, start_at, end_at, rest_time, remarks, created_at, created_by) VALUES (28, 6, '2017/07/11', 'NORMAL', '9:00:00', '18:00:00', '60', '', '2017/07/01 00:00:00', '内立良介');
INSERT INTO work_time (id, staff_id, date, work_type, start_at, end_at, rest_time, remarks, created_at, created_by) VALUES (29, 6, '2017/07/12', 'NORMAL', '9:00:00', '18:00:00', '60', '', '2017/07/01 00:00:00', '内立良介');
INSERT INTO work_time (id, staff_id, date, work_type, start_at, end_at, rest_time, remarks, created_at, created_by) VALUES (30, 6, '2017/07/13', 'NORMAL', '9:00:00', '18:00:00', '60', '', '2017/07/01 00:00:00', '内立良介');
INSERT INTO work_time (id, staff_id, date, work_type, start_at, end_at, rest_time, remarks, created_at, created_by) VALUES (31, 6, '2017/07/14', 'NORMAL', '9:00:00', '18:00:00', '60', '', '2017/07/01 00:00:00', '内立良介');
INSERT INTO work_time (id, staff_id, date, work_type, remarks, created_at, created_by) VALUES (32, 6, '2017/07/15', 'LEGAL_VACATION', '', '2017/07/01 00:00:00', '内立良介');
INSERT INTO work_time (id, staff_id, date, work_type, remarks, created_at, created_by) VALUES (33, 6, '2017/07/16', 'LEGAL_VACATION', '', '2017/07/01 00:00:00', '内立良介');
INSERT INTO work_time (id, staff_id, date, work_type, remarks, created_at, created_by) VALUES (34, 6, '2017/07/17', 'LEGAL_VACATION', '', '2017/07/01 00:00:00', '内立良介');
INSERT INTO work_time (id, staff_id, date, work_type, start_at, end_at, rest_time, remarks, created_at, created_by) VALUES (35, 6, '2017/07/18', 'NORMAL', '9:00:00', '18:00:00', '60', '', '2017/07/01 00:00:00', '内立良介');
INSERT INTO work_time (id, staff_id, date, work_type, start_at, end_at, rest_time, remarks, created_at, created_by) VALUES (36, 6, '2017/07/19', 'NORMAL', '9:00:00', '18:00:00', '60', '', '2017/07/01 00:00:00', '内立良介');
INSERT INTO work_time (id, staff_id, date, work_type, start_at, end_at, rest_time, remarks, created_at, created_by) VALUES (37, 6, '2017/07/20', 'NORMAL', '9:00:00', '18:00:00', '60', '', '2017/07/01 00:00:00', '内立良介');
INSERT INTO work_time (id, staff_id, date, work_type, start_at, end_at, rest_time, remarks, created_at, created_by) VALUES (38, 6, '2017/07/21', 'NORMAL', '9:00:00', '18:00:00', '60', '', '2017/07/01 00:00:00', '内立良介');
INSERT INTO work_time (id, staff_id, date, work_type, remarks, created_at, created_by) VALUES (39, 6, '2017/07/22', 'LEGAL_VACATION', '', '2017/07/01 00:00:00', '内立良介');
INSERT INTO work_time (id, staff_id, date, work_type, remarks, created_at, created_by) VALUES (40, 6, '2017/07/23', 'LEGAL_VACATION', '', '2017/07/01 00:00:00', '内立良介');
INSERT INTO work_time (id, staff_id, date, work_type, start_at, end_at, rest_time, remarks, created_at, created_by) VALUES (41, 6, '2017/07/24', 'NORMAL', '9:00:00', '18:00:00', '60', '', '2017/07/01 00:00:00', '内立良介');
INSERT INTO work_time (id, staff_id, date, work_type, start_at, end_at, rest_time, remarks, created_at, created_by) VALUES (42, 6, '2017/07/25', 'NORMAL', '9:00:00', '18:00:00', '60', '', '2017/07/01 00:00:00', '内立良介');
INSERT INTO work_time (id, staff_id, date, work_type, start_at, end_at, rest_time, remarks, created_at, created_by) VALUES (43, 6, '2017/07/26', 'NORMAL', '9:00:00', '18:00:00', '60', '', '2017/07/01 00:00:00', '内立良介');
INSERT INTO work_time (id, staff_id, date, work_type, start_at, end_at, rest_time, remarks, created_at, created_by) VALUES (44, 6, '2017/07/27', 'NORMAL', '9:00:00', '18:00:00', '60', '', '2017/07/01 00:00:00', '内立良介');
INSERT INTO work_time (id, staff_id, date, work_type, start_at, end_at, rest_time, remarks, created_at, created_by) VALUES (45, 6, '2017/07/28', 'NORMAL', '9:00:00', '18:00:00', '60', '', '2017/07/01 00:00:00', '内立良介');
INSERT INTO work_time (id, staff_id, date, work_type, remarks, created_at, created_by) VALUES (46, 6, '2017/07/29', 'LEGAL_VACATION', '', '2017/07/01 00:00:00', '内立良介');
INSERT INTO work_time (id, staff_id, date, work_type, remarks, created_at, created_by) VALUES (47, 6, '2017/07/30', 'LEGAL_VACATION', '', '2017/07/01 00:00:00', '内立良介');
INSERT INTO work_time (id, staff_id, date, work_type, start_at, end_at, rest_time, remarks, created_at, created_by) VALUES (48, 6, '2017/07/31', 'NORMAL', '9:00:00', '18:00:00', '60', '', '2017/07/01 00:00:00', '内立良介');

INSERT INTO user_role (staff_id, role) VALUES (6, 'STAFF');
INSERT INTO user_role (staff_id, role) VALUES (7, 'ADMIN');
INSERT INTO user_role (staff_id, role) VALUES (8, 'STAFF');
INSERT INTO user_role (staff_id, role) VALUES (9, 'STAFF');
INSERT INTO user_role (staff_id, role) VALUES (10, 'MANAGER');

alter table paid_vacation add constraint FKjk3lekaqd3mnn5432w6lsxtys foreign key (staff_id) references staff (id);
alter table staff add constraint FKmdoqnc1maraqd1w8ptjpk1i9r foreign key (team_id) references team (id);
alter table user_role add constraint FKilbm7ghgjn6sgtr1p9g138bk2 foreign key (staff_id) references staff (id);
alter table work_time add constraint FK86tblnddtvonw25p15pap03l3 foreign key (staff_id) references staff (id);
