var express = require('express');
var router = express.Router();
var wrapper = require('../dbQueries/procedureWrapper');

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Molabs' });
});

    router.post('/api/:procedureName', function(req,res) {
        wrapper.genericProcedureCall(req, res);
    })

module.exports = router;
