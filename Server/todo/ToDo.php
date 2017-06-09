<?php
    // include "Widget.php";
    include "ToDoFailure.php";

    class ToDo implements Widget {
        private $User;

        public function __construct(User $user) {
            $this->User = $user;
        }

        public function insert(string $task, mysqli $SQL) {
            return $SQL->query("INSERT INTO todo (username, content)
                                    VALUES ('" . $this->User->getUsername() . "', '" . $task . "')") ? $this->returnResponse(ToDoFailure::Success) : $this->returnResponse(ToDoFailure::Failure);
        }

        public function remove(string $task, mysqli $SQL) {
            $SQL->query("DELETE FROM todo
                                    WHERE username = '" . $this->User->getUsername() . "' and content = '" . $task . "'");
        }

        public function get(mysqli $SQL) {
            $tasks = $SQL->query("SELECT * FROM todo WHERE username = '" . $this->User->getUsername() . "'");

            $_tasks = array();
            foreach ($tasks as $i) {
                array_push($_tasks, array("task" => array("checked" => $i["checked"], "content" => $i["content"])));
            }
            return json_encode(array("list" => $_tasks));
        }

        public function check(string $task, bool $b, mysqli $SQL) {
            $SQL->query("UPDATE todo
                                      SET checked = " . ($b ? "1" : "0") . "
                                      WHERE username = '" . $this->User->getUsername() . "' and content = '" . $task . "'");
        }

        public function returnResponse($status) {
            switch ($status) {
                case ToDoFailure::Success:
                    return json_encode(array("status" => "Success"));
                case ToDoFailure::Failure:
                    return json_encode(array("status" => "Failure"));
                default:
                    return null;
            }
        }
    }
?>