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
    planelimit int not null default 50
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

*/
public class Config {
    public static final int OPERATOR = 0;
    public static final int CLIENT = 1;
    
    public static final String msgNoPermissionOperator = "No permissions to invoke the method. Just an operator has permissions.";
}
