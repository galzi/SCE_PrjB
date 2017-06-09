<?php
    include "RegFailure.php";

    class User {
        private $username;
        private $pass;

        public function __construct(string $username, string $pass) {
            $this->username = $username;
            $this->pass = $pass;
        }

        private function isCredentialsCorrect(mysqli $SQL) { // isExists
            return ($SQL->query("SELECT * FROM users WHERE username = '" . $this->username . "' and password = '" . $this->pass . "'"))->num_rows == 1;
            // returns false if username doesn't exist or password incorrect
        }

        public function login(mysqli $SQL) {
            if ($this->isCredentialsCorrect($SQL)) {
                return $this->returnResponse(RegFailure::Login, true);
            }
            return $this->returnResponse(RegFailure::Login, false);
        }

        private function verifyFormat() {
            if (!preg_match("/^[a-z0-9_-]{6,18}$/", $this->pass) or !preg_match("/^[a-z0-9_-]{3,16}$/", $this->username)) {
                return false;
            }
            return true;
        }

        private function isExists(mysqli $SQL) {
            return ($SQL->query("SELECT * FROM users WHERE username = '" . $this->username . "'"))->num_rows == 1;
        }

        private function createUser(mysqli $SQL) {
            $SQL->query("INSERT INTO users (username, password)
                                 VALUES ('" . $this->username . "', '" . $this->pass . "')");
        }

        public function returnResponse($status, bool $b = false) {
            switch ($status) {
                case RegFailure::IllegalFormat:
                    return json_encode(array("MSG" => "Illegal format!\nUsernames and passwords must contain only latin characters, digits and hyphens. \nUsernames has to be 3 to 16 characters long and passwords 6 to 18 characters long.", "Code" => 1));
                case RegFailure::SameUsername:
                    return json_encode(array("MSG" => "A user with the same username already exists.", "Code" => 2));
                case RegFailure::Success:
                    return json_encode(array("MSG" => "Registration completed", "Code" => 0));
                case RegFailure::Login:
                    $str = $b ? "SUCCESS" : "FAILURE";
                    return json_encode(array("loginStatus" => $str));
                default:
                    return null;
            }
            return null;
        }

        public function register(mysqli $SQL) {
            if (!$this->verifyFormat()) {
                return $this->returnResponse(RegFailure::IllegalFormat);
            } elseif ($this->isExists($SQL)) {
                return $this->returnResponse(RegFailure::SameUsername);
            } else {
                $this->createUser($SQL);
                return $this->returnResponse(RegFailure::Success);
            }
        }

        public function getUsername(): string {
            return $this->username;
        }
    }