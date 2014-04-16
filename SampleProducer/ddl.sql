drop table TAGS;
drop database sampleDB;

create database sampleDB;
use sampleDB;

create table TAGS
(
  name varchar(20) not null,
  count int,
  PRIMARY KEY (name)
);

