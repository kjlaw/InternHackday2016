var express = require('express')
  , app = express()
  , server = require('http').createServer(app);

server.listen(3000);

app.get('/', function(req, res) {
  res.send("Hello world!");
});

// Fantastic three app
app.get('/face', function(req, res) {
	var obj = {
		hello: "FANTASTIC THREE"
	}
  res.json(obj);
});

var Kairos = require('kairos-api');
var client = new Kairos('app_id', 'app_key');
 
var params = {
  image: 'http://media.kairos.com/kairos-elizabeth.jpg',
  subject_id: 'subtest1',
  gallery_name: 'gallerytest1',
  selector: 'SETPOSE'
};
 
client.enroll(params)   // return Promise 
  //  result: { 
  //    status: <http status code>, 
  //    body: <data> 
  //  } 
  .then(function(result) { ... })
  // err -> array: jsonschema validate errors 
  //        or throw Error 


console.log('hello world')
