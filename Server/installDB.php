<?php
    $SQL = new mysqli("localhost", "root", "");
    $SQL->query("CREATE DATABASE Widgets");
    $SQL->select_db("Widgets");

    $SQL->query("CREATE TABLE `users` (
              `username` varchar(30) NOT NULL,
              `password` varchar(40) DEFAULT NULL,
              PRIMARY KEY (`username`)
            ) ENGINE=InnoDB DEFAULT CHARSET=latin1");

    $SQL->query("CREATE TABLE `exchange` (
              `username` varchar(30) NOT NULL,
              `source` varchar(3) DEFAULT NULL,
              `destination` varchar(3) DEFAULT NULL,
              PRIMARY KEY (`username`,`source`, `destination`),
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