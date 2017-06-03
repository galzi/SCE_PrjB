<?php
    // var_dump($_SERVER['REQUEST_URI']);
    // var_dump($_SERVER['QUERY_STRING']);

    if ((!isset($_GET['month']) or !isset($_GET['day'])) or ($_GET['month'] == null or $_GET['day'] == null)) {
        $today = new DateTime();
        $url = "https://en.wikipedia.org/api/rest_v1/feed/onthisday/all/" . $today->format('m/d');;
    } else {
        $url = "https://en.wikipedia.org/api/rest_v1/feed/onthisday/all/" . $_GET['month'] . "/" . $_GET['day'];
    }
    $result = file_get_contents($url);
    echo $result;
?>