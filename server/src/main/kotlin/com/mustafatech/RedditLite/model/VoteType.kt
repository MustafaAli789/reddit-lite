package com.mustafatech.RedditLite.model

enum class VoteType(val dir: Int) {
    UPVOTE(1), DOWNVOTE(-1);

//    fun lookup(dir: Int): VoteType {
//        val votetype = VoteType.values().filter { voteType -> voteType.dir == dir }.firstOrNull()
//        if (votetype == null) {
//            throw SpringRedditException("Vote not found")
//        }
//        return votetype
//    }
}