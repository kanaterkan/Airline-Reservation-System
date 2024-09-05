-- DROP SCHEMA prj2schema;

CREATE SCHEMA prj2schema AUTHORIZATION postgres;

-- DROP SEQUENCE prj2schema.booking_bookingid_seq;

CREATE SEQUENCE prj2schema.booking_bookingid_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE prj2schema.employee_employeenr_seq;

CREATE SEQUENCE prj2schema.employee_employeenr_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE prj2schema.flight_id_seq;

CREATE SEQUENCE prj2schema.flight_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE prj2schema.my_serial;

CREATE SEQUENCE prj2schema.my_serial
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE prj2schema.plane_id_seq;

CREATE SEQUENCE prj2schema.plane_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE prj2schema.route_id_seq;

CREATE SEQUENCE prj2schema.route_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;-- prj2schema.airport definition

-- Drop table

-- DROP TABLE prj2schema.airport;

CREATE TABLE prj2schema.airport (
	iata varchar(5) NOT NULL,
	"name" varchar NULL,
	"location" varchar NULL,
	CONSTRAINT airport_pkey PRIMARY KEY (iata),
	CONSTRAINT unique_airport UNIQUE (iata)
);


-- prj2schema.customer definition

-- Drop table

-- DROP TABLE prj2schema.customer;

CREATE TABLE prj2schema.customer (
	customerssn varchar(9) NOT NULL,
	customeriban varchar(34) NOT NULL,
	firstname varchar(64) NOT NULL,
	lastname varchar(64) NOT NULL,
	dateofbirth date NOT NULL,
	phonenumber varchar(15) NULL,
	email varchar(64) NOT NULL,
	address varchar(64) NULL,
	gender bpchar(1) NULL DEFAULT 'M'::bpchar,
	CONSTRAINT customer_gender_check CHECK ((gender = ANY (ARRAY['M'::bpchar, 'F'::bpchar]))),
	CONSTRAINT customer_pkey PRIMARY KEY (customerssn)
);


-- prj2schema.employee definition

-- Drop table

-- DROP TABLE prj2schema.employee;

CREATE TABLE prj2schema.employee (
	employeenr serial4 NOT NULL,
	"role" bpchar(64) NULL,
	lastname bpchar(64) NOT NULL,
	firstname bpchar(64) NULL,
	dob date NOT NULL,
	CONSTRAINT employee_pkey PRIMARY KEY (employeenr),
	CONSTRAINT unique_employee UNIQUE (lastname, firstname)
);


-- prj2schema.plane definition

-- Drop table

-- DROP TABLE prj2schema.plane;

CREATE TABLE prj2schema.plane (
	id serial4 NOT NULL,
	nrofseats int4 NULL,
	totalcapacity numeric(5) NULL,
	CONSTRAINT plane_pkey PRIMARY KEY (id),
	CONSTRAINT unique_plane UNIQUE (nrofseats, totalcapacity)
);


-- prj2schema.flight definition

-- Drop table

-- DROP TABLE prj2schema.flight;

CREATE TABLE prj2schema.flight (
	id serial4 NOT NULL,
	creationdate date NULL,
	departuredate date NULL,
	planeid int4 NOT NULL,
	departuretime time NULL,
	arrivaldate date NULL,
	arrivaltime time NULL,
	baseprice numeric NULL,
	CONSTRAINT flight_pkey PRIMARY KEY (id),
	CONSTRAINT unique_flights UNIQUE (departuredate, planeid),
	CONSTRAINT flight_planeid_fkey FOREIGN KEY (planeid) REFERENCES prj2schema.plane(id)
);


-- prj2schema.passenger definition

-- Drop table

-- DROP TABLE prj2schema.passenger;

CREATE TABLE prj2schema.passenger (
	passengerssn varchar(9) NOT NULL,
	firstname varchar(64) NOT NULL,
	lastname varchar(64) NOT NULL,
	customerssn varchar(9) NULL,
	CONSTRAINT passenger_pkey PRIMARY KEY (passengerssn),
	CONSTRAINT passenger_customerssn_fkey FOREIGN KEY (customerssn) REFERENCES prj2schema.customer(customerssn)
);


-- prj2schema.route definition

-- Drop table

-- DROP TABLE prj2schema.route;

CREATE TABLE prj2schema.route (
	id serial4 NOT NULL,
	estduration time NULL,
	startairport varchar(5) NULL,
	endairport varchar(5) NULL,
	revenue numeric NULL,
	fctsold int4 NULL,
	ectsold int4 NULL,
	CONSTRAINT route_pkey PRIMARY KEY (id),
	CONSTRAINT unique_routes UNIQUE (startairport, endairport),
	CONSTRAINT route_endairport_fkey FOREIGN KEY (endairport) REFERENCES prj2schema.airport(iata),
	CONSTRAINT route_startairport_fkey FOREIGN KEY (startairport) REFERENCES prj2schema.airport(iata)
);


-- prj2schema.ticket definition

-- Drop table

-- DROP TABLE prj2schema.ticket;

CREATE TABLE prj2schema.ticket (
	barcode varchar(64) NOT NULL,
	passengerssn varchar(9) NULL,
	CONSTRAINT ticket_pkey PRIMARY KEY (barcode),
	CONSTRAINT ticket_passengerssn_fkey FOREIGN KEY (passengerssn) REFERENCES prj2schema.passenger(passengerssn)
);


-- prj2schema.booking definition

-- Drop table

-- DROP TABLE prj2schema.booking;

CREATE TABLE prj2schema.booking (
	bookingid serial4 NOT NULL,
	flightid int4 NOT NULL,
	customerssn varchar(9) NOT NULL,
	numberofbookedseats int4 NOT NULL,
	flightclass bool NULL,
	extralegroom bool NULL,
	numberofmenus int4 NOT NULL,
	luggage numeric NULL,
	discount numeric NULL,
	CONSTRAINT booking_pkey PRIMARY KEY (bookingid),
	CONSTRAINT booking_customerssn_fkey FOREIGN KEY (customerssn) REFERENCES prj2schema.customer(customerssn),
	CONSTRAINT booking_flightid_fkey FOREIGN KEY (flightid) REFERENCES prj2schema.flight(id)
);


-- prj2schema.bookingprice definition

-- Drop table

-- DROP TABLE prj2schema.bookingprice;

CREATE TABLE prj2schema.bookingprice (
	bookingid int4 NULL,
	price numeric NULL,
	CONSTRAINT bookingprice_bookingid_fkey FOREIGN KEY (bookingid) REFERENCES prj2schema.booking(bookingid)
);


-- prj2schema.flights_routes definition

-- Drop table

-- DROP TABLE prj2schema.flights_routes;

CREATE TABLE prj2schema.flights_routes (
	flightid int4 NOT NULL,
	routeid int4 NOT NULL,
	CONSTRAINT flights_routes_pkey PRIMARY KEY (flightid, routeid),
	CONSTRAINT fk_flight_id FOREIGN KEY (flightid) REFERENCES prj2schema.flight(id) ON DELETE CASCADE,
	CONSTRAINT flights_routes_flightid_fkey FOREIGN KEY (flightid) REFERENCES prj2schema.flight(id),
	CONSTRAINT flights_routes_routeid_fkey FOREIGN KEY (routeid) REFERENCES prj2schema.route(id)
);
