DELIMITER $$
DROP PROCEDURE IF EXISTS molabsdb.insertUser;$$
CREATE PROCEDURE molabsdb.insertUser(pNewUserName VARCHAR(45), pNewPassword VARBINARY(512), pType VARCHAR(10),
					pCompleteName VARCHAR(85), pTelephoneNumber VARCHAR(20), pEmail VARCHAR(45),
					pUserName VARCHAR(45), pPassword VARCHAR(45)) -- these 2 is for user validation
BEGIN

    

    DECLARE EXIT HANDLER FOR 1062 BEGIN /*Duplicate key for userName*/
		SET @error = 'Ya existe un usuario con ese nombre de usuario. Por favor seleccionar otro.';
        RESIGNAL SET MESSAGE_TEXT = @error;
    END;
    
    START TRANSACTION;
    
    SET @type = (SELECT type
					FROM molabsdb.users
						WHERE userName = pUserName AND password = (CAST(SHA2(pPassword, 512) AS BINARY)));

	IF (@type = 'user' OR @type IS NULL) THEN -- only owner and admin can perform this action
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Validación de usuario incorrecta.';
	END IF;
    
    /* pType must only be: owner | admin | user */
     IF((pType != 'owner') AND (pType != 'admin') AND (pType != 'user')) THEN  /* User defined Errors */
		 SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Tipo de usuario ingresado incorrecto.';
	 END IF;

    /*DECLARE EXIT HANDLER FOR 1452 BEGIN Foreign Key Error
        SET @error = CONCAT('No existe una ','compañía', ' con ese identificador.');
        RESIGNAL SET MESSAGE_TEXT = @error;
    END;*/
    
    
	INSERT INTO molabsdb.users(username, password, type, date, createdBy,completeName, telephoneNumber, email, valueMin, valueMax)
		VALUES(pNewUserName, (CAST(SHA2(pNewPassword, 512) AS BINARY)), pType, NOW(), pUserName,pCompleteName,pTelephoneNumber, pEmail, -1.0, 1.0);
            
	SET @idUser = (SELECT MAX(idUser) FROM molabsdb.users);
        
	/* Now insert all posible graphs for the users. JSON in null */
	INSERT INTO molabsdb.graphs(type, json, idUser, date)
		VALUES ('CalibrationGraph', NULL, @idUser, NOW());
		
	INSERT INTO molabsdb.graphs(type, json, idUser, date)
		VALUES ('ABSvsConce', NULL, @idUser, NOW());
		
	INSERT INTO molabsdb.graphs(type, json, idUser, date)
		VALUES ('ConcenVsTime', NULL, @idUser, NOW());
            
	COMMIT;
        

    
END$$

DELIMITER ;