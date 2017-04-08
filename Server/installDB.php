<?php
    $config['displayErrorDetails'] = true;
    $config['addContentLengthHeader'] = false;

    $config['db']['host']   = "localhost";
    $config['db']['user']   = "root";
    $config['db']['pass']   = "";
    $config['db']['dbname'] = "Widgets";

    $app = new \Slim\App(["settings" => $config]);

    $container = $app->getContainer();
    $container['db'] = function ($c) {
        $db = $c['settings']['db'];
        $pdo = new PDO("mysql:host=" . $db['host'] . ";dbname=" . $db['dbname'],
            $db['user'], $db['pass']);
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        $pdo->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_ASSOC);
        return $pdo;
    };

    // $container['db']->call();

    try {
        // Get Database Object
        $db = new db();
        // Connect
        $db = $db->connect();

        $stmt = $db->query($sql);
        $posts = $stmt->fetchAll(PDO::FETCH_OBJ);
        $db = null;
        echo json_encode($posts);
    } catch (PDOException $e){
        echo '{"error": {"text":' . $e->getMessage() . '}}';
    }

    include "SQLManipulator.php";
    $SQL = SQLManipulator::getInstance('localhost', 'root', '', 'Widgets');

    if ($SQL->createDB()) {
        $SQL->performQuery("CREATE TABLE `users` (
          `username` varchar(30) NOT NULL,
          `password` varchar(40) DEFAULT NULL,
          PRIMARY KEY (`username`)
        ) ENGINE=InnoDB DEFAULT CHARSET=latin1");

        $SQL->performQuery("CREATE TABLE `memo` (
          `username` varchar(30) NOT NULL,
          `memoid` int(11) NOT NULL,
          `title` varchar(30) DEFAULT NULL,
          `content` varchar(120) DEFAULT NULL,
          PRIMARY KEY (`username`,`memoid`),
          CONSTRAINT `memo_users__fk` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
        ) ENGINE=InnoDB DEFAULT CHARSET=latin1");

        $SQL->performQuery("CREATE TABLE `picturealbum` (
          `username` varchar(30) NOT NULL,
          `imageid` int(11) NOT NULL,
          `location` varchar(60) DEFAULT NULL,
          `date` date DEFAULT NULL,
          `description` varchar(120) DEFAULT NULL,
          PRIMARY KEY (`imageid`,`username`),
          KEY `picturealbum___fk` (`username`),
          CONSTRAINT `picturealbum___fk` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
        ) ENGINE=InnoDB DEFAULT CHARSET=latin1");

        $SQL->performQuery("CREATE TABLE `todo` (
          `username` varchar(30) NOT NULL,
          `bulletid` int(11) NOT NULL,
          `checked` tinyint(1) DEFAULT NULL,
          `content` varchar(120) DEFAULT NULL,
          PRIMARY KEY (`username`,`bulletid`),
          CONSTRAINT `todo_users_USER_fk` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
        ) ENGINE=InnoDB DEFAULT CHARSET=latin1");
    }

    $SQL->closeConnection();

    // var_dump($SQL->toJSON($SQL->performQuery("SELECT * FROM users"))); // testing json, to activate, first disable closeConnection statement which occures before this line
?>