<?php
    class SQLManipulator {
        private $servername;
        private $username;
        private $password;
        private $dbname;
        private $conn; // with/out dbname
        private $status = false;
        private static $instance = null;

        private function __construct($servername, $username, $password, $dbname) {
            $this->servername = $servername;
            $this->username = $username;
            $this->password = $password;
            $this->dbname = $dbname;

            self::initializeConnection();
        }

        public function initializeConnection() {
            if (!$this->conn) {
                $this->conn = new mysqli($this->servername, $this->username, $this->password);
            }
            return $this->conn;
        }

        public function createDB() {
            if (!$this->status) {
                $this->conn->query("CREATE DATABASE " . $this->dbname);
                $this->conn->select_db($this->dbname);
                $this->status = true;
            }
        }

        public function performQuery($sql) {
            return $this->conn->query($sql);
        }

        public function iterate($sql) {

        }

        public function closeConnection() {
            $this->conn->close();
        }

        public function toJSON($query) {

        }

        public static function getInstance($servername = null, $username = null, $password = null, $dbname = null) {
            if (self::$instance == null) {
                self::$instance = new SQLManipulator($servername, $username, $password, $dbname);
            }
            return self::$instance;
        }
    }
?>