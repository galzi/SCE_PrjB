<?php
    class History {
        public static function getHistory(string $type, int $month, int $day) {
            if ($type == null) {
                die();
            }

            if ($month == null or $day == null) {
                $today = new DateTime();
                $url = "https://en.wikipedia.org/api/rest_v1/feed/onthisday/" . $type . "/" . $today->format('m/d');;
            } else {
                if (($month >= 1) and ($month <= 12) and ($day >= 1) and ($day <= 31)) {
                    $url = "https://en.wikipedia.org/api/rest_v1/feed/onthisday/" . $type . "/" . $month . "/" . $day;
                }
            }
            return file_get_contents($url);
        }
    }
