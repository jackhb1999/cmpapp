package com.hb.dao.follows

import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.datetime

object FollowsTable : Table(name = "follows") {
    val followerId = varchar(name = "follower_id", length = 21)
    val followingId = varchar(name = "following_id", length = 21)
    val followDate = datetime(name = "follow_date").defaultExpression(defaultValue = CurrentDateTime)
}