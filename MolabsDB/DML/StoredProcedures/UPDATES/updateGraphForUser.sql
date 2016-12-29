DELIMITER $$
DROP PROCEDURE IF EXISTS molabsdb.updateGraphForUser;$$
CREATE PROCEDURE molabsdb.updateGraphForUser(graphType VARCHAR(45), newJson VARCHAR(1000),
	pUserName VARCHAR(45), pPassword VARBINARY(512))
BEGIN

	-- receives an pUserName to update it´s graph
    -- if graphType is sent incorrectly, it simply does nothing.action
    
    SET @type = (SELECT type
					FROM molabsdb.users
						WHERE userName = pUserName AND password = pPassword);

	IF (@type = 'user' OR @type IS NULL) THEN -- only owner and admin can perform this action
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Validación de usuario incorrecta.';
	END IF;
    
    START TRANSACTION;
    
    SET @idUser = (SELECT idUser FROM molabsdb.users WHERE userName = pUserName);
    
    
    UPDATE molabsdb.graphs
		SET json = newJson , date = NOW()
			WHERE idUser = @idUser AND type = graphType;
            
	COMMIT;
		
    
END$$

DELIMITER ;