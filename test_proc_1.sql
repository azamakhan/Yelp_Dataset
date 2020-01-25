create or replace procedure test_proc is
begin
	dbms_output.put_line('Testing procedure');
end;
/


INSERT into rented_apartments SELECT * FROM apartments WHERE listing = 33;


select apartment_stats(25, 'Restaurants') from dual;