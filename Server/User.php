<?php

class User {
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

    public function login() {
        if ($this->isCredentialsCorrect()) {
            $this->initSession();
            return $this->returnResponse(RegFailure::Login, true);
        }
        return $this->returnResponse(RegFailure::Login, false);
    }

    public function verifyFormat() {
        if (!preg_match("/^[a-z0-9_-]{6,18}$/", $this->pass) or !preg_match("/^[a-z0-9_-]{3,16}$/", $this->username)) {
            return false;
        }
        return true;
    }

    public function isExists() {
        return ($this->SQL->query("SELECT * FROM users WHERE username = '" . $this->username . "'"))->num_rows == 1;
    }

    public function createUser() {
        $this->SQL->query("INSERT INTO users (username, password)
                             VALUES ('" . $this->username . "', '" . $this->pass . "')");
    }

    public function returnResponse(RegFailure $status, bool $b = false) {
        switch ($status) {
            case RegFailure::IllegalFormat:
                return json_encode(array("MSG" => "Illegal format!\nUsernames and passwords must contain only latin characters, digits and hyphens. \nUsernames has to be 3 to 16 characters long and passwords 6 to 18 characters long.", "Code" => 1));
                break;
            case RegFailure::SameUsername:
                return json_encode(array("MSG" => "A user with the same username already exists.", "Code" => 2));
                break;
            case RegFailure::Success:
                break;
            case RegFailure::Login:
                $str = $b ? "SUCCESS" : "FAILURE";
                return json_encode(array("loginStatus" => $str));
            default:
                return null;
                break;
        }
        return null;
    }

    public function register() {
        if (!$this->verifyFormat()) {
            return $this->returnResponse(RegFailure::IllegalFormat);
        } elseif ($this->isExists()) {
            return $this->returnResponse(RegFailure::SameUsername);
        } else {
            $this->createUser();
            return $this->returnResponse(RegFailure::Success);
        }
    }

    public function getUsername(): string {
        return $this->username;
    }
}