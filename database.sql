drop table if EXISTS notify_tb;
drop table if EXISTS chatlist_tb;
drop table if EXISTS chatting_tb;
drop table if EXISTS customercare_tb;
drop table if EXISTS orders_tb;
drop table if EXISTS user_data;

create table user_data(
    user_id varchar(20) not null,
    pass varchar(100) not null,
    name char(10) not null,
    major char(15) not null,
    school_number char(20) not null,
    gender char(10),
    primary key(user_id)
);

create table orders_tb( #게시되는 게시글
    o_number int not null auto_increment, #주문게시글 넘버
    o_time varchar(20), #주문 생성시간 H:M:S
    orders varchar(20) not null, #주문자
    errand varchar(20), #수락하는 심부름꾼
    title char(15) not null, #제목
    content varchar(100), #내용
    latitude double, #위도
    longitude double, #경도
    dedatil_adress char(70), #세부주소
    price int, #가격
    progress varchar(20) default "@@Waiting", #진행상황
    foreign key(orders) references user_data(user_id), # 주문자 
    primary key(o_number)
    
);

create table customercare_tb ( #신고내용 테이블
    c_number int not null auto_increment, #신고 게시글 넘버
    o_number int, #주문 게시글 숫자
    check_content text, #체크박스에 체크되서 넘어온 신고내용
    content text, #신고내용
    reporter varchar(20), #신고자
    defendant varchar(20), #피고
    foreign key(reporter) references user_data(user_id), #신고자 아디이 참조
    foreign key(defendant) references user_data(user_id), #피고 아이디 참조
    foreign key(o_number) references orders_tb(o_number), #신고된 게시글 넘버
    primary key(c_number)
);

create table chatting_tb ( 
    increase_num int not null auto_increment,
    chatting_number int not null, #채팅방 번호
    sender_id varchar(20) not null, #발신자 이름
    c_time varchar(20) not null,
    chatting text, # 채팅내용

    primary key(increase_num)
);

create table chatlist_tb(
	increase_num int not null auto_increment,
	order_id varchar(20) not null,
    errand_id varchar(20) not null,
    
    primary key(increase_num)
);

create table notify_tb (
	notify_num int not null auto_increment,
    notify_title varchar(20) not null,
	notify_content text not null,
    notify_time varchar(20) not null,
    
    primary key(notify_num)
);

insert into user_data values ("hi","03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4","nName","tec","201721200","male");
insert into user_data values ("hello","ceaa28bba4caba687dc31b1bbe79eca3c70c33f871f1ce8f528cf9ab5cfd76dd","nHello","so","201721999","female");
insert into user_data values ("id_A","03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4","A_Name","tec","201721888","female");
insert into user_data values ("Id_B","03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4","B_Name","smart","201721777","female");
insert into user_data values ("Id_C","03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4","C_Name","music","201721666","male");

insert into orders_tb values (null,"20210416122730","hello","hi","타이틀0","물건0",22.2,22.4,"6층이용",20000,"@@Completed");
insert into orders_tb values (null,"20210416042257","hi","hello","타이틀2","completed",22.2,22.4,"6층이용",30000,"@@Completed");
insert into orders_tb values (null,"20210416210616","id_A","hi","타이틀4","난 정상이다",22.2,22.4,"74층이용",6974,"@@Ongoing");
insert into orders_tb values (null,"20210416210616","hi","id_A","타이틀4","A가수락한거",22.2,22.4,"74층이용",6974,"@@Ongoing");
insert into orders_tb values (null,"20210416210406","hello","hi","타이틀1","하이가 수락한거",22.2,22.4,"8층이용",40000,"@@Ongoing");
insert into orders_tb values (null,"20210416042257","hi","hello","타이틀2","헬로우가 수락한거",22.2,22.4,"6층이용",30000,"@@Ongoing");
insert into orders_tb values (null,"20210416042257","hi",null,"타이틀2","waiting",22.2,22.4,"6층이용",30000,"@@Waiting");
insert into orders_tb values (null,"20210416210516","hi",null,"타이틀4","난 정상이다",22.2,22.4,"74층이용",6974,"@@Waiting");
insert into orders_tb values (null,"20210416210716","hello",null,"타이틀4","난 정상이다",22.2,22.4,"74층이용",6974,"@@Waiting");

insert into notify_tb values (null,"공지 타이틀입니다.","공지 내용입니다.","20210418221403");