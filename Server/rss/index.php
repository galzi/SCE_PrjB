<?php
    include "../SQLManipulator.php";
    include "../dbLogin.php";
    session_start();

    if (!isset($_SESSION["username"]) or !isset($_GET["action"]) or !isset($_GET["content"])) {
        die();
    }
    // " ?
    foreach (array('||', '-', '*', '<>', '<', '>', ',', '=', '<=', '>=', '~=', '!=', '^=', '(', ')', ';', "''") as $i) { // http://forums.codeguru.com/showthread.php?350081-Invalid-characters-in-sql
        if (strpos($_GET["content"], $i) !== false) {
            echo "Illegal characters found!";
            die();
        }
    }

    $SQL = SQLManipulator::getInstance(); // session?
    switch ($_GET["action"]) {
        case "add":
            $SQL->performQuery("INSERT INTO rss (username, url)
                                    VALUES ('" . $_SESSION["username"] . "', '" . $_GET["content"] . "')");
            // json success
                   echo $SQL->getErrorMessage();
            break;
        case "del":
            $SQL->performQuery("DELETE FROM rss
                                    WHERE username = '" . $_SESSION["username"] . "' and feedid = '" . $_GET["content"] . "'");
            $SQL->performQuery("SET @var_name = 0;
                                    UPDATE rss SET ID = (@var_name := @var_name +1) WHERE username = " . $_SESSION["username"] . ";
                                    ALTER TABLE rss AUTO_INCREMENT = 1"); // https://stackoverflow.com/questions/2643371/how-to-renumber-primary-index
            break;
        case "get":
            $SQL->toJSON($SQL->performQuery("SELECT * FROM rss WHERE username = '" . $_SESSION["username"] . "'"));
            break;
        default:
    }
?>