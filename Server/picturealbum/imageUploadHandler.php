<?php
    class uploadHandler {
        private static $target_dir = "uploads/";

        public static function setTargetDir($path) {
            self::$target_dir = $path;
        }

        private function getFileName() {
            return $_FILES["fileToUpload"]["name"];
        }

        private function getFileTempName() {
            return $_FILES["fileToUpload"]["tmp_name"];
        }

        private function getFileType() {
            return $_FILES["fileToUpload"]["type"];
        }

        private function getFileSize() {
            return $_FILES["fileToUpload"]["size"];
        }

        private function IsAnImage() {
            $file_type = self::getFileType();
            if ($file_type == "image/jpg" || $file_type == "image/jpeg" || $file_type == "image/gif" || $file_type == "image/png")
                return true;
            return false;
        }

        private function isFileNameExists() {
            if (file_exists(self::$target_dir . basename(self::getFileName()))) {
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
            move_uploaded_file(self::getFileTempName(), self::$target_dir . basename(self::getFileName()));
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

            $this->moveDirectory();
            return json_encode(array("Error" => "File successfully uploaded."));
        }
    }

    echo (new uploadHandler())->uploadFile();
?>