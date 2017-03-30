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
            if ($this->performQuery("SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '" . $this->dbname . "'")->num_rows != 0) {
                $this->status = true;
                $this->conn->select_db($this->dbname);
            }
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
                return true;
            }
            return false;
        }

        public function performQuery($sql) {
            return $this->conn->query($sql);
        }

        public function iterate($queryResults) {
            $arr = array();
            while ($row = $queryResults->fetch_array(MYSQLI_ASSOC)) {
                array_push($arr, $row);
            }
            $queryResults->free(); // is assigning the address and free will delete the arrays?
            return $arr;
        }

        public function closeConnection() {
            $this->conn->close();
        }

        public function toJSON($queryResults) {
            return json_encode($this->iterate($queryResults));
        }

        public static function getInstance($servername = null, $username = null, $password = null, $dbname = null) {
            if (self::$instance == null) {
                self::$instance = new SQLManipulator($servername, $username, $password, $dbname);
            }
            return self::$instance;
        }
    }
?>