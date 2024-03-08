CREATE KEYSPACE IF NOT EXISTS driver_service
	WITH REPLICATION = {
		'class': 'org.apache.cassandra.locator.NetworkTopologyStrategy',
		'datacenter1': '1'
	}
	AND DURABLE_WRITES = true;





CREATE TYPE IF NOT EXISTS driver_service.score(
	start_count float,
	score_level int
);


CREATE TYPE IF NOT EXISTS  driver_service.address(
	continent text,
	iscurrent boolean,
	lat double,
	lon double,
	addat timestamp,
	postalcode text,
	street text,
	timezone text,
	town text,
	state text,
	region text,
	country text
);

CREATE TYPE IF NOT EXISTS  driver_service.human_identity(
	identity_id text,
	is_verified boolean,
	expire_at timestamp,
	issue_at timestamp,
	created_at timestamp,
	verified_at timestamp,
	identity_type text,
	docs set<text>,
	provider_id text,
	provider_formatted text,
	details text
);

CREATE TYPE IF NOT EXISTS  driver_service.contact(
	value text,
	is_verified boolean,
	is_favorite boolean,
	created_at timestamp,
	verified_at timestamp,
	contact_type text
);



CREATE TYPE  IF NOT EXISTS  driver_service.skill(
	name text,
	description text,
	proof text
);

CREATE TYPE  IF NOT EXISTS  driver_service.driver_experience(
	label text,
	description text,
	attachments set<text>
);

CREATE TYPE  IF NOT EXISTS  driver_service.certificate(
	certificatelink text,
	certifiedat timestamp
);


CREATE TYPE  IF NOT EXISTS  driver_service.pricing(
	currencycode text,
	cannegotiated boolean,
	priceperhour float,
	priceperday float,
	priceperkilometer float
);


CREATE TYPE  IF NOT EXISTS  driver_service.subscription(
	id uuid,
	isactive boolean,
	startedat timestamp,
	endat timestamp,
	subscriptiontype text
);

CREATE TYPE  IF NOT EXISTS  driver_service.work_zone(
	cities set<text>,
	country text,
	state text
);




CREATE TABLE IF NOT EXISTS driver_service.certificates (
	certificate_id uuid,
	certificatelink text,
	certifiedat timestamp,
	driver_id text,
	PRIMARY KEY(certificate_id, driver_id)
);


CREATE TABLE IF NOT EXISTS driver_service.drivers (

	address set<frozen<address>>,
	avatar text,
	firstname text,
	gender text,
	keywords set<text>,
	lastname text,
	picture text,
	driver_id text,
	about text,
	birthdate timestamp,
	score frozen<score>,
	version int,
	contacts set<frozen<contact>>,

	businesscontacts set<frozen<contact>>,
	businessaddress set<frozen<address>>,
	isentreprise boolean,
	businessname text,
	businesscode text,
	businessdescription text,
	businesslogo text,
	businesstype text,
	businesstaxationnumber text,
	businessregistrationnumber text,
	businesswebsite text,

	yearsofexperience int,
	cvlink text,
	images set<text>,
	isindependent boolean,
	skills set<frozen<skill>>,
	licences set<frozen<human_identity>>,
	experiences set<frozen<driver_experience>>,
	certificate frozen<certificate>,
	driverpricing frozen<pricing>,
	subscription frozen<subscription>,
	workzone frozen<work_zone>,
	PRIMARY KEY(driver_id)
);


