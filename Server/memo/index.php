<?php
    use \Psr\Http\Message\ServerRequestInterface as Request;
    use \Psr\Http\Message\ResponseInterface as Response;

    $app->get('/memo', function () {
        echo "welcome";
    });

    $app->get('/memo/{req}', function (Request $request, Response $response) {
        $name = $request->getAttribute('name');
        // $response->getBody()->write("Hello, $name");

        switch ($name) {
            case "add":
            case "del":
            case "edit":
                // which note?

        }

        return $response;
    });

    /*
     * TODO:
     * * Get sent content via POST method
     * * Implements a login authorization - identify connection by username and password, create a session.
     */
?>