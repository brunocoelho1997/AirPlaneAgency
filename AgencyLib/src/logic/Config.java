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

--------------------------------------------------------------------
---------------------------WARNING----------------------------------

depois de ter as tabelas todas na base de dados e as quisermos transoformar em classes com o witcher do netbeans fazer:
1 - transformar tabela para classe uma a uma... para poder realizar todas as foreign keys...
2 - dps ai podemos fazer o facade de todas as entities de uma vez que correra' tudo bem

--------------------------------------------------------------------
-Plane

drop sequence if exists plane_seq;

create sequence plane_seq;

drop table if exists t_plane;

create table t_plane(
    id int primary key default nextval('plane_seq'),
    planename varchar(30) not null,
    planelimit int not null
);


--------------------------------------------------------------------
-User

drop sequence if exists user_seq;

create sequence user_seq;

drop table if exists t_user;

create table t_user(
    id int primary key default nextval('user_seq'),
    usertype int not null,
    username varchar(30) not null unique,
    password varchar(30) not null,
    clientname varchar(30),
    balance float,
    accepted boolean
);


--------------------------------------------------------------------
- Airline

drop sequence if exists airline_seq;

create sequence airline_seq;

drop table if exists t_airline;

create table t_airline(
    id int primary key default nextval('airline_seq'),
    airlinename varchar(30) not null,
    phonenumber varchar(30) not null
);

--------------------------------------------------------------------
-Place

drop sequence if exists place_seq;

create sequence place_seq;

drop table if exists t_place;

create table t_place(
    id int primary key default nextval('place_seq'),
    country varchar(30) not null,
    city varchar(30) not null,
    address varchar(150)
);

--------------------------------------------------------------------
-placefeedback

drop sequence if exists placefeedback_seq;

create sequence placefeedback_seq;

drop table if exists t_placefeedback;


create table t_placefeedback(
    id int primary key default nextval('placefeedback_seq'),
    placeid int not null,
    userid int not null,
    score int not null,

    constraint fk1 foreign key (placeid) references t_place (id),
    constraint fk2 foreign key (userid) references t_user (id)
);



--------------------------------------------------------------------
- trip

drop sequence if exists trip_seq;

create sequence trip_seq;

drop table if exists t_trip;


create table t_trip(
    id int primary key default nextval('trip_seq'),
    price float,
    done boolean,
    canceled boolean,
    datetrip int,
    fromplaceid int not null,
    toplaceid int not null,
    airlineid int not null,
    planeid int not null,

    constraint fk1 foreign key (fromplaceid) references t_place (id),
    constraint fk2 foreign key (toplaceid) references t_place (id),
    constraint fk3 foreign key (airlineid) references t_airline (id),
    constraint fk4 foreign key (planeid) references t_plane (id)

);


--------------------------------------------------------------------

- purchase

drop sequence if exists purchase_seq;

create sequence purchase_seq;

drop table if exists t_purchase;


create table t_purchase(
    id int primary key default nextval('purchase_seq'),
    done boolean,
    userid int not null,

    constraint fk foreign key (userid) references t_user (id)
);

--------------------------------------------------------------------

-seat

drop sequence if exists seat_seq;

create sequence seat_seq;

drop table if exists t_seat;


create table t_seat(
    id int primary key default nextval('seat_seq'),
    luggage varchar(30),
    auctioned boolean,
    price float,
    tripid int not null,
    purchaseid int,


    constraint fk1 foreign key (tripid) references t_trip (id),
    constraint fk2 foreign key (purchaseid) references t_purchase (id)

);

--------------------------------------------------------------------

- trip feedback
drop sequence if exists tripfeedback_seq;

create sequence tripfeedback_seq;

drop table if exists t_tripfeedback;


create table t_tripfeedback(
    id int primary key default nextval('tripfeedback_seq'),
    score int not null,
    tripid int not null,
    userid int not null,
   

    constraint fk1 foreign key (tripid) references t_trip (id),
    constraint fk2 foreign key (userid) references t_user (id)
);

--------------------------------------------------------------------

LOGS
-- Para conseguir usar Messages Beans fazer:
1 - criar Connection Factory
2 - criar Destination Resource
*/
public class Config {
    public static final int OPERATOR = 0;
    public static final int CLIENT = 1;
    
    
    public static final String MSG_NO_PERMISSION = "No permissions to invoke the method. Just an accepted operator or client has permissions.";
    public static final String MSG_NO_PERMISSION_OPERATOR = "No permissions to invoke the method. Just an accepted operator has permissions.";
    public static final String MSG_NO_PERMISSION_FEEDBACK = "No permission to change the place's feedback of other user.";
    public static final String MSG_NO_PERMISSION_LOG = "No permission to add logg: user does not exist.";
    public static final String MSG_NO_PERMISSION_CHANGE_TRIP = "No permissions to change the trip. It already has bought seats. To perform any alteration, you must cancel the trip.";
    public static final String MSG_NO_PERMISSION_MONEY = "No permissions to buy the seat/seats to the trip. The user doesn't have money.";
    public static final String MSG_NO_PERMISSION_TRIP_DONE = "No permissions to finish the purchase because one trip is already done.";
    public static final String MSG_NO_PERMISSION_PLANE_LIMIT_EXCEDED = "No permissions to finish the purchase because a plane already fullfit";

    
            
    public static final long DEFAULT_TIMER= 60;

    
}