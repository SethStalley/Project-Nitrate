# Webservice

## Server
* User: `ubuntu`
* DNS: `ec2-54-144-112-150.compute-1.amazonaws.com`

### SSH Example
`ssh -i ~/myPrivateKey.pem ubuntu@ec2-54-144-112-150.compute-1.amazonaws.com`

## MySql
* Schema: `molabsdb`


## API
* URL: http://54.144.112.150/api/

All procedures are http POST requests. Send a json containing the parameters of the procedure to the url that is the procedures name. For example if the procedure is called `insertUser`, send your POST to `54.144.112.150/api/insertUser`.
