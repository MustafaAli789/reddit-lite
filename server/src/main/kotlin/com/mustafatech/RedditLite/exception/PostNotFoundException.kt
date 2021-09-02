package com.mustafatech.RedditLite.exception

import java.lang.RuntimeException

class PostNotFoundException(msg: String) : RuntimeException(msg)