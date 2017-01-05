var getProcedures = require('mysql-procedures-2-json')

var config = {
    host     : 'localhost',
    port     : '3306',
    user     : 'root',
    password : '25ae0cfb73e84cc0bd44184687a31642',
    database : 'molabsdb',
}

var data

getProcedures(config, function(result) { 
    data = result
})


module.exports = function(result){
    if(data){
        result(data)
    } else {
        data = result
    }
}
