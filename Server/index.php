<?php
    include "route.php";
    include "User.php";
    include "rss/index.php";
    include "todo/index.php";
    session_start();

    $route = new Route();

    // Home route
    $route->add('/', function() {
        echo "Hi";
    });

    // Other routes
    $route->add('/login', function() {
        // Define User and SQL connection
        if (!isset($_POST["username"]) or !isset($_POST["password"])) {
            die();
        }

        if ((!isset($_SESSION["SQL"]) or !isset($_SESSION["User"])) or (isset($_SESSION["User"]) and $_SESSION["User"]->login() == $_SESSION["User"]->returnResponse(RegFailure::Login, false))) { // in case you've just registered
            $_SESSION["SQL"] = new mysqli("localhost", "root", "", "Widgets");
            $_SESSION["User"] = new User($_SESSION["SQL"], $_POST["username"], $_POST["password"]);
        }

        echo $_SESSION["User"]->login();
    });

    $route->add('/register', function() {
        if (!isset($_POST["username"]) or !isset($_POST["password"])) {
            die();
        }

        $_SESSION["SQL"] = new mysqli("localhost", "root", "", "Widgets");
        $_SESSION["User"] = new User($_SESSION["SQL"], $_POST["username"], $_POST["password"]);
        echo $_SESSION["User"]->register();
        echo $_SESSION["User"]->login();
    });

    $route->add('/rss', function() {
        $RSS = new RSS($_SESSION["User"], $_SESSION["SQL"]);

        if ((isset($_GET["content"])) and ($RSS->checkInjection($_GET["content"]))) {
            echo $RSS->returnResponse(RSSFailure::IllegalChar);
            die();
        }

        if (isset($_GET["action"])) {
            if (($_GET["action"] != "get") and (!isset($_GET["content"]))) { // when "action" is "get", "content" is undefined.
                die();
            }

            switch ($_GET["action"]) {
                case "add":
                    echo $RSS->insert($_GET["content"]);
                    break;
                case "del":
                    $RSS->remove($_GET["content"]);;
                    break;
                case "get":
                    echo $RSS->get();
                    break;
            }
        } else {
            die();
        }
    });

    $route->add('/todo', function() {
        $ToDo = new ToDo($_SESSION["User"], $_SESSION["SQL"]);

        if ((isset($_GET["content"])) and ($ToDo->checkInjection($_GET["content"]))) {
            echo $ToDo->returnResponse(ToDoFailure::IllegalChar);
            die();
        }

        if (isset($_GET["action"])) {
            if (($_GET["action"] != "get") and (!isset($_GET["content"]))) { // when "action" is "get", "content" is undefined.
                die();
            }

            switch ($_GET["action"]) {
                case "add":
                    echo $ToDo->insert($_GET["content"]);
                    break;
                case "del":
                    $ToDo->remove($_GET["content"]);;
                    break;
                case "get":
                    echo $ToDo->get();
                    break;
                case "check":
                    $ToDo->check($_GET["content"], true);
                    break;
                case "uncheck":
                    $ToDo->check($_GET["content"], false);
                    break;
            }
        } else {
            die();
        }
    });

    $route->submit();