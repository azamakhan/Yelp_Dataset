CREATE OR REPLACE PROCEDURE delete_listing(listing_param rented_apartments.id%TYPE) IS
BEGIN
	INSERT into rented_apartments (SELECT * FROM apartments WHERE listing = listing_param);
	DELETE from apartments WHERE listing = listing_param;
END;
/

CALL delete_listing(120);

SELECT COUNT(*) FROM apartments;

SELECT COUNT(*) FROM rented_apartments;
