<?php
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

        $SQL->performQuery("CREATE TABLE `rss` (
          `username` varchar(30) NOT NULL,
          `feedid` int(11) NOT NULL AUTO_INCREMENT,
          `url` varchar(120) DEFAULT NULL,
          PRIMARY KEY (`feedid`,`username`),
          CONSTRAINT `rss___fk` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
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