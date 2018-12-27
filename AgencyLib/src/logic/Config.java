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

- feedback

drop sequence if exists feedback_seq;

create sequence feedback_seq;

drop table if exists t_feedback;

create table t_feedback(
    id int primary key default nextval('feedback_seq'),
    score int not null
);

*/
public class Config {
    public static final int OPERATOR = 0;
    public static final int CLIENT = 1;
    
    public static final String msgNoPermissionOperator = "No permissions to invoke the method. Just an operator has permissions and accepted.";
}
