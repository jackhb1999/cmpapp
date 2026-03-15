package com.hb.model

import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.datetime

object FollowsTable : Table(name = "follows") {
    val followerId = binary(name = "follower_id")
    val followingId = binary(name = "following_id")
    val followDate = datetime(name = "follow_date").defaultExpression(defaultValue = CurrentDateTime)
}