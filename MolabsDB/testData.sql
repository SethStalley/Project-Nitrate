INSERT INTO molabsdb.users(username, password, type, date)
	VALUES('root' , CAST(SHA2('root', 512) AS BINARY), 'owner', NOW());

CALL molabsdb.insertUser('seth', CAST(SHA2('omg', 512) AS BINARY), 'user','root' , CAST(SHA2('root', 512) AS BINARY));
CALL molabsdb.insertUser('adrian', CAST(SHA2('lopez', 512) AS BINARY), 'admin','root' , CAST(SHA2('root', 512) AS BINARY));
CALL molabsdb.insertUser('josue', CAST(SHA2('caca', 512) AS BINARY), 'owner','root' , CAST(SHA2('root', 512) AS BINARY));

SELECT * FROM molabsdb.users;
SELECT * FROM molabsdb.graphs;

-- DELETE FROM molabsdb.graphs WHERE idGraph > 0;
-- DELETE FROM molabsdb.users WHERE idUser > 0;

