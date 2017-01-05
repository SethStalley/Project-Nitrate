INSERT INTO molabsdb.users(username, password, type, date, completeName, telephoneNumber, email, createdBy)
	VALUES('root' , CAST(SHA2('root', 512) AS BINARY), 'owner', NOW(), 'Root Quiros', '6666-6666','root@gmail.com','root'); -- this first user is created by himself

CALL molabsdb.insertUser('seth', CAST(SHA2('omg', 512) AS BINARY), 'user', 'Seth Stalley', '1234-5678','seth@hotmail.com',
	'root' , CAST(SHA2('root', 512) AS BINARY));
    
CALL molabsdb.insertUser('adrian', CAST(SHA2('lopez', 512) AS BINARY), 'admin', 'Adriàn Lòpez', '8765-4321','adrian@hotmail.com',
	'root' , CAST(SHA2('root', 512) AS BINARY));
    
CALL molabsdb.insertUser('admin', CAST(SHA2('admin', 512) AS BINARY),  'admin', 'Admin Admin', '2222-2222','admin@hotmail.com',
	'root' , CAST(SHA2('root', 512) AS BINARY));
    
CALL molabsdb.insertUser('josue', CAST(SHA2('caca', 512) AS BINARY),  'user','Josue Arrieta Salas', '8327-3913','josuearrietasalas@gmail.com',
	'root' , CAST(SHA2('root', 512) AS BINARY));

CALL molabsdb.insertUser('user', CAST(SHA2('user', 512) AS BINARY),  'user','User User', '1111-1111','user@hotmail.com',
	'adrian' , CAST(SHA2('lopez', 512) AS BINARY));

SELECT * FROM molabsdb.users;
SELECT * FROM molabsdb.graphs;

-- DELETE FROM molabsdb.graphs WHERE idGraph > 0;
-- DELETE FROM molabsdb.users WHERE idUser > 0;

