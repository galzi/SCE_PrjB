<?php
    session_start();
    include 'route.php';
    $route = new Route();

    // Home route
    $route->add('/', function() {
    });

    // Other routes
    $route->add('/login', function() use ($User) {
        // Define User and SQL connection
        if (!isset($_POST["username"]) or !isset($_POST["password"])) {
            die();
        }
        $SQL = new mysqli("localhost", "root", "", "Widgets");
        $User = new User($SQL, $_POST["username"], $_POST["password"]);

        // TODO $obj = new Object(); $_SESSION['obj'] = serialize($obj);
        echo $User->login();
    });

    $route->add('/register', function() use ($User) {
        echo $User->register();
    });

    $route->add('/rss', function() use ($User, $SQL) {
        $RSS = new RSS($User, $SQL);

        if ((isset($_GET["content"])) and ($RSS->checkInjection($_GET["content"]))) {
            echo $RSS->returnResponse(RSSFailure::IllegalChar);
            die();
        }

        if (isset($_GET["action"])) {
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