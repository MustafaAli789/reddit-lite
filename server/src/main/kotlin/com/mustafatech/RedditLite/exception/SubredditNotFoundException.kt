package com.mustafatech.RedditLite.exception

import java.lang.RuntimeException

class SubredditNotFoundException(msg: String) : RuntimeException(msg)