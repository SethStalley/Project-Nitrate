DELIMITER $$
DROP PROCEDURE IF EXISTS molabsdb.validateUser;$$
CREATE PROCEDURE molabsdb.validateUser(pUserName VARCHAR(45), pPassword VARCHAR(45))
BEGIN

	-- returns the type of the user if it´s valid
    -- returns null otherwise
    
    START TRANSACTION READ ONLY;
    
    SELECT type
		FROM molabsdb.users
			WHERE userName = pUserName AND password = (CAST(SHA2(pPassword, 512) AS BINARY));
            
	COMMIT;
    
END$$

DELIMITER ;