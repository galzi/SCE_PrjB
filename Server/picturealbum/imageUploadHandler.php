<?php

class uploadHandler{

    private static $target_dir;

    public static function setTargetDir($path){
        self::$target_dir = $path;
    }

    public static function getFileName(){
        return $_FILES["fileToUpload"]["name"];
    }

    static function getFileTempName(){
        return $_FILES["fileToUpload"]["tmp_name"];
    }

    static function getFileType(){
        return $_FILES["fileToUpload"]["type"];
    }

    static function getFileSize(){
        return $_FILES["fileToUpload"]["size"];
    }

    static function moveDirectory(){
        move_uploaded_file(self::getFileTempName(),self::$target_dir . basename(self::getFileName()));
    }

    static function IsAnImage()
    {
        $file_type = self::getFileType();
        if ($file_type == "image/jpg" || $file_type == "image/jpeg" || $file_type == "image/gif" || $file_type == "image/png")
            return true;

        return false;
    }

    static function isFileNameExists(){
        if(file_exists(self::$target_dir . basename(self::getFileName()))) {
            return true;
        }
        return false;
    }

    static function isLargeFile(){
        if(uploadHandler::getFileSize()>500000)
            return true;
        return false;
    }
}

uploadHandler::setTargetDir("uploads/");
if(uploadHandler::IsAnImage() && !uploadHandler::isFileNameExists() && !uploadHandler::isLargeFile()){
    uploadHandler::moveDirectory();
}
?>