DELETE FROM molabsdb.graphs WHERE idGraph > 0;
DELETE FROM molabsdb.users WHERE idUser > 0;

INSERT INTO molabsdb.users(username, password, type, date, completeName, telephoneNumber, email, createdBy)
	VALUES('root' , CAST(SHA2('root', 512) AS BINARY), 'owner', NOW(), 'Root Quiros', '6666-6666','root@gmail.com', 'root');
    
INSERT INTO molabsdb.users(username, password, type, date, completeName, telephoneNumber, email, createdBy)
	VALUES('Lauranha' , CAST(SHA2('epsilon', 512) AS BINARY), 'owner', NOW(), 'Laura Hernández', '','', 'Lauranha');

CALL molabsdb.insertUser('seth', 'omg', 'user', 'Seth Stalley', '1234-5678','seth@hotmail.com',
	'root' , 'root');
    
CALL molabsdb.insertUser('adrian', 'lopez', 'admin', 'Adriàn Lòpez', '8765-4321','adrian@hotmail.com',
	'root' ,'root');
    
CALL molabsdb.insertUser('admin', 'admin',  'admin', 'Admin Admin', '2222-2222','admin@hotmail.com',
	'root' , 'root');
    
CALL molabsdb.insertUser('josue', 'josue',  'user','Josue Arrieta Salas', '8327-3913','josuearrietasalas@gmail.com',
	'root' , 'root');

CALL molabsdb.insertUser('user', 'user',  'user','User User', '1111-1111','user@hotmail.com',
	'adrian' , 'lopez');

SELECT * FROM molabsdb.users order by date;
SELECT * FROM molabsdb.graphs;