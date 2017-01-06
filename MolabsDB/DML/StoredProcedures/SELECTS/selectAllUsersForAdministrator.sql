DELIMITER $$
DROP PROCEDURE IF EXISTS molabsdb.selectAllUsersForAdministrator;$$
CREATE PROCEDURE molabsdb.selectAllUsersForAdministrator(pUserName VARCHAR(45), pPassword VARCHAR(45))
BEGIN

	-- retruns all public information of all users created by an administrator or owner
    SET @type = (SELECT type
					FROM molabsdb.users
						WHERE userName = pUserName AND password = (CAST(SHA2(pPassword, 512) AS BINARY)));

	IF (@type = 'user' OR @type IS NULL) THEN -- only owner and admin can perform this action
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Validación de usuario incorrecta.';
	END IF;
    
    SELECT idUser, userName, type, date,completeName, telephoneNumber, email
		FROM molabsdb.users
			WHERE (type = 'user' OR type = 'admin') AND createdBy = pUserName;
		
    
END$$

DELIMITER ;