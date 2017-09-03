create database wtm character set utf8;
grant all privileges on wtm.* to 'wtm'@'localhost' identified by 'wtm' with grant option;
grant all privileges on wtm.* to 'wtm'@'%' identified by 'wtm' with grant option;
