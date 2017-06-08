<?php
    session_start();
    include 'route.php';
    $route = new Route();

    // Home route
    $route->add('/', function() {
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

    $route->submit();