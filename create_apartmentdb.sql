CREATE TABLE apartments as (SELECT * FROM yelp_db.apartments);

ALTER TABLE apartments ADD Constraint listing_pk PRIMARY KEY (listing);

CREATE TABLE rented_apartments as (SELECT listing as id, neighborhood, address, city, state, postal_code, latitude, longitude FROM apartments WHERE 1=0);

ALTER TABLE rented_apartments ADD Constraint id_pk PRIMARY KEY (id);
