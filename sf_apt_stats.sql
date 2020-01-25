create or replace function apartment_stats(listing_param number, category_param varchar2)
return varchar2
IS  
count_var varchar2(20):=0; 
BEGIN  
select count(*) into count_var  
from (select X.stars,X.review_count,X.name,A.listing,A.address,geo_distance(A.Longitude,A.Latitude,X.Longitude,X.Latitude) as Dis
	from yelp_db.apartments A,(select * 
		from yelp_db.business B,yelp_db.category C 
		where B.city='Las Vegas' and state='NV' and B.id=C.business_id and C.category=apartment_stats.category_param and B.review_count>=10)X  
	where A.listing=apartment_stats.listing_param)Z  
where Z.Dis<=200;
return (count_var);
END;  
/

