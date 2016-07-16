var express = require('express'),
  app = express(),
  server = require('http').createServer(app),
  kairos = require('kairos-api'),
  bodyParser = require('body-parser');

 app.use(bodyParser.json());       // to support JSON-encoded bodies
 app.use(bodyParser.urlencoded({     // to support URL-encoded bodies
   extended: true
 }));

// Sets the directory path for the app, __dirname resolves to the directory where this script resides
app.use(express.static(__dirname));

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
// var client = new kairos('a65ca343', '6345fbfcab2872c8469f0e6c3d2f2c3f');
var client = new kairos('31227806', 'ef6aa3a422e2ea8e4782623867f07c95');

var kristen = {
  found: true,
  id: 'kristenjlaw',
  name: 'kristen',
  company: 'Google',
  email: 'kristenjlaw@gmail.com'
}

var jeffrey = {
  found: true,
  id: 'jeffreyphuang',
  name: 'jeffrey',
  company: 'Yahoo',
  email: 'jeffreyhaung@gmail.com'
}

var blake = {
  found: true,
  id: 'blakebrown1995',
  name: 'blake',
  company: 'Riviera Partners',
  email: 'blakebrown129@gmail.com'
}

var nothing = {
  found: false
}
 
// Recognize
app.post('/recognize', function(req, res) {
  console.log("recieved request");
  if(!req.body.photo) {
    // No photo sent in request
    console.log("Couldn't find photo");
    res.json(nothing);
  } else {
    // Check for a face match
    var recognize_params = {
      image: req.body.photo,
      gallery_name: 'gallery',
      threshold: 0.01
    }
    console.log("Calling kairos");
    // Call kairos API
    client.recognize(recognize_params).then(function(result) {
      var resultObj = result["body"]["images"][0]["transaction"];
      if(resultObj["status"] == 'success') {
        console.log("Match");
        if(resultObj["subject"] == 'kristen') {
          console.log("kristen");
          res.json(kristen);
        } else if(resultObj["subject"] == 'jeffrey') {
          console.log("jeffrey");
          res.json(jeffrey);
        } else {
          console.log("blake");
          res.json(blake);
        }
      } else {
        console.log("No match");
        res.json(nothing);
      }
    }).catch(function(err) {
      console.log("Kairos error, error on next line:");
      console.log(err);
      res.json(nothing)
    });
  }
});

// var enroll_params = {
//   image: 'http://blakelockbrown.com/jeffrey6.jpg',
//   subject_id: 'jeffrey',
//   gallery_name: 'gallery',
// };

// // Enroll
// client.enroll(enroll_params).then(function(result) {
//   console.log(JSON.stringify(result));
// }).catch(function(err) {
//   console.log(JSON.stringify(err));
// });
