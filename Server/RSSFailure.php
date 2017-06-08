<?php
class RSSFailure extends SplEnum {
    const __default = self::Success;

    const Success = 0;
    const IllegalChar = 1;
    const IllegalURL = 2;
    const AlreadyExists = 3;
}