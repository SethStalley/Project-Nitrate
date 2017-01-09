DELIMITER $$
DROP PROCEDURE IF EXISTS molabsdb.selectUserGraphs;$$
CREATE PROCEDURE molabsdb.selectUserGraphs(pUserNameGraph VARCHAR (45), graphType VARCHAR(45),
	pUserName VARCHAR(45), pPassword VARCHAR(45))
BEGIN

	-- receives an username and return its graphs.
    -- graphType could be null or specific to a type of graph. 
    -- if type is sent incorrectly it simply does not returns something
    SET @type = (SELECT type
					FROM molabsdb.users
						WHERE userName = pUserName AND password = (CAST(SHA2(pPassword, 512) AS BINARY)));

	IF (@type IS NULL) THEN -- anyone can perform this action
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Validación de usuario incorrecta.';
	END IF;
    
    START TRANSACTION READ ONLY;
    
    SET @idUser = (SELECT idUser FROM molabsdb.users WHERE userName = pUserNameGraph);
    
    
    SELECT type, json, date
		FROM molabsdb.graphs
			WHERE idUser = @idUser AND type = IFNULL(graphType, type);
            
	COMMIT;
		
    
END$$

DELIMITER ;