DELIMITER $$
DROP PROCEDURE IF EXISTS molabsdb.selectUserByUsername;$$
CREATE PROCEDURE molabsdb.selectUserByUsername(userToLook VARCHAR(45), 
	pUserName VARCHAR(45), pPassword VARBINARY(512))
BEGIN

	-- retruns all public information of a given user by username
    -- only returned if pUserName created userToLook
    SET @type = (SELECT type
					FROM molabsdb.users
						WHERE userName = pUserName AND password = pPassword);

	IF (@type = 'user' OR @type IS NULL) THEN -- only owner and admin can perform this action
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Validación de usuario incorrecta.';
	END IF;
    
    SELECT idUser, userName, type, date,completeName, telephoneNumber, email
		FROM molabsdb.users
			WHERE (type = 'user' OR type = 'admin') AND userName = userToLook
				AND createdBy = pUserName; -- owner cannot be selected
		
    
END$$

DELIMITER ;