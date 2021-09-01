package com.mustafatech.RedditLite.exception

import java.lang.RuntimeException

class SpringRedditException(exMsg: String) : RuntimeException(exMsg) {
}