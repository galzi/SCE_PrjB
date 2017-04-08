<?php
    require 'lib/Slim/Slim.php';
    use lib\Slim\Middleware\SessionCookie;

    \Slim\Slim::registerAutoloader();

    $app = new \Slim\Slim(
        array(
            'cookies.encrypt' => true,
            'cookies.secret_key' => 'my_secret_key',
            'cookies.cipher' => MCRYPT_RIJNDAEL_256,
            'cookies.cipher_mode' => MCRYPT_MODE_CBC
        )
    );

    $app->add(new \Slim\Middleware\SessionCookie(array(
        'expires' => '20 minutes',
        'path' => '/',
        'domain' => '',
        'secure' => false,
        'httponly' => false,
        'name' => 'slim_session',
        'secret' => '',
        'cipher' => MCRYPT_RIJNDAEL_256,
        'cipher_mode' => MCRYPT_MODE_CBC
    )));

    $app->get("/login/:string", function($string) use ($app) {
        $input = json_decode($string);
        try {
            if ($input->username && $input->password) {
                $user = Model::factory('Users')->where("username",$input->username)->where("password",md5($input->password))->find_one();
                //$app->setCookie('user_id',$user->id);
                session_cache_limiter(false);
                session_start();
                $_SESSION['id'] =  $user->id;

                $status = 'success';
                $message = 'Logged in successfully.';
            }
            else {
                $status = false;
                $message = 'Could not log you in. Please try again.';
            }

        }
        catch (Exception $e) {
            $status = 'danger';
            $message = $e->getMessage();
        }
        $response = array(
            'status' => $status,
            'message' => $message
        );
        $app->response()->header("Content-Type", "application/json");
        echo json_encode($response);
    });

    $app->get("/logout",function() use ($app) {
        try {
            unset($_SESSION['id']);
            session_destroy();
            session_start();

            //$app->getCookie('user_id');

            $status = 'success';
            $message = 'You have been logged out successfully';
        }

        catch (Exception $e) {
            $status = 'danger';
            $message = $e->getMessage();
        }
        $response = array(
            'status' => $status,
            'message' => $message
        );

        $app->response()->header("Content-Type", "application/json");
        echo json_encode($response);
    });
?>