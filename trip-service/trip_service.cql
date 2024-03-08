-- change keyspace name
CREATE KEYSPACE trip_service
	WITH REPLICATION = {
		'class': 'org.apache.cassandra.locator.NetworkTopologyStrategy',
		'datacenter1': '1'
	}
	AND DURABLE_WRITES = true;

/*
################################################
################### TYPES ######################
################################################
*/
CREATE TYPE trip_service.cancellation_policy(
	note text,
	items map<bigint, float>
);

CREATE TYPE trip_service.comfort(
	name text,
	description text
);

CREATE TYPE trip_service.confirmation_policy(
	confirm_by_booker boolean,
	require_pooler_identity boolean,
	require_verified_pooler_identity boolean,
	require_passenger_identity boolean,
	one_ticket_per_passenger boolean,
	duration_before_confirmation bigint
);

CREATE TYPE trip_service.course_policy(
	max_departure_waiting_duration bigint,
	max_waiting_duration_on_stop bigint
);

CREATE TYPE trip_service.driver(
	about text,
	avatar text,
	docs set<text>,
	driver_id text,
	firstname text,
	gender text,
	lastname text,
	owner_id text,
	phone text,
	picture text,
	pseudo text,
	cv text,
	yearsofexperience int,
	score frozen<score>,
	version int
);

CREATE TYPE trip_service.luggage(
	box_label text,
	box_number int,
	box_quantity float
);

CREATE TYPE trip_service.payment_setting(
	currency varchar,
	payment_accounts map<varchar, text>
);


CREATE TYPE trip_service.planner_info(
	name text,
	phones set<text>,
	picture text,
	score int,
	userid text,
	version int,
	designation text,
	description text
);

CREATE TYPE trip_service.pooler_info(
	name text,
	phones set<text>,
	picture text,
	score int,
	userid text,
	version int
);

CREATE TYPE trip_service.reservation_policy(
	confirm_with_reservation boolean,
	duration_before_last_reservation bigint,
	confirmation_delay_after_reservation bigint,
	max_seat_count_per_user int,
	max_luggage_per_user float,
	max_free_luggage_weight_per_user float,
	require_planner_consent boolean
);


CREATE TYPE trip_service.score(
	start_count float,
	score_level int
);

CREATE TYPE trip_service.seat(
	place_label text,
	place_number int,
	comforts set<frozen<comfort>>
);

CREATE TYPE trip_service.stop_arrival(
	lat double,
	lon double,
	timezone text,
	pos int,
	city text,
	name text
);

CREATE TYPE trip_service.stop_location(
	lat double,
	lon double,
	timezone text,
	city text,
	name text
);

CREATE TYPE trip_service.stop_point(
	lat double,
	lon double,
	timezone text,
	city text,
	name text,
	pos int,
	code text,
	delay bigint
);

CREATE TYPE trip_service.trip_presentation(
	txt text,
	images set<text>
);

CREATE TYPE trip_service.vehicle(
	comforts set<frozen<comfort>>,
	comment text,
	created_at timestamp,
	gray_card text,
	images set<text>,
	insurance_validity_date timestamp,
	insurancenumber text,
	luggage_boxes set<frozen<luggage>>,
	mark text,
	matriculation text,
	model text,
	owner_id text,
	place_count int,
	score frozen<score>,
	seats set<frozen<seat>>,
	transport_mode text,
	vehicle_id text,
	version int
);


/*
################################################
################# FUNCTIONS ####################
################################################
*/

/*
################################################
################# AGGREGATES ###################
################################################
*/

/*
################################################
################### TABLES #####################
################################################
*/
CREATE TABLE trip_service.drafts (
	comforts set<frozen<comfort>>,
	created_at timestamp,
	draft_id uuid,
	from_location frozen<stop_location>,
	intermediate_stops list<frozen<stop_point>>,
	owner_id text,
	service_type text,
	to_location frozen<stop_location>,
	trip_prestige text,
	trip_type text,
	updated_at timestamp,
	usage_count int,
	PRIMARY KEY(draft_id, owner_id)
);


CREATE INDEX drafts_owner_id_idx ON trip_service.drafts(owner_id);


CREATE TABLE trip_service.legs (
	departure timestamp,
	direct_distance double,
	distance double,
	driver frozen<driver>,
	from_location frozen<stop_point>,
	intermediate_stop set<frozen<stop_arrival>>,
	leg_index int,
	mode text,
	plan_id uuid,
	planner_id text,
	timezone text,
	to_location frozen<stop_point>,
	total_duration bigint,
	transit_duration bigint,
	vehicle frozen<vehicle>,
	PRIMARY KEY((plan_id, leg_index), planner_id)
);


