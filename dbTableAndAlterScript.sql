-- TABLES
create table prj2schema.employee (
	employeeNr serial primary key,
	role char(64),
	lastName char(64) not null,
	firstName char(64),
	dob date not null
);

create table prj2schema.airport (
	iata varchar(3) primary key,
	name char(64),
	location char(64)
);

create table prj2schema.route (
	id serial primary key,
	estDuration time,
	startAirport varchar(5),
	endAirport varchar(5),
	foreign key (startAirport) references prj2schema.airport(iata),
	foreign key (endAirport) references prj2schema.airport(iata)
);

create table prj2schema.plane (
	id serial primary key,
	nrOfSeats int,
	totalCapacity decimal(4)
);

create table prj2schema.flight (
	id serial primary key,
	creationDate date,
	departureDate date,
	departureLocation 
	planeId int not null,
	foreign key (planeId) references prj2schema.plane(id)
);
-- 2023/05/10, new table to allow
-- multiple routes to be related to a single flight
create table prj2schema.flights_routes (
	flightId int,
	routeId int,
	primary key(flightId, routeId),
	foreign key(flightId) references prj2schema.flight(id) on delete cascade,
	foreign key(routeId) references prj2schema.route(id)
);

-- customer table 2023/05/12
create table prj2schema.customer (
	customerssn varchar(9) primary key,
	customeriban varchar(34) not null,
	firstname varchar(64) not null,
	lastname varchar(64) not null,
	dateofbirth date not null,
	phonenumber varchar(15),
	email varchar(64) not null,
	address varchar(64),
	gender char(1) default 'M' check (gender in ('M', 'F'))
);

-- passenger table 2023/05/12
create table prj2schema.passenger (
	passengerssn varchar(9) primary key,
	firstname varchar(64) not null,
	lastname varchar(64) not null,
	customerssn varchar(9),
	foreign key (customerssn) references prj2schema.customer(customerssn)
);

-- ticket table 2023/05/12
create table prj2schema.ticket (
	barcode varchar(64) primary key,
	passengerssn varchar(9),
	foreign key (passengerssn) references prj2schema.passenger(passengerssn)
);
-- booking table 2023/05/12
CREATE TABLE prj2schema.booking (
	bookingid serial4 primary key,
	flightid int not null,
	customerssn varchar(9) not null,
	numberofbookedseats int not null,
	flightclass boolean,
	extralegroom boolean,
	numberofmenus int not null,
	luggage decimal,
	discount decimal,
	foreign key (flightid) references prj2schema.flight(id),
	foreign key (customerssn) references prj2schema.customer(customerssn)
);
-- separate table to hold booking price
-- calculate it in a java method and insert it into this table
create table prj2schema.bookingprice (
	bookingid int,
	price decimal,
	foreign key (bookingid) references prj2schema.booking(bookingid)
);

-- CONSTRAINTS
delete from prj2schema.flight; -- deletes all flights; use if you get an "already exists" error
alter table prj2schema.flight add constraint unique_flights unique (departuredate, planeid); -- edited from (depDate, routeid, planeid)
-- rest self explanatory
alter table prj2schema.route add constraint unique_routes unique (startairport, endairport);

alter table prj2schema.airport add constraint unique_airport unique (iata);

alter table prj2schema.plane add constraint unique_plane unique (nrofseats, totalcapacity);

alter table prj2schema.employee add constraint unique_employee unique (lastname, firstname);

alter table prj2schema.flights_routes add constraint fk_flight_id foreign key (flightId) references prj2schema.flight(id) on delete cascade;

alter table prj2schema.flight add column departuretime time;
alter table prj2schema.flight add column arrivaldate date;
alter table prj2schema.flight add column arrivaltime time;

alter table prj2schema.route add column totalSales int;
alter table prj2schema.route add column totalFlights int;
alter table prj2schema.route add column revenue decimal;

alter table prj2schema.airport alter column name type varchar;
alter table prj2schema.airport alter column location type varchar;

alter table prj2schema.flight add column baseprice decimal;

alter table prj2schema.route add constraint different_start_and_end_airports
check (startairport != endairport);

alter table prj2schema.flight add constraint check_depDate_not_older_than_creationDate
check (departuredate >= creationDate);

alter table prj2schema.flight add constraint check_depDate_not_younger_than_arvDate
check (departureDate <= arrivalDate);

alter table prj2schema.flight add constraint check_depTime_arvTime_differ
check (departureDate != arrivalDate or (departureDate = arrivalDate and departureTime <= arrivalTime - interval '30 minutes'));

insert into prj2schema.flight values (default,'2023-05-21', '2023-05-24', 4, '11:00:00', '2023-05-24', '12:00:00', 300);
