<h2 align="center"><p>You have been successfully logged in.</p></h2>

<p align="center">Your name: <?php echo $_SERVER['MELLON_name'];?></p>
<p align="center">Your email: <?php echo $_SERVER['MELLON_mail'];?></p>
<p align="center">Your id: <?php echo $_SERVER['MELLON_eppn'];?></p>
<p align="center">Your groups:
<?php
for ($i = 0; ; $i++) {
  $var = 'MELLON_entitlement_' . $i;
  if (array_key_exists($var, $_SERVER) == false)
  break;
  if ($i > 0)
    print (", ");
    print($_SERVER[$var]);
  }
?>