delete from borrowlist;
delete from transactionList;
delete from scooters;
delete from users;
drop sequence upnum;
drop sequence blist; --�Ⱦ�
drop sequence translist;
create sequence upnum;
create sequence blist;
create sequence translist;
insert into users(id,pwd,name,phone) values('admin','admin1234','������','010-0000-0000');
commit;
select* from users;
select* from scooters;
select* from transactionList;
select* from borrowlist;