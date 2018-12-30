/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 *
 * @author bruno
 */

/*

SQL COMMANDS


-Plane

drop sequence if exists plane_seq;

create sequence plane_seq;

drop table if exists t_plane;

create table t_plane(
    id int primary key default nextval('plane_seq'),
    planename varchar(30) not null,
    planelimit int not null
);


-User

drop sequence if exists user_seq;

create sequence user_seq;

drop table if exists t_user;

create table t_user(
    id int primary key default nextval('user_seq'),
    usertype int not null,
    username varchar(30) not null,
    password varchar(30) not null,
    clientname varchar(30),
    balance float,
    accepted boolean
);


- Airline

drop sequence if exists airline_seq;

create sequence airline_seq;

drop table if exists t_airline;

create table t_airline(
    id int primary key default nextval('airline_seq'),
    airlinename varchar(30) not null,
    phonenumber varchar(30) not null
);

-Place

drop sequence if exists place_seq;

create sequence place_seq;

drop table if exists t_place;

create table t_place(
    id int primary key default nextval('place_seq'),
    country varchar(30) not null,
    city varchar(30) not null,
    address varchar(150) not null
);

-placefeedback

drop sequence if exists placefeedback_seq;

create sequence placefeedback_seq;

drop table if exists t_placefeedback;


create table t_placefeedback(
    id int primary key default nextval('placefeedback_seq'),
    placeid int not null,
    userid int not null,
    score int not null,

    constraint fk foreign key (placeid) references t_place (id),
    foreign key (userid) references t_user (id)
);

*/
public class Config {
    public static final int OPERATOR = 0;
    public static final int CLIENT = 1;
    
    public static final String msgNoPermissionOperator = "No permissions to invoke the method. Just an operator has permissions and accepted.";
    public static final String msgNoPermissionFeedback= "No permission to change the place's feedback of other user.";

}
