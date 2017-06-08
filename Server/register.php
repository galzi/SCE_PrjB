<?php
    include "dbLogin.php";

    if (!isset($_POST)) {
        die();
    }

    // regex
    if (!preg_match("/^[a-z0-9_-]{6,18}$/", $_POST["password"]) or !preg_match("/^[a-z0-9_-]{3,16}$/", $_POST["username"])) {
        echo json_encode(array("MSG" => "Illegal format!\nUsernames and passwords must contain only latin characters, digits and hyphens. Usernames has to be 3 to 16 characters long and passwords 6 to 18 characters long.", "Code" => 1));
        die();
    }


    // does already exist
    $SQL->initializeConnection();
    $query = $SQL->performQuery("SELECT * FROM users WHERE username = '" . $_POST["username"] . "'");
    if (sizeof($SQL->iterate($query)) == 1) {
        echo json_encode(array("MSG" => "A user with the same username already exists.", "Code" => 2));
        die();
    }

    // sql
    $SQL->performQuery("INSERT INTO users (username, password)
                             VALUES ('" . $_POST["username"] . "', '" . $_POST["password"] . "')");
    echo json_encode(array("MSG" => "User successfully created.", "Code" => 0));

    // json response

    /*
    POSIX Regular Expressions
    ereg(regex, str);

    Username
    /^[a-z0-9_-]{3,16}$/
    lowercase letter (a-z), number (0-9), an underscore, or a hyphen
    at least 3 of those characters, but no more than 16

    Password
    /^[a-z0-9_-]{6,18}$/
    letters, numbers, underscores, or hyphens
    6 to 18

    URL
    /^(https?:\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?$/

    */

    class Register {
        private $SQL;
        private $username;
        private $pass;

        public function __construct(mysqli $SQL, string $username, string $pass) {
            $this->SQL = $SQL;
            $this->username = $username;
            $this->pass = $pass;
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

        public function returnResponse($status) {
            switch ($status) {
                case RegFailure::IllegalFormat:
                    return json_encode(array("MSG" => "Illegal format!\nUsernames and passwords must contain only latin characters, digits and hyphens. \nUsernames has to be 3 to 16 characters long and passwords 6 to 18 characters long.", "Code" => 1));
                    break;
                case RegFailure::SameUsername:
                    return json_encode(array("MSG" => "A user with the same username already exists.", "Code" => 2));
                    break;
                case RegFailure::Success:
                    break;
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
    }




(new Register(new mysqli("localhost", "root", "", "Widgets"), $_POST["username"], $_POST["password"]))->register();