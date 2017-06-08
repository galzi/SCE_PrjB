<?php
    // session_start();

    class RSS {
        private $User;
        private $SQL;

        public function __construct(User $user, mysqli $SQL) {
            $this->User = $user;
            $this->SQL = $SQL;
        }

        public function checkInjection(string $URL) {
            foreach (array('||', '-', '*', /*'/',*/ '<>', '<', '>', ',', '=', '<=', '>=', '~=', '!=', '^=', '(', ')', ';') as $i) { // http://forums.codeguru.com/showthread.php?350081-Invalid-characters-in-sql
                if (strpos($URL, $i) !== false) {
                    return true;
                }
            }
            return false;
        }

        public function checkURL(string $URL) {
            return preg_match("/^(https?:\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?$/", $URL) == 1;
        }

        public function insert(string $URL) {
            if ($this->checkURL($URL)) {
                return $this->SQL->query("INSERT INTO rss (username, url)
                                    VALUES ('" . $this->User->getUsername() . "', '" . $URL . "')") ? $this->returnResponse(RSSFailure::Success) : $this->returnResponse(RSSFailure::AlreadyExists);
            } else {
                return $this->returnResponse(RSSFailure::IllegalURL);
            }
        }

        public function remove(string $URL) {
            $this->SQL->query("DELETE FROM rss
                                    WHERE username = '" . $this->User->getUsername() . "' and url = '" . $URL . "'");
        }

        public function get() {
            $URLs = $this->SQL->query("SELECT url FROM rss WHERE username = '" . $this->User->getUsername() . "'");

            $_URLs = array();
            foreach ($URLs as $i) {
                array_push($_URLs, $i["url"]);
            }
            return json_encode(array("url" => $_URLs));
        }

        public function returnResponse($status) {
            switch ($status) {
                case RSSFailure::IllegalChar:
                    return json_encode(array("status" => "Illegal characters found!"));
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