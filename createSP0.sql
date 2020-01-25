-- stored procedure sample for hw5
-- call from SqlPlus as follows (drop comment markers --):
-- var eid number;  -- bind variable to receive out param
-- call assign_pilot(10, 1000, :eid);
-- print eid;   -- returned value
-- to call from Java, see FindPilotSP0.java
-- Note that we allow any exception to throw up to the caller, so don't
-- code exception handling here
create or replace procedure assign_pilot (param_flno int, param_distance int, param_eid out int) as 
begin 
	-- do something with db to show the connection is still working
	select count(*) into param_eid from flights;
	-- for use when calling this SP from SqlPlus--
	dbms_output.put_line('hi from SP assign_pilot, param_flno = '||param_flno);
end;
/

