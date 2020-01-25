SELECT b.name, b.stars, b.review_count, geo_distance( a.Longitude ,a.Latitude ,b.Longitude,b.Latitude) as Distance FROM yelp_db.business b, yelp_db.category c ,(select * from yelp_db.apartments where listing = 25 )a WHERE b.id = c.business_id AND b.city = 'Las Vegas' AND b.state = 'NV' AND  c.category = 'Restaurants' AND 200 > geo_distance( a.Longitude ,a.Latitude ,b.Longitude,b.Latitude) GROUP BY b.id, b.name, b.stars, b.review_count, geo_distance( a.Longitude ,a.Latitude ,b.Longitude,b.Latitude) HAVING b.review_count > 9;


select count(*) from rented_apartments;

select count(*) from apartments;

INSERT into rented_apartments SELECT * FROM apartments WHERE listing = 35;