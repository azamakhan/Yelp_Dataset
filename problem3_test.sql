select sdo_geom.sdo_distance (sdo_geometry (2001, 4326, null, sdo_elem_info_array(1, 1, 1), sdo_ordinate_array(151.20208, -33.883741)),
							  sdo_geometry (2001, 4326, null, sdo_elem_info_array(1, 1, 1), sdo_ordinate_array(200.195986, -33.87266)),1,'unit=M') 
							  distance_m from yelp_db.business b, yelp_db.category c
							  GROUP BY b.id, b.name, b.stars, b.review_count
							  HAVING b.review_count > 9;
							  

select geo_distance(151.20208, -33.883741, 151.195986, -33.87266) from dual;							  