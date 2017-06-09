<?php
    // include "Widget.php";
    include "RSSFailure.php";

    class RSS implements Widget {
        private $User;

        public function __construct(User $user) {
            $this->User = $user;
        }

        public function checkURL(string $URL) {
            return preg_match("/^(https?:\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?$/", $URL) == 1;
        }

        public function insert(string $URL, mysqli $SQL) {
            if ($this->checkURL($URL)) {
                return $SQL->query("INSERT INTO rss (username, url)
                                    VALUES ('" . $this->User->getUsername() . "', '" . $URL . "')") ? $this->returnResponse(RSSFailure::Success) : $this->returnResponse(RSSFailure::AlreadyExists);
            } else {
                return $this->returnResponse(RSSFailure::IllegalURL);
            }
        }

        public function remove(string $URL, mysqli $SQL) {
            $SQL->query("DELETE FROM rss
                                    WHERE username = '" . $this->User->getUsername() . "' and url = '" . $URL . "'");
        }

        public function get(mysqli $SQL) {
            $URLs = $SQL->query("SELECT url FROM rss WHERE username = '" . $this->User->getUsername() . "'");

            $_URLs = array();
            foreach ($URLs as $i) {
                array_push($_URLs, $i["url"]);
            }
            return json_encode(array("url" => $_URLs));
        }

        public function returnResponse($status) {
            switch ($status) {
                case RSSFailure::IllegalURL:
                    return json_encode(array("status" => "Illegal URL!"));
                case RSSFailure::AlreadyExists:
                    return json_encode(array("status" => "URL already exists!"));
                case RSSFailure::Success:
                    return json_encode(array("status" => "Success"));
                default:
                    return null;
            }
        }
    }