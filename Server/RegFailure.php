<?php
class RegFailure extends SplEnum {
    const __default = self::Success;

    const Success = 0;
    const IllegalFormat = 1;
    const SameUsername = 2;
    const Login = 3;
}