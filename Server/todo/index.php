<?php
    class ToDo {
        private $User;
        private $SQL;

        public function __construct(User $user, mysqli $SQL) {
            $this->User = $user;
            $this->SQL = $SQL;
        }

        public function insert(string $task) {
            return $this->SQL->query("INSERT INTO todo (username, content)
                                    VALUES ('" . $this->User->getUsername() . "', '" . $task . "')") ? $this->returnResponse(ToDoFailure::Success) : $this->returnResponse(ToDoFailure::Failure);
        }

        public function remove(string $task) {
            $this->SQL->query("DELETE FROM todo
                                    WHERE username = '" . $this->User->getUsername() . "' and url = '" . $task . "'");
        }

        public function get() {
            $tasks = $this->SQL->query("SELECT content FROM todo WHERE username = '" . $this->User->getUsername() . "'");

            $_tasks = array();
            foreach ($tasks as $i) {
                array_push($_tasks, array("task" => array("checked" => $i["checked"], "content" => $i["content"])));
            }
            return json_encode(array("url" => $_tasks));
        }

        public function check(bool $b) {

        }

        public function returnResponse($status) {
            switch ($status) {
                case ToDoFailure::Success:
                    return json_encode(array("status" => "Success"));
                case ToDoFailure::Failure:
                    return json_encode(array("status" => "Failure"));
                case ToDoFailure::IllegalChar: // ?
                    return json_encode(array("status" => "Illegal characters found!"));
                default:
                    return null;
            }
        }

        public function checkInjection(string $URL) { // ?
            foreach (array('||', '-', '*', /*'/',*/ '<>', '<', '>', ',', '=', '<=', '>=', '~=', '!=', '^=', '(', ')', ';') as $i) { // http://forums.codeguru.com/showthread.php?350081-Invalid-characters-in-sql
                if (strpos($URL, $i) !== false) {
                    return true;
                }
            }
            return false;
        }
    }
?>