var express = require('express');
var router = express.Router();
var wrapper = require('../dbQueries/procedureWrapper');

    router.post('/api/:procedureName', function(req,res) {
        wrapper.genericProcedureCall(req, res);
    })

module.exports = router;
