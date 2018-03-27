<?php
// simple service in PHP returning access code and auth token from ARIA.
// 1. step call with no parameters, then it generates and returns url link with access code
// 2. step call with parameters  'code' and 'state' is set, then returns auth_token
// additionally tests the cookie access_token and refresh_token
// include unique client ID that your application (not user) is authenticated with ARIA
// you need to register your application in ARIA to obtain unique client_id secret_id
include 'clientIds.php';
//$client_id = 'abc123...';
//$client_secret = 'ABC123...';

$headerHost = $_SERVER['HTTP_HOST'];

$OAservice = 'https://www.structuralbiology.eu/ws/oauth/';

// define a unique state string for security
session_start();
if(!isset($_SESSION['OAstate'])) {
  $_SESSION['OAstate'] = rand(1000000, 9999999);
}
error_log("session state:".$_SESSION['OAstate']);

// if we have code, then retrieve token and set it to cookie
if(isset($_GET['code']) && isset($_GET['state'])){
  $code = $_GET['code'];
  $state = $_GET['state'];

  // quickly sanitize input
  if(!preg_match('/[A-Za-z0-9]+/', $code))
    exit('Authentication code invalid');
  if(!preg_match('/[A-Za-z0-9]+/', $state))
    exit('State is invalid');

  // verify the state before continuing on
  if($state != $_SESSION['OAstate'])
    exit('State is invalid: state:'.$state.' oastate:'.$_SESSION['OAstate']);

  // manually build OAuth packet - this can be replaced for an off-shelf solution
  $OApacket = array(
    'grant_type' => 'authorization_code',
    'code' => urlencode($code)
  );
//    'client_id' => urlencode($client_id),
//  'client_secret' => urlencode($client_secret),

  // start curl connection
  $ch = curl_init();
  curl_setopt( $ch, CURLOPT_URL, $OAservice.'token' );
  curl_setopt( $ch, CURLOPT_FOLLOWLOCATION, true );
  curl_setopt( $ch, CURLOPT_RETURNTRANSFER, true );
  curl_setopt( $ch, CURLOPT_AUTOREFERER, true );
  //TODO VERIFYPEER,true fails on SL7
  curl_setopt( $ch, CURLOPT_SSL_VERIFYPEER, false );
  curl_setopt( $ch, CURLOPT_SSL_VERIFYHOST, 0 );
  curl_setopt( $ch, CURLOPT_CAINFO, "cacert.pem" ) ;
  curl_setopt( $ch, CURLOPT_MAXREDIRS, 10 );
  //TODO debuging curl request/response
  curl_setopt($ch, CURLOPT_VERBOSE, true);
  // add OAuth packet and define HTTP POST
  curl_setopt( $ch, CURLOPT_POST, true );
  curl_setopt( $ch, CURLOPT_POSTFIELDS, http_build_query($OApacket) );
  curl_setopt( $ch, CURLOPT_USERPWD, $client_id .':'. $client_secret );
  error_log("sending curl request");
  // run curl and capture output
  if(!$content = curl_exec( $ch )) {
    error_log("error occured errno:".curl_errno($ch));
    error_log(curl_error($ch));
    header("Status: 404 Not Found"); //TODO return HTTP 404 not found or 403 forbidden or redirect curl error??
    echo('error:'.curl_error($ch));
    exit('Could not fetch access token');
  }
  curl_close ( $ch );

  // convert JSON into object
  if(!$authorization = json_decode($content))
    exit('JSON parsing error');
  if($authorization->access_token){
    // we will store access tokens as cookies for this demo, but you will want to store them somewhere more persistent (database)
    setcookie('access_token', $authorization->access_token, time()+$authorization->expires_in);
    // this refresh token actually has an expiry of 28 days, however for the demo it's useful to expire within a single day
    setcookie('refresh_token', $authorization->refresh_token, time()+60*60*24*1);
    $_COOKIE['access_token'] = $authorization->access_token;
    //now exit - cookie set - no other content is needed
    header("Status: 200 OK");
    header('Content-type: application/json');
    echo ($content);
  } else {
    header("Status: 404 Not Found");
    header('Content-type: application/json');
    echo ($content);
  }
  exit;
}
//otherwise create link
else {
  if (isset($client_id)) {
    header("Status: 200 OK");
    header('Content-type: application/json');
    echo '{"url":"' . $OAservice . 'authorize?client_id=' . $client_id . '&state=' . $_SESSION['OAstate'] . '&response_type=code"}';
    exit;
  } else {//
    header("HTTP/1.1 500 Internal Server Error");
    header('Content-type: application/json');
    echo '{"error":"ARIA integration not configured. Need client_id, secret_id dedicated to this website."}';
    exit;
  }
}
?>
