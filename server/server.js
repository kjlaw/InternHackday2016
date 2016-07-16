var express = require('express'),
  app = express(),
  server = require('http').createServer(app),
  kairos = require('kairos-api'),
  bodyParser = require('body-parser');

 app.use(bodyParser.json());       // to support JSON-encoded bodies
 app.use(bodyParser.urlencoded({     // to support URL-encoded bodies
   extended: true
 })); 

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

// Facial recognition
var client = new kairos('a65ca343', '6345fbfcab2872c8469f0e6c3d2f2c3f');

var kristen = {
  found: true,
  name: 'kristen',
  company: 'Google',
  email: 'kristenjlaw@gmail.com'
}

var jeffrey = {
  found: true,
  name: 'jeffrey',
  company: 'Yahoo',
  email: 'jeffreyhaung@gmail.com'
}

var blake = {
  found: true,
  name: 'blake',
  company: 'Riviera Partners',
  email: 'blakebrown129@gmail.com'
}

var nothing = {
  found: false
}
 
// Recognize
app.post('/recognize', function(req, res) {
  if(!req.body.photo) {
    // No photo sent in request
    res.json(nothing);
  } else {
    // Check for a face match
    var recognize_params = {
      image: req.body.photo,
      gallery_name: 'gallery2',
      threshold: 0.01
    }
    // Call kairos API
    client.recognize(recognize_params).then(function(result) {
      var resultObj = result["body"]["images"][0]["transaction"];
      if(resultObj["status"] == 'success') {
        if(resultObj["subject"] == 'kristen') {
          res.json(kristen);
        } else if(resultObj["subject"] == 'jeffrey') {
          res.json(jeffrey);
        } else {
          res.json(blake);
        }
      } else {
        res.json(nothing);
      }
    }).catch(function(err) {
      res.json(nothing)
    });
  }
});

// var enroll_params = {
//   image: 'http://blakelockbrown.com/kristen4.jpg',
//   subject_id: 'kristen',
//   gallery_name: 'gallery2',
// };

// Enroll
// client.enroll(enroll_params).then(function(result) {
//   console.log(JSON.stringify(result));
// }).catch(function(err) {
//   console.log(JSON.stringify(err));
// });
