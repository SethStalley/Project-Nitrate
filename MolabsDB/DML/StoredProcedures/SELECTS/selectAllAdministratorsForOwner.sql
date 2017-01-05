DELIMITER $$
DROP PROCEDURE IF EXISTS molabsdb.selectAllAdministratorsForOwner;$$
CREATE PROCEDURE molabsdb.selectAllAdministratorsForOwner(pUserName VARCHAR(45), pPassword VARBINARY(512))
BEGIN

	-- retruns all public information of a given administrator
    SET @type = (SELECT type
					FROM molabsdb.users
						WHERE userName = pUserName AND password = pPassword);

	IF (@type != 'owner' OR @type IS NULL) THEN -- only owner can perform this action
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Validación de usuario incorrecta.';
	END IF;
    
    SELECT idUser, userName, type, date,completeName, telephoneNumber, email, createdBy
		FROM molabsdb.users
			WHERE type = 'admin' AND createdBy = pUserName; -- you can only see your admnistratros
		
    
END$$

DELIMITER ;