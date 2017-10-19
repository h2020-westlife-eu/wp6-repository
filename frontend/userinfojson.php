<?php
        $groups = array();
        for ($i = 0; ; $i++) {
                $var = 'MELLON_entitlement_' . $i;
                if (array_key_exists($var, $_SERVER) == false)
                        break;
                $groups[] = $_SERVER[$var];
        }
        $attributes = array("name" => $_SERVER['MELLON_name'], "mail" => $_SERVER['MELLON_mail'], "eppn" => $_SERVER['MELLON_eppn'], "groups" => $groups);
        header('Content-Type: application/json');
        $JSON = json_encode($attributes);
        echo $JSON;
?>
