package com.hb.di

import com.hb.dao.dept.DeptDao
import com.hb.dao.dept.DeptDaoImpl
import com.hb.dao.follows.FollowsDao
import com.hb.dao.follows.FollowsDaoImpl
import com.hb.dao.post.PostDao
import com.hb.dao.post.PostDaoImpl
import com.hb.dao.post_likes.PostLikesDao
import com.hb.dao.post_likes.PostLikesDaoImpl
import com.hb.dao.user.UserDao
import com.hb.dao.user.UserDaoImpl
import com.hb.repository.DeptRepositoryImpl
import com.hb.repository.FollowsRepositoryImpl
import com.hb.repository.PostRepositoryImpl
import repository.UserRepository
import com.hb.repository.UserRepositoryImpl
import fake_data.Post
import org.koin.dsl.module
import repository.FollowsRepository
import repository.PostRepository

val appModule = module {
    single<UserDao> { UserDaoImpl() }
    single<DeptDao> { DeptDaoImpl() }
    single<FollowsDao> { FollowsDaoImpl() }
    single<PostDao> { PostDaoImpl() }
    single<PostLikesDao> { PostLikesDaoImpl() }

    single {
        DeptRepositoryImpl(get())
    }
    single<UserRepository> {
        UserRepositoryImpl(get())
    }
    single<FollowsRepository> {
        FollowsRepositoryImpl(userDao = get(), followsDao = get())
    }
    single<PostRepository> {
        PostRepositoryImpl(postDao = get(), followsDao = get(), postLikesDao = get())
    }
}
