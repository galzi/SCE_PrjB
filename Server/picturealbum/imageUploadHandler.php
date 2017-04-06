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
            if (isset($this->file)) {
                $Err = array();
                if (!$this->IsAnImage()) {
                    array_push($Err, "Unsupported file type.");
                }

                if ($this->isFileNameExists()) {
                    array_push($Err, "File with the same name already exists.");
                }

                if ($this->isLargeFile()) {
                    array_push($Err, "File exceeded size limit.");
                }

                if (count($Err) == 0) {
                    if ($this->moveDirectory()) {
                        return json_encode(array("status" => "Success", "message" => "File successfully uploaded."));
                    }
                    return json_encode(array("status" => "Failure", "message" => "An error occurred while uploading the file."));
                } else {
                    return json_encode(array("status" => "Error", "message" => $Err));
                }
            } else {
                return json_encode(array("status" => "Failure", "message" => "No file was sent."));
            }
        }
    }

    echo (new uploadHandler($_FILES["fileToUpload"]))->uploadFile();
?>