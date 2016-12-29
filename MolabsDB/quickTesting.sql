/*call molabsdb.selectAllUsers('root' , CAST(SHA2('root', 512) AS BINARY));
call molabsdb.selectAllAdministrators('root' , CAST(SHA2('root', 512) AS BINARY));
call molabsdb.selectAllUsersForAdministrator('root' , CAST(SHA2('root', 512) AS BINARY));
call molabsdb.selectUserByUsername('adrian','root' , CAST(SHA2('root', 512) AS BINARY));*/


call molabsdb.updateGraphForUser('ABSvsConce','Jcaca','admin' , CAST(SHA2('admin', 512) AS BINARY));
call molabsdb.selectUserGraphs('admin', 'ABSvsConce','root' , CAST(SHA2('root', 512) AS BINARY));
SELECT * from molabsdb.graphs

/*call molabsdb.selectAllUsers('root' , CAST(SHA2('root', 512) AS BINARY));
call molabsdb.deleteUserByUsername('josue','seth' , CAST(SHA2('omg', 512) AS BINARY));
call molabsdb.selectAllUsers('root' , CAST(SHA2('root', 512) AS BINARY));
SELECT * from molabsdb.graphs*/