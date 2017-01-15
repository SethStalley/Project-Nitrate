DELIMITER $$
DROP PROCEDURE IF EXISTS molabsdb.selectActiveStations;$$
CREATE PROCEDURE molabsdb.selectActiveStations(pUserName VARCHAR(45), pPassword VARCHAR(45), pDays INT)
BEGIN

	-- receives an username and password to return all active stations
    -- for users only returns stations of admins or owners that create them
    -- for admin or owners return every active station
    -- pDays could be Null. It says how many days back from todays it´s the range considered as 'active'
    SET @type = (SELECT type
					FROM molabsdb.users
						WHERE userName = pUserName AND password = (CAST(SHA2(pPassword, 512) AS BINARY)));

	IF (@type IS NULL) THEN -- anyone can perform this action
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Validación de usuario incorrecta.';
	END IF;
    
    
    
    START TRANSACTION READ ONLY;
    SET @createdBy = null;
    
    IF (@type = 'user') THEN -- for users
		SET @createdBy = (SELECT createdBy FROM molabsdb.users WHERE username = pUserName); -- get
    END IF;
    
    
	SELECT u.username, u.completeName, u.telephoneNumber, u.email, CONVERT_TZ(g.date, @@session.time_zone,'-6:00') as date -- date time for CR
		FROM molabsdb.users u JOIN molabsdb.graphs g ON u.idUser = g.idUser
			WHERE g.date > (NOW() - INTERVAL IFNULL(pDays , 3) DAY) -- if pDays is null, set dafult to 3 days
				  AND u.userName = IFNULL(@createdBy, u.username)-- for users, admin and owners ignore this clasue
				  AND u.type != 'user'-- ignore user
                  AND g.type = 'ConcenVsTime' -- only check for this graph
					ORDER by date DESC;
    
            
	COMMIT;
		
    
END$$

DELIMITER ;