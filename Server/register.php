<?php
    include "dbLogin.php";

    if (!isset($_POST)) {
        die();
    }

    // regex
    if (!ereg("/^[a-z0-9_-]{6,18}$/", $_POST["password"]) or !ereg("/^[a-z0-9_-]{3,16}$/", $_POST["username"])) {
        echo "Illegal format";
        die();
    }


    // does already exist
    $SQL->initializeConnection();
    $query = $SQL->performQuery("SELECT * FROM users WHERE username = '" . $_POST["username"] . "'");
    if (sizeof($SQL->iterate($query)) == 1) {
        echo "A user with the same username already exists.";
        die();
    }

    // sql
    $SQL->performQuery("INSERT INTO users (username, password)
                             VALUES ('" . $_POST["username"] . "', '" . $_POST["password"] . "')");
    echo "User successfully created.";

    // json response

    /*
    POSIX Regular Expressions
    ereg(regex, str);

    Username
    /^[a-z0-9_-]{3,16}$/
    lowercase letter (a-z), number (0-9), an underscore, or a hyphen
    at least 3 of those characters, but no more than 16

    Password
    /^[a-z0-9_-]{6,18}$/
    letters, numbers, underscores, or hyphens
    6 to 18

    URL
    /^(https?:\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?$/

    */