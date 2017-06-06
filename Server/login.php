<?php
    // include "SQLManipulator.php";
    include "dbLogin.php";

    if (!isset($_POST)) {
        die();
    } else {
        $SQL->initializeConnection();
        $query = $SQL->performQuery("SELECT * FROM users WHERE username = '" . $_POST["username"] . "' and password = '" . $_POST["password"] . "'");

        if (sizeof($SQL->iterate($query)) == 1) {
            session_start();
            $_SESSION["username"] = $_POST["username"];
            // echo "{loginStatus: 'SUCCESS'}";
            echo json_encode(array("loginStatus" => "SUCCESS"));
        } else {
            // echo "{loginStatus: 'FAILURE'}";
            echo json_encode(array("loginStatus" => "FAILURE"));
            die();
        }
    }