CREATE INDEX legs_plan_id_idx ON trip_service.legs(plan_id);

CREATE INDEX legs_planner_id_idx ON trip_service.legs(planner_id);


CREATE TABLE trip_service.planner_trip_plan (
	canceled_at timestamp,
	cancellation_policy frozen<cancellation_policy>,
	comforts set<frozen<comfort>>,
	confirmation_policy frozen<confirmation_policy>,
	course_policy frozen<course_policy>,
	departure timestamp,
	draft_id uuid,
	executed_at timestamp,
	from_location frozen<stop_location>,
	is_bookable boolean,
	luggage_box_quantity float,
	luggage_cost float,
	note text,
	payment_setting frozen<payment_setting>,
	plan_id uuid,
	planned_at timestamp,
	planner frozen<planner_info>,
	planner_id text,
	publish_at timestamp,
	publish_code text,
	publisher_id text,
	reservation_count int,
	reservation_policy frozen<reservation_policy>,
	resolved_at timestamp,
	seat_cost float,
	seat_count int,
	service_type text,
	shared_policy_level text,
	status text,
	subscriber_types set<text>,
	timezone text,
	to_location frozen<stop_location>,
	transport_modes set<text>,
	trip_presentation_items frozen<trip_presentation>,
	trip_prestige text,
	trip_type text,
	PRIMARY KEY(plan_id, planner_id)
);


CREATE INDEX planner_trip_plan_planner_id_idx ON trip_service.planner_trip_plan(planner_id);


CREATE TABLE trip_service.pooler_trip_plan (
	canceled_at timestamp,
	comforts set<frozen<comfort>>,
	currency text,
	departure timestamp,
	draft_id uuid,
	executed_at timestamp,
	from_location frozen<stop_location>,
	is_bookable boolean,
	luggage_box_quantity float,
	note text,
	payment_methods set<text>,
	place_count int,
	plan_id uuid,
	planned_at timestamp,
	planner_id text,
	pooler frozen<pooler_info>,
	publish_at timestamp,
	publish_code text,
	publisher_id text,
	reservation_count int,
	resolved_at timestamp,
	service_type text,
	shared_policy_level text,
	status text,
	subscriber_types set<text>,
	timezone text,
	to_location frozen<stop_location>,
	total_cost float,
	transport_modes set<text>,
	trip_prestige text,
	trip_type text,
	vehicle frozen<vehicle>,
	PRIMARY KEY(plan_id, planner_id)
);


CREATE INDEX pooler_trip_plan_planner_id_idx ON trip_service.pooler_trip_plan(planner_id);



/*
################################################
############# MATERIALIZED VIEWS ###############
################################################
*/
CREATE MATERIALIZED VIEW trip_service.drafts_owner_id_idx_index
AS SELECT
draft_id, idx_token, owner_id
FROM trip_service.drafts
WHERE owner_id IS NOT NULL
PRIMARY KEY(owner_id, idx_token, draft_id);


CREATE MATERIALIZED VIEW trip_service.legs_plan_id_idx_index
AS SELECT
idx_token, leg_index, plan_id, planner_id
FROM trip_service.legs
WHERE plan_id IS NOT NULL
PRIMARY KEY(plan_id, idx_token, leg_index, planner_id);



CREATE MATERIALIZED VIEW trip_service.legs_planner_id_idx_index
AS SELECT
idx_token, leg_index, plan_id, planner_id
FROM trip_service.legs
WHERE planner_id IS NOT NULL
PRIMARY KEY(planner_id, idx_token, plan_id, leg_index);



CREATE MATERIALIZED VIEW trip_service.planner_trip_plan_planner_id_idx_index
AS SELECT
idx_token, plan_id, planner_id
FROM trip_service.planner_trip_plan
WHERE planner_id IS NOT NULL
PRIMARY KEY(planner_id, idx_token, plan_id);



CREATE MATERIALIZED VIEW trip_service.pooler_trip_plan_planner_id_idx_index
AS SELECT
idx_token, plan_id, planner_id
FROM trip_service.pooler_trip_plan
WHERE planner_id IS NOT NULL
PRIMARY KEY(planner_id, idx_token, plan_id);



