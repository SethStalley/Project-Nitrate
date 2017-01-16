-- call molabsdb.selectAllUsersForOwner('root' , CAST(SHA2('root', 512) AS BINARY));
-- call molabsdb.selectAllAdministratorsForOwner('root' , CAST(SHA2('root', 512) AS BINARY));
call molabsdb.selectAllUsersForAdministrator('root' , 'root');
call molabsdb.validateUser('root' , 'root');
-- call molabsdb.selectUserByUsername('adrian','root' , CAST(SHA2('root', 512) AS BINARY));


/*call molabsdb.updateGraphForUser('ConcenVsTime','Jcaca','admin' , 'admin');
call molabsdb.selectUserGraphs('admin', 'ConcenVsTime','admin' , 'admin');
SELECT * from molabsdb.graphs;*/

/*START TRANSACTION;
SELECT * from molabsdb.graphs;
call molabsdb.deleteUserByUsername('josue','root' , CAST(SHA2('root', 512) AS BINARY));
SELECT * from molabsdb.graphs;
SELECT * FROM molabsdb.users
ROLLBACK;*/
	
-- UPDATE USERS
/*START TRANSACTION;

SELECT * FROM molabsdb.users;
CALL molabsdb.updateUser('adrian','userr', CAST(SHA2('user', 512) AS BINARY),  'admin','Userr User', '1111-1112','userr@hotmail.com',
	'adrian' , CAST(SHA2('lopez', 512) AS BINARY));
SELECT * FROM molabsdb.users; 
 
ROLLBACK;*/

-- GRAPHS

-- CALL molabsdb.selectActiveStations('admin','admin',5);

CALL molabsdb.updateAlertValues(-2.6 , 5.8,'user','user');
CALL molabsdb.selectAlertValues('admin','admin');

SELECT * FROM molabsdb.users;


