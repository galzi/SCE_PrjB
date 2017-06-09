<?php
    include "route.php";
    include "checkInjection.php";
    include "User.php";

    include "Widget.php";

    include "rss/RSS.php";
    include "todo/ToDo.php";
    include "exchange/ExchangeRates.php";
    include "history/History.php";

    session_start();
    $SQL = new mysqli("localhost", "root", "", "Widgets"); // can't be serialized, so can't be saved as session variable or be sent to other objects
    $route = new Route();

    // Home route
    $route->add('/', function() {
    });

    // Other routes
    $route->add('/login', function() use ($SQL) {
        // Define User and SQL connection
        if (!isset($_POST["username"]) or !isset($_POST["password"])) {
            die();
        }

        if (!isset($_SESSION["User"]) or (isset($_SESSION["User"]) and $_SESSION["User"]->login() == $_SESSION["User"]->returnResponse(RegFailure::Login, false))) { // in case you've just registered
            $_SESSION["User"] = new User($_POST["username"], $_POST["password"]);
        }
        echo $_SESSION["User"]->login($SQL);
    });

    $route->add('/register', function() use ($SQL) {
        if (!isset($_POST["username"]) or !isset($_POST["password"])) {
            die();
        }

        $_SESSION["User"] = new User($_POST["username"], $_POST["password"]);
        echo $_SESSION["User"]->register($SQL);
        echo $_SESSION["User"]->login($SQL);
    });

    $route->add('/rss', function() use ($SQL) {
        $RSS = new RSS($_SESSION["User"]);

        if ((isset($_GET["content"])) and (checkInjection::check($_GET["content"]))) {
            echo $RSS->returnResponse(RSSFailure::IllegalChar);
            die();
        }

        if (isset($_GET["action"])) {
            if (($_GET["action"] != "get") and (!isset($_GET["content"]))) { // when "action" is "get", "content" is undefined.
                die();
            }

            switch ($_GET["action"]) {
                case "add":
                    echo $RSS->insert($_GET["content"], $SQL);
                    break;
                case "del":
                    $RSS->remove($_GET["content"], $SQL);;
                    break;
                case "get":
                    echo $RSS->get($SQL);
                    break;
            }
        } else {
            die();
        }
    });

    $route->add('/todo', function() use ($SQL) {
        $ToDo = new ToDo($_SESSION["User"]);

        if ((isset($_GET["content"])) and (checkInjection::check($_GET["content"]))) {
            echo checkInjection::returnResponse(true);
            die();
        }

        if (isset($_GET["action"])) {
            if (($_GET["action"] != "get") and (!isset($_GET["content"]))) { // when "action" is "get", "content" is undefined.
                die();
            }

            switch ($_GET["action"]) {
                case "add":
                    echo $ToDo->insert($_GET["content"], $SQL);
                    break;
                case "del":
                    $ToDo->remove($_GET["content"], $SQL);;
                    break;
                case "get":
                    echo $ToDo->get($SQL);
                    break;
                case "check":
                    $ToDo->check($_GET["content"], true, $SQL);
                    break;
                case "uncheck":
                    $ToDo->check($_GET["content"], false, $SQL);
                    break;
            }
        } else {
            die();
        }
    });

    $route->add('/exchange', function() use ($SQL) {
        $Exchange = new ExchangeRates($_SESSION["User"]);

        if ((isset($_GET["source"])) and (checkInjection::check($_GET["source"]))) {
            echo checkInjection::returnResponse(true);
            die();
        }

        if ((isset($_GET["destination"])) and (checkInjection::check($_GET["destination"]))) {
            echo checkInjection::returnResponse(true);
            die();
        }

        if (isset($_GET["action"])) {
            if (($_GET["action"] != "get") and (!isset($_GET["source"]) or !isset($_GET["destination"]))) {
                die();
            }

            switch ($_GET["action"]) {
                case "add":
                    echo $Exchange->insert($_GET["source"] . "|" . $_GET["destination"], $SQL);
                    break;
                case "del":
                    $Exchange->remove($_GET["source"] . "|" . $_GET["destination"], $SQL);
                    break;
                case "get":
                    echo $Exchange->get($SQL);
                    break;
            }
        } else {
            die();
        }
    });

    $route->add('/history', function() {
        $type = isset($_GET["type"]) ? $_GET["type"] : null;
        $month = isset($_GET["month"]) ? $_GET["month"] : null;
        $day = isset($_GET["day"]) ? $_GET["day"] : null;

        echo History::getHistory($_GET["type"], $_GET["month"], $_GET["day"]);
    });

    $route->submit();