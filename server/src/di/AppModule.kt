package com.hb.di

import com.hb.dao.plate.PlateDao
import com.hb.dao.plate.PlateDaoImpl
import com.hb.dao.post.PostDao
import com.hb.dao.post.PostDaoImpl
import com.hb.dao.post_comments.PostCommentsDao
import com.hb.dao.post_comments.PostCommentsDaoImpl
import com.hb.dao.user.UserDao
import com.hb.dao.user.UserDaoImpl
import com.hb.service.impl.AwesomeServiceImpl
import com.hb.service.impl.PlateServiceImpl
import com.hb.service.impl.PostServiceImpl
import com.hb.service.impl.UserServiceImpl
import org.koin.dsl.module
import service.AwesomeService
import service.PlateService
import service.PostService
import service.UserService

fun appModule() = listOf(userModule, plateModule, postModule, otherModule)


val userModule = module {
    single<UserDao> { UserDaoImpl() }
    single<UserService> {
        UserServiceImpl(userDao = get())
    }
}

val plateModule = module {
    single<PlateDao> { PlateDaoImpl() }
    single<PlateService> {
        PlateServiceImpl(get())
    }
}

val postModule = module {
    single<PostDao> { PostDaoImpl() }
    single<PostService> {
        PostServiceImpl(get())
    }
}


val otherModule = module {

    single<PostCommentsDao> { PostCommentsDaoImpl() }

    single<AwesomeService> {
        AwesomeServiceImpl()
    }
}
