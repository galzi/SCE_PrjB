<?php
    class login {
        private $SQL;
        private $username;
        private $pass;

        public function __construct(mysqli $SQL, string $username, string $pass) {
            $this->SQL = $SQL;
            $this->username = $username;
            $this->pass = $pass;
        }

        public function isCredentialsCorrect() { // isExists
            return ($this->SQL->query("SELECT * FROM users WHERE username = '" . $this->username . "' and password = '" . $this->pass . "'"))->num_rows == 1;
            // returns false if username doesn't exist or password incorrect
        }

        public function initSession() {
            session_start();
            $_SESSION["username"] = $_POST["username"];
        }

        public function returnResponse(bool $b) {
            $str = $b ? "SUCCESS" : "FAILURE";
            return json_encode(array("loginStatus" => $str));
        }

        public function login() {
            if ($this->isCredentialsCorrect()) {
                $this->initSession();
                return $this->returnResponse(true);
            }
            return $this->returnResponse(false);
        }
    }

    if (!isset($_POST["username"]) or !isset($_POST["password"])) {
        die();
    } else {
        echo (new login(new mysqli("localhost", "root", "", "Widgets"), $_POST["username"], $_POST["password"]))->login();
    }