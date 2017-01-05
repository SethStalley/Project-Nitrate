var mysql = require('mysql');

//Localhost config
var pool = mysql.createPool({
    host     : 'localhost',
    port     : '3306',
    user     : 'root',
    password : '25ae0cfb73e84cc0bd44184687a31642',
    database : 'molabsdb',   //Name of the database
    debug    :  false
});


pool.getConnection(function(err) {
  if(!err)
    console.log("MySQL connection was succesful.")
  else
    console.log("ERROR on MySQL connection: " + err)
});


/*
    Wrapper which allows easy calling of the database from anywhere that references this file.
    If any error results from the query or connection a json response of the error will be
    returned instead.
    
    Example:
        query('select * from test', function(rows) {
            // 'rows' will now contain all returned rows.
        });     
*/
module.exports.pool = pool;


module.exports = {
    query : function(queryString, result){
        pool.getConnection(function(error,connection){
            if(error) {
                return result(error);
            }else{
                console.log('connected as id ' + connection.threadId);
                connection.query(queryString ,function(error,rows){
                    connection.release();
                    if(error){
                        return result(error);
                    }else {
                        return result(rows);
                    }
                });
            }
        });
    },

    /**
     *  Export of the DB to use it when is necessary to call a stored procedure
     * @returns {Pool}
     *
     */
    basePool : function () {
      return pool;
    },
};
