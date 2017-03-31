<?php
    class uploadHandler {
        private static $target_dir = "uploads/";
        private $file;

        public function __construct($file) {
            $this->file = $file;
        }

        public static function setTargetDir($path) {
            self::$target_dir = $path;
        }

        private function getFileName() {
            return ($this->file)["name"];
        }

        private function getFileTempName() {
            return ($this->file)["tmp_name"];
        }

        private function getFileType() {
            return ($this->file)["type"];
        }

        private function getFileSize() {
            return ($this->file)["size"];
        }

        private function IsAnImage() {
            $file_type = $this->getFileType();
            if ($file_type == "image/jpg" || $file_type == "image/jpeg" || $file_type == "image/gif" || $file_type == "image/png")
                return true;
            return false;
        }

        private function isFileNameExists() {
            if (file_exists(self::$target_dir . basename($this->getFileName()))) {
                return true;
            }
            return false;
        }

        private function isLargeFile() {
            if (uploadHandler::getFileSize() > 500000) {
                return true;
            }
            return false;
        }

        private function moveDirectory() {
            return move_uploaded_file($this->getFileTempName(), self::$target_dir . basename($this->getFileName()));
        }

        public function uploadFile() {
            if (!$this->IsAnImage()) {
                return json_encode(array("Error" => "Unsupported file type."));
            }

            if ($this->isFileNameExists()) {
                return json_encode(array("Error" => "File with the same name already exists."));
            }

            if ($this->isLargeFile()) {
                return json_encode(array("Error" => "File exceeded size limit."));
            }

            if ($this->moveDirectory()) {
                return json_encode(array("Error" => "File successfully uploaded."));
            }
            return json_encode(array("Error" => "An error occurred while uploading the file."));
        }
    }

    echo (new uploadHandler($_FILES["fileToUpload"]))->uploadFile();
?>