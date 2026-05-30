package com.hb.di

import com.hb.dao.post.PostDao
import com.hb.dao.post.PostDaoImpl
import com.hb.dao.post_comments.PostCommentsDao
import com.hb.dao.post_comments.PostCommentsDaoImpl
import com.hb.dao.post_likes.PostLikesDao
import com.hb.dao.post_likes.PostLikesDaoImpl
import com.hb.dao.user.UserDao
import com.hb.dao.user.UserDaoImpl
import com.hb.service.impl.AwesomeServiceImpl
import com.hb.service.impl.UserServiceImpl
import org.koin.dsl.module
import service.AwesomeService
import service.UserService

fun appModule() = listOf(userModule, otherModule)


val userModule = module {
    single<UserDao> { UserDaoImpl() }
    single<UserService> {
        UserServiceImpl(userDao = get())
    }
}

val otherModule = module {

    single<PostDao> { PostDaoImpl() }
    single<PostLikesDao> { PostLikesDaoImpl() }
    single<PostCommentsDao> { PostCommentsDaoImpl() }

    single<AwesomeService> {
        AwesomeServiceImpl()
    }
}
