<?php
    abstract class RSSFailure {
        const Success = 0;
        const IllegalChar = 1;
        const IllegalURL = 2;
        const AlreadyExists = 3;
    }