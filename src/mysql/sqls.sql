SET GLOBAL log_bin_trust_function_creators = 1;

drop schema if exists `mirae_db`;
create schema if not exists `mirae_db`;

use `mirae_db`;

drop table if exists `professor_tbl`;
create table if not exists `professor_tbl` (
    `id` varchar(15) unique not null,
    `name` varchar(10) not null,
    `phone` varchar(11) null,
    constraint `pk_id` primary key(`id`)
) engine=innodb default charset=utf8;

drop table if exists `course_tbl`;
create table if not exists `course_tbl` (
	`id` bigint auto_increment,
    `professor_id` varchar(15) unique not null,
    `course_name` varchar(30) not null,
    `date` date not null,
    constraint `pk_id` primary key(`id`),
    constraint `fk_professor_id` foreign key(`professor_id`) references `professor_tbl`(`id`) on delete restrict on update cascade
) engine=innodb default charset=utf8;

drop table if exists `student_tbl`;
CREATE TABLE if not exists `student_tbl` (
  `id` varchar(10) NOT NULL,
  `name` varchar(10) NOT NULL,
  `course_id` bigint not null,
  `birthdate` varchar(10) NOT NULL,
  `gender` varchar(1) NOT NULL,
  `email` varchar(40) NOT NULL,
  `phone` varchar(20) NOT NULL unique,
  `c` int NOT NULL,
  `java` int NOT NULL,
  `android` int NOT NULL,
  `web` int not null,
  `total` int null,
  `avg` double null,
  `grade` varchar(1) null,
  constraint `pk_id` PRIMARY KEY (`id`),
  constraint `fk_course_id` foreign key(`course_id`) references `course_tbl`(`id`) on delete cascade on update cascade
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop function if exists `fun_calcTotal`;
delimiter //
create function `fun_calcTotal`(_c int, _java int, _android int, _web int) returns int
begin
	return _c + _java + _android + _web;
end //
delimiter ;

drop function if exists `fun_calcGrade`;
delimiter //
create function `fun_calcGrade`(_avg double) returns varchar(1)
begin
	declare grade varchar(1);
	if (_avg >= 90.0) then set grade = 'A';
    elseif (_avg >= 80.0) then set grade = 'B';
    elseif (_avg >= 70.0) then set grade = 'C';
    elseif (_avg >= 60.0) then set grade = 'D';
    else set grade = 'F';
    end if;
	return grade;
end //
delimiter ;


drop procedure if exists `proc_insert_then_calc`;
delimiter //
create procedure `proc_insert_then_calc`(in _id varchar(10), in _name varchar(10), in _course_id int, in _birthdate varchar(10), 
								in _gender varchar(1), in _email varchar(40), in _phone varchar(20), 
								in _c int, in _java int, in _android int, in _web int)
begin
	declare total int;
    declare avg double;
    declare grade varchar(1);
    
    set total = `fun_calcTotal`(_c, _java, _android, _web);
    set avg = total / 4;
	set grade = `fun_calcGrade`(avg);

	insert into `student_tbl` values(_id, _name, _course_id, _birthdate, _gender, _email, _phone, _c, _java, _android, _web, total, avg, grade);
    select * from `student_tbl` where `id` = _id;
end //
delimiter ;

drop procedure if exists `proc_update_then_calc`;
delimiter //
create procedure `proc_update_then_calc`(in _id varchar(10), in _name varchar(10), in _course_id int, in _birthdate varchar(10), 
								in _gender varchar(1), in _email varchar(40), in _phone varchar(20), 
								in _c int, in _java int, in _android int, in _web int)
begin
	declare total int;
    declare avg double;
    declare grade varchar(1);
    
    set total = `fun_calcTotal`(_c, _java, _android, _web);
    set avg = total / 4;
	set grade = `fun_calcGrade`(avg);

	update `student_tbl` set `name` = _name, `course_id` = _course_id, `birthdate` = _birthdate, `gender` = _gender, `email` = _email, `phone` = _phone, 
							`c` = _c, `java` = _java, `android` = _android, `web` = _web, `total` = total, `avg` = avg, `grade` = grade 
                            where `id` = _id;
	select * from `student_tbl` where `id` = _id;
end //
delimiter ;

insert into `professor_tbl` values('kdj1234', '김동진', '01011112222');
insert into `professor_tbl` values('ljs11', '이종선', '01033334444');
insert into `professor_tbl` values('ly56', '이연', '01055556666');

insert into `course_tbl` values(null, 'kdj1234', '안드로이드웹앱개발자양성과정', curdate());
insert into `course_tbl` values(null, 'ljs11', 'C개발자양성과정', curdate());
insert into `course_tbl` values(null, 'ly56', 'UX개발자양성과정', curdate());




call `proc_insert_then_calc`('freean2468', '송훈일', 1, '19920329', '남', 'freean2468@gmail.com', '01079978395', 100, 100, 100, 100);
call `proc_insert_then_calc`('test1', '홍길동', 2, '19950329', '여', 'fran2468@gmail.com', '01079732945', 80, 10, 20, 80);
call `proc_insert_then_calc`('jdj', '장발산', 3, '19980329', '남', 'freean8@gmail.com', '01067973095', 60, 60, 60, 30);

call `proc_update_then_calc`('freean2468', '송훈일', 1, '19920329', '남', 'freean2468@gmail.com', '01079978395', 100, 100, 100, 30);

select * from `professor_tbl`;
select * from `course_tbl`;
select * from `student_tbl`;

