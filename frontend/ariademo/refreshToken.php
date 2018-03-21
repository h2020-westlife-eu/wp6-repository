<?php
include 'clientIds.php';

$OAservice = 'https://www.structuralbiology.eu/ws/oauth/';//'http://instruct-test.strubi.ox.ac.uk/update/ws/oauth/';

if(!preg_match('/[A-Za-z0-9]+/', $_COOKIE['refresh_token']))
  exit();

// verify the state before continuing on
if($state != $_SESSION['OAstate'])
  exit('{"error":"State is invalid"}');

// manually build OAuth packet - this can be replaced for an off-shelf solution
$OApacket = array(
//  'client_id' => urlencode($client_id),
//  'client_secret' => urlencode($client_secret),
  'grant_type' => 'refresh_token',
  // get your refresh token from the
  'refresh_token' => urlencode($_COOKIE['refresh_token'])
);

// start curl connection
$ch = curl_init();
curl_setopt( $ch, CURLOPT_URL, $OAservice.'token' );
curl_setopt( $ch, CURLOPT_FOLLOWLOCATION, true );
curl_setopt( $ch, CURLOPT_RETURNTRANSFER, true );
curl_setopt( $ch, CURLOPT_AUTOREFERER, true );
//TODO VERIFYPEER,true fails on SL7
curl_setopt( $ch, CURLOPT_SSL_VERIFYPEER, false );
curl_setopt( $ch, CURLOPT_SSL_VERIFYHOST, 0 );
//TODO debuging curl request/response
curl_setopt($ch, CURLOPT_VERBOSE, true);

// add OAuth packet and define HTTP POST
curl_setopt( $ch, CURLOPT_POST, true );
curl_setopt( $ch, CURLOPT_POSTFIELDS, http_build_query($OApacket) );
curl_setopt( $ch, CURLOPT_USERPWD, $client_id .':'. $client_secret );

// run curl and capture output
if(!$content = curl_exec( $ch ))
  exit('{"error":"Could not fetch access token"}');
curl_close ( $ch );

// convert JSON into object

if(!$authorization = json_decode($content))
  exit('{"error":"JSON parsing error"}');
if($authorization->access_token){
  // we will store access tokens as cookies for this demo, but you will want to store them somewhere more persistent (database)
  setcookie('access_token', $authorization->access_token, time()+$authorization->expires_in);
  $_COOKIE['access_token'] = $authorization->access_token;
  // store the new refresh token that we recieved too
  setcookie('refresh_token', $authorization->refresh_token, time()+28*24*60*60);
}
echo $content;
