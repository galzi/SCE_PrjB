<?php
    include "../dbLogin.php";
    session_start();

    if (!isset($_SESSION["username"]) or !isset($_GET["action"]) or !isset($_GET["content"])) {
        if (!$_GET["action"] == "get") {
            die();
        }
    }

    // " ?
    if (isset($_GET["content"])) {
        foreach (array('||', '-', '*', /*'/',*/ '<>', '<', '>', ',', '=', '<=', '>=', '~=', '!=', '^=', '(', ')', ';') as $i) { // http://forums.codeguru.com/showthread.php?350081-Invalid-characters-in-sql
            if (strpos($_GET["content"], $i) !== false) {
                echo "Illegal characters found!";
                die();
            }
        }
    }


    $SQL = SQLManipulator::getInstance(); // session?
    switch ($_GET["action"]) {
        case "add":
            $str = "INSERT INTO rss (username, url)
                                    VALUES ('" . $_SESSION["username"] . "', '" . $_GET["content"] . "')";
            echo $str;
            $SQL->performQuery($str);

            if ($SQL->getErrorMessage() == "") {
                echo json_encode(array("status" => "Success"));
            } else {
                echo json_encode(array("status" => "Failure"));
                echo $SQL->getErrorMessage();
            }

            // json success
            break;
        case "del":
            $SQL->performQuery("DELETE FROM rss
                                    WHERE username = '" . $_SESSION["username"] . "' and url = '" . $_GET["content"] . "'");
            break;
        case "get":
            $URLlist = $SQL->performQuery("SELECT url FROM rss WHERE username = '" . $_SESSION["username"] . "'");

            $newURLlist = array();
            foreach ($URLlist as $i) {
                array_push($newURLlist, $i["url"]);
            }
            echo json_encode(array("url" => $newURLlist));
            break;
        default:
    }