var getProcedures = require('./procedures');
var mysql = require('../config/mysql');
var pool = mysql.basePool();

/*
    Returns parameters as a string within parenthesis, based on template.
    Example: "(jaco, Costa Rica)"
*/
var parametersAsString = function(templateParameters, parametersToAdd) {
    var query = ""

    //add parameters to query string
    for (i in templateParameters) {
        if(i == 0) [
            query += "("
        ]

        query += parametersToAdd[templateParameters[i]]
        
        if(i == templateParameters.length -1) {
            query += ');'
            break
        }

        query += ','
    }

    return query
}

module.exports = {
     /*
        Calls any select defined within the "procedures" object.
     */
    genericProcedureCall : function(req, res) {
        var procedureName = req.params.procedureName
        
        getProcedures(function(procedures) {
            //check that procedures is defined

            if (procedures[procedureName]) {
                var templateParameters = procedures[procedureName].parameters
                
                var query = "CALL molabsdb."
                query += procedureName
                query += parametersAsString(templateParameters, req.query)
                console.log(query)
                pool.getConnection(function (error, connection) {
                    pool.query(query, function (err, rows) {
                        connection.release();

                        if (err) {
                            console.log (err);
                            res.send(err);
                        }
                        
                        res.send(rows);
                    })
                })
            } else {
                res.status(500).send({error: "No select procedure exsists with that name!"})
            }
        })

    },
}
