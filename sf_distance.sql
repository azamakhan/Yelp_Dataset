create or replace function geo_distance(long1 in binary_double, lat1 in binary_double, long2 in binary_double, lat2 in binary_double)
return binary_double is
distance_var binary_double;

begin

select sdo_geom.sdo_distance (sdo_geometry (2001, 4326, null, sdo_elem_info_array(1, 1, 1), sdo_ordinate_array(long1, lat1)),
							  sdo_geometry (2001, 4326, null, sdo_elem_info_array(1, 1, 1), sdo_ordinate_array(long2, lat2)),1,'unit=M') 
							  into distance_var from dual;
							  
return distance_var;
end;
/


select geo_distance(151.20208, -33.883741, 151.195986, -33.87266) from dual;


select new_table.name, new_table.stars, new_table.review_count, ST_Distance_Sphere( point(a.longitude, a.latitude), point(new_table.longitude, new_table.latitude)) as Distance from yelp_db.apartments a,(select * from yelp_db.business b, yelp_db.category c where c.business_id=b.id and c.category='Restaurants'and  b.city='Las Vegas' and state='NV' )new_table  where  a.listing = '" +user_input_3+ "' and new_table.review_count >10 and ST_Distance_Sphere( point(a.longitude, a.latitude), point(new_table.longitude, new_table.latitude))<200;

