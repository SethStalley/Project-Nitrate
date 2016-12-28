DELIMITER $$
DROP PROCEDURE IF EXISTS molabsdb.validateUser;$$
CREATE PROCEDURE molabsdb.validateUser(pUserName VARCHAR(45), pPassword VARBINARY(512))
BEGIN

	-- returns the type of the user if it´s valid
    -- returns null otherwise
    
    SELECT type
		FROM molabsdb.users
			WHERE userName = pUserName AND password = pPassword;
    
END$$

DELIMITER ;