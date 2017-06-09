<?php
    class checkInjection {
        public static function check(string $data) { // ?
            foreach (array('||', '-', '*', /*'/',*/ '<>', '<', '>', ',', '=', '<=', '>=', '~=', '!=', '^=', '(', ')', ';') as $i) { // http://forums.codeguru.com/showthread.php?350081-Invalid-characters-in-sql
                if (strpos($data, $i) !== false) {
                    return true;
                }
            }
            return false;
        }

        public static function returnResponse(bool $b) {
            if ($b) {
                return json_encode(array("status" => "Illegal characters found!"));
            }
            return null;
        }
    }
