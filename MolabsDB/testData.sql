call molabsdb.insertUser('seth', CAST(SHA2('omg', 512) AS BINARY), 'user');
call molabsdb.insertUser('adrian', CAST(SHA2('lopez', 512) AS BINARY), 'admin');
call molabsdb.insertUser('josue', CAST(SHA2('caca', 512) AS BINARY), 'owner');

SELECT * from molabsdb.users;

-- delete from molabsdb.users where idUser > 0;