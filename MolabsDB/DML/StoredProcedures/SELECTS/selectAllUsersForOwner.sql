DELIMITER $$
DROP PROCEDURE IF EXISTS molabsdb.selectAllUsersForOwner;$$
CREATE PROCEDURE molabsdb.selectAllUsersForOwner(pUserName VARCHAR(45), pPassword VARBINARY(512))
BEGIN

	-- retruns all public information of ALL users. created by an owner.
    SET @type = (SELECT type
					FROM molabsdb.users
						WHERE userName = pUserName AND password = pPassword);

	IF (@type != 'owner' OR @type IS NULL) THEN -- only owner can perform this action
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Validación de usuario incorrecta.';
	END IF;
    
    SELECT idUser, userName, type, date, createdBy,completeName, telephoneNumber, email
		FROM molabsdb.users
			WHERE createdBy = pUserName;-- you can only see your users
		
    
END$$

DELIMITER ;