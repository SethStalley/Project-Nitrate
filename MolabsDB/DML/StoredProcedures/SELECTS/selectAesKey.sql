DELIMITER $$
DROP PROCEDURE IF EXISTS molabsdb.selectAesKey;$$

CREATE PROCEDURE molabsdb.selectAesKey(
					pUserName VARCHAR(45), pPassword VARCHAR(45)) -- these 2 is for user validation
BEGIN

    
    START TRANSACTION;
    
    SET @type = (SELECT type
					FROM molabsdb.users
						WHERE userName = pUserName AND password = (CAST(SHA2(pPassword, 512) AS BINARY)));

	IF (@type IS NULL) THEN -- any user can perform this action
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Validación de usuario incorrecta.';
	END IF;
    

    
    
	SELECT * from molabsdb.keys;
        

    
END$$

DELIMITER ;