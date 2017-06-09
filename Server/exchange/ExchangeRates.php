<?php
    include "Widget.php";

    class ExchangeRates implements Widget {
        private $User;

        public function __construct(User $user) {
            $this->User = $user;
        }

        public function insert(string $curr, mysqli $SQL) {
            $curr = explode("|", $curr);

            return $SQL->query("INSERT INTO exchange (username, source, destination)
                                        VALUES ('" . $this->User->getUsername() . "', '" . $curr[0] . "', '" . $curr[1] . "')") ? $this->returnResponse(ExchangeFailure::Success) : $this->returnResponse(ExchangeFailure::AlreadyExists);
        }

        public function remove(string $curr, mysqli $SQL) {
            $curr = explode("|", $curr);

            $SQL->query("DELETE FROM exchange
                                        WHERE username = '" . $this->User->getUsername() . "' and source = '" . $curr[0] . "' and destination = '" . $curr[1] . "'");
        }

        public function get(mysqli $SQL) {
            $Exchange = $SQL->query("SELECT source, destination FROM exchange WHERE username = '" . $this->User->getUsername() . "'");

            $_Exchange = array();
            foreach ($Exchange as $i) {
                array_push($_Exchange, array("curr" => array("source" => $i["source"], "destination" => $i["destination"])));
            }
            return json_encode(array("list" => $_Exchange));
        }

        public function returnResponse($status) {
            switch ($status) {
                case RSSFailure::AlreadyExists:
                    return json_encode(array("status" => "Currency conversion selection record already exists!"));
                case RSSFailure::Success:
                    return json_encode(array("status" => "Success"));
                default:
                    return null;
            }
        }
    }