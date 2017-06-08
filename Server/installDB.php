<?php
    $SQL = new mysqli("localhost", "root", "");
    $SQL->query("CREATE DATABASE Widgets");
    $SQL->select_db("Widgets");

    $SQL->query("CREATE TABLE `users` (
              `username` varchar(30) NOT NULL,
              `password` varchar(40) DEFAULT NULL,
              PRIMARY KEY (`username`)
            ) ENGINE=InnoDB DEFAULT CHARSET=latin1");

    $SQL->query("CREATE TABLE `memo` (
              `username` varchar(30) NOT NULL,
              `memoid` int(11) NOT NULL,
              `title` varchar(30) DEFAULT NULL,
              `content` varchar(120) DEFAULT NULL,
              PRIMARY KEY (`username`,`memoid`),
              CONSTRAINT `memo_users_USER_fk` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
            ) ENGINE=InnoDB DEFAULT CHARSET=latin1");

    $SQL->query("CREATE TABLE `rss` (
              `username` varchar(30) NOT NULL,
              `url` varchar(120) DEFAULT NULL,
              PRIMARY KEY (`url`,`username`),
              CONSTRAINT `rss_users_USER_fk` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
            ) ENGINE=InnoDB DEFAULT CHARSET=latin1");

    $SQL->query("CREATE TABLE `todo` (
              `username` varchar(30) NOT NULL,
              `checked` tinyint(1) DEFAULT NULL,
              `content` varchar(120) DEFAULT NULL,
              PRIMARY KEY (`username`,`content`),
              CONSTRAINT `todo_users_USER_fk` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
            ) ENGINE=InnoDB DEFAULT CHARSET=latin1");

    $SQL->close();
?>