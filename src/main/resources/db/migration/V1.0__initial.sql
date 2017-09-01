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
  disabled bit,
  password varchar(255) not null,
  role varchar(20) not null,
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

create table work_time_year_month (
  id bigint not null auto_increment,
  staff_id bigint not null,
  work_year_month int not null,
  complete bit,
  approve1 bit,
  approve2 bit,
  created_at datetime,
  created_by varchar(255),
  last_modified_at datetime,
  last_modified_by varchar(255),
  primary key (id)
)
  engine=InnoDB;

create table work_time (
  work_time_year_month_id bigint not null,
  date date not null,
  work_type varchar(255) not null,
  start_at time,
  end_at time,
  rest_time integer,
  remarks varchar(255)
)
  engine=InnoDB;



INSERT INTO team (id, name, short_name, created_at, created_by) VALUES (1, 'クリエイティブチーム', 'CR', '2017/07/01 00:00:00', '内立良介');
INSERT INTO team (id, name, short_name, created_at, created_by) VALUES (2, 'セールス＆プロモーションチーム', 'SP', '2017/07/01 00:00:00', '内立良介');
INSERT INTO team (id, name, short_name, created_at, created_by) VALUES (3, '事業推進室', '事推', '2017/07/01 00:00:00', '内立良介');
INSERT INTO team (id, name, short_name, created_at, created_by) VALUES (4, 'フルフィルメントチーム', 'FF', '2017/07/01 00:00:00', '内立良介');
INSERT INTO team (id, name, short_name, created_at, created_by) VALUES (5, 'マネジメントプランニングチーム', 'MP', '2017/07/01 00:00:00', '内立良介');

INSERT INTO staff(id, team_id, name_last, name_first, name_last_kana, name_first_kana, email, gender, employment_type, entered_date, telework, disabled, password, role, created_at, created_by)
VALUES (6, 1, '田中', '一郎', 'たなか', 'いちろう', 'tanaka_taro@waja.jp', 'MAN', 'PERMANENT_STAFF', '2015/01/01', 1, 0, '$2a$10$7FWRtqvPeMcU/J57SrlFN.U0neZpe2hu8qllj7Ajqw2IhNG0C1FPG', 'STAFF', '2017/07/01 00:00:00', '内立良介');
INSERT INTO staff(id, team_id, name_last, name_first, name_last_kana, name_first_kana, email, gender, employment_type, entered_date, telework, disabled, password, role, created_at, created_by)
VALUES (7, 2, '田中', '二郎', 'たなか', 'じろう', 'tanaka_jiro@waja.jp', 'MAN', 'CREW_TWO', '2015/01/01', 1, 0, '$2a$10$7FWRtqvPeMcU/J57SrlFN.U0neZpe2hu8qllj7Ajqw2IhNG0C1FPG', 'ADMIN', '2017/07/01 00:00:00', '内立良介');
INSERT INTO staff(id, team_id, name_last, name_first, name_last_kana, name_first_kana, email, gender, employment_type, entered_date, telework, disabled, password, role, created_at, created_by)
VALUES (8, 3, '長澤', 'まさみ', 'ながさわ', 'まさみ', 'nagasawa_masami@waja.jp', 'WOMAN', 'CREW_THREE', '2015/01/01', 1, 0, '$2a$10$7FWRtqvPeMcU/J57SrlFN.U0neZpe2hu8qllj7Ajqw2IhNG0C1FPG', 'STAFF', '2017/07/01 00:00:00', '内立良介');
INSERT INTO staff(id, team_id, name_last, name_first, name_last_kana, name_first_kana, email, gender, employment_type, entered_date, telework, disabled, password, role, created_at, created_by)
VALUES (9, 4, '田中', '四郎', 'たなか', 'しろう', 'tanaka_shiro@waja.jp', 'MAN', 'CREW_FOUR', '2015/01/01', 1, 0, '$2a$10$7FWRtqvPeMcU/J57SrlFN.U0neZpe2hu8qllj7Ajqw2IhNG0C1FPG', 'STAFF', '2017/07/01 00:00:00', '内立良介');
INSERT INTO staff(id, team_id, name_last, name_first, name_last_kana, name_first_kana, email, gender, employment_type, entered_date, telework, disabled, password, role, created_at, created_by)
VALUES (10, 5, '鈴木', '花子', 'すずき', 'はなこ', 'suzuki_hanako@waja.jp', 'WOMAN', 'PERMANENT_STAFF', '2015/01/01', 1, 0, '$2a$10$7FWRtqvPeMcU/J57SrlFN.U0neZpe2hu8qllj7Ajqw2IhNG0C1FPG', 'MANAGER', '2017/07/01 00:00:00', '内立良介');

INSERT INTO paid_vacation (id, staff_id, days, provide_date, disappear_date, created_at, created_by) VALUES (11, 6, 10, '2017/07/01', '2019/06/30', '2017/07/01 00:00:00', '内立良介');
INSERT INTO paid_vacation (id, staff_id, days, provide_date, disappear_date, created_at, created_by) VALUES (12, 7, 21, '2015/08/01', '2017/07/31', '2017/07/01 00:00:00', '内立良介');
INSERT INTO paid_vacation (id, staff_id, days, provide_date, disappear_date, created_at, created_by) VALUES (13, 10, 10, '2015/04/01', '2017/03/30', '2017/07/01 00:00:00', '内立良介');
INSERT INTO paid_vacation (id, staff_id, days, provide_date, disappear_date, created_at, created_by) VALUES (14, 10, 11, '2016/04/01', '2018/03/30', '2017/07/01 00:00:00', '内立良介');
INSERT INTO paid_vacation (id, staff_id, days, provide_date, disappear_date, created_at, created_by) VALUES (15, 10, 12, '2017/04/01', '2019/03/30', '2017/07/01 00:00:00', '内立良介');

INSERT INTO work_time_year_month (id, staff_id, work_year_month, complete, approve1, approve2, created_at, created_by) VALUES (16, 6, 20178, 0, 0, 0, '2017/07/01 00:00:00', '内立良介');

INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (16, '2017/08/01', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (16, '2017/08/02', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (16, '2017/08/03', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (16, '2017/08/04', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (16, '2017/08/05', 'LEGAL_VACATION', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (16, '2017/08/06', 'LEGAL_VACATION', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (16, '2017/08/07', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (16, '2017/08/08', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (16, '2017/08/09', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (16, '2017/08/10', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (16, '2017/08/11', 'LEGAL_VACATION', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (16, '2017/08/12', 'LEGAL_VACATION', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (16, '2017/08/13', 'LEGAL_VACATION', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (16, '2017/08/14', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (16, '2017/08/15', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (16, '2017/08/16', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (16, '2017/08/17', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (16, '2017/08/18', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (16, '2017/08/19', 'LEGAL_VACATION', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (16, '2017/08/20', 'LEGAL_VACATION', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (16, '2017/08/21', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (16, '2017/08/22', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (16, '2017/08/23', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (16, '2017/08/24', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (16, '2017/08/25', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (16, '2017/08/26', 'LEGAL_VACATION', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (16, '2017/08/27', 'LEGAL_VACATION', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (16, '2017/08/28', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (16, '2017/08/29', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (16, '2017/08/30', 'NORMAL', '');
INSERT INTO work_time (work_time_year_month_id, date, work_type, remarks) VALUES (16, '2017/08/30', 'NORMAL', '');

alter table staff add constraint UK_pvctx4dbua9qh4p4s3gm3scrh unique (email);
alter table paid_vacation add constraint FKjk3lekaqd3mnn5432w6lsxtys foreign key (staff_id) references staff (id);
alter table staff add constraint FKmdoqnc1maraqd1w8ptjpk1i9r foreign key (team_id) references team (id);
alter table work_time add constraint FKewu2afn7kycb1qkdyd3njg9mk foreign key (work_time_year_month_id) references work_time_year_month (id);
alter table work_time_year_month add constraint FKhl16odltapeepn6bbvvtwgjuu foreign key (staff_id) references staff (id);
