DELIMITER $$
DROP PROCEDURE IF EXISTS molabsdb.insertUser;$$
CREATE PROCEDURE molabsdb.insertUser(pUserName VARCHAR(45), pPassword VARBINARY(512), pType VARCHAR(10))
BEGIN

    DECLARE EXIT HANDLER FOR 1062 BEGIN /*Duplicate key for userName*/
        
		SET @error = 'Ya existe un usuario con ese nombre de usuario. Por favor seleccionar otro.';

        RESIGNAL SET MESSAGE_TEXT = @error;
    END;
    
    /* pType must only be: owner | admin | user */
    
     IF((pType != 'owner') AND (pType != 'admin') AND (pType != 'user')) THEN  /* User defined Errors */
		 SIGNAL SQLSTATE '45000'
		 SET MESSAGE_TEXT = 'Tipo de usuario ingresado incorrecto.';
	 END IF;

    /*DECLARE EXIT HANDLER FOR 1452 BEGIN Foreign Key Error
        SET @error = CONCAT('No existe una ','compañía', ' con ese identificador.');
        RESIGNAL SET MESSAGE_TEXT = @error;
    END;*/
    
    INSERT INTO molabsdb.users(username, password, type, date)
		VALUES(pUserName, pPassword, pType, NOW());
        

    
END$$

DELIMITER ;