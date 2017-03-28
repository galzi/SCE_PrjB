<?php
    include "SQLManipulator.php";
    $SQL = SQLManipulator::getInstance('localhost', 'root', '', 'Widgets');

    $SQL->createDB();

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

    $SQL->performQuery("CREATE TABLE `users` (
      `username` varchar(30) NOT NULL,
      `password` varchar(40) DEFAULT NULL,
      PRIMARY KEY (`username`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1");
?>