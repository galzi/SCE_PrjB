<?php

    interface Widget {
        public function insert(string $data, mysqli $SQL);
        public function remove(string $data, mysqli $SQL);
        public function get(mysqli $SQL);
        public function returnResponse($status);
    }