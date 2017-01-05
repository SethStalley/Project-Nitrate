DELIMITER $$
DROP PROCEDURE IF EXISTS molabsdb.updateUser;$$
-- all fields could be null except for pUserNameToUpdate.
CREATE PROCEDURE molabsdb.updateUser(pUserNameToUpdate VARCHAR (45), pNewUserName VARCHAR(45), pNewPassword VARBINARY(512), pType VARCHAR(10),
					pCompleteName VARCHAR(85), pTelephoneNumber VARCHAR(20), pEmail VARCHAR(45),
					pUserName VARCHAR(45), pPassword VARBINARY(512)) -- these 2 is for user validation
BEGIN

    

    DECLARE EXIT HANDLER FOR 1062 BEGIN /*Duplicate key for userName*/
		SET @error = 'Ya existe un usuario con ese nombre de usuario. Por favor seleccionar otro.';
        RESIGNAL SET MESSAGE_TEXT = @error;
    END;
    
    START TRANSACTION;
    
    SET @type = (SELECT type
					FROM molabsdb.users
						WHERE userName = pUserName AND password = pPassword);

	IF (@type = 'user' OR @type IS NULL) THEN -- only owner and admin can perform this action
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Validación de usuario incorrecta.';
	END IF;
    
    /* pType must only be: owner | admin | user */
     IF((pType != 'owner') AND (pType != 'admin') AND (pType != 'user')) THEN  /* User defined Errors */
		 SIGNAL SQLSTATE '45000'
		 SET MESSAGE_TEXT = 'Tipo de usuario ingresado incorrecto.';
	 END IF;

    
    
	UPDATE molabsdb.users
		SET userName = IFNULL(pNewUserName, userName),
			password = IFNULL(pNewPassword, password),
            type = IFNULL(pType, type),
            completeName = IFNULL(pCompleteName, completeName),
            telephoneNumber = IFNULL(pTelephoneNumber, telephoneNumber),
            userName = IFNULL(pNewUserName, userName),
            email = IFNULL(pEmail, email)
				WHERE username = pUserNameToUpdate 
					  AND (createdBy = pUserName OR pUserNameToUpdate = pUsername); -- only if you created this user you can modify it, or is it yourself :)
        

    
END$$

DELIMITER ;