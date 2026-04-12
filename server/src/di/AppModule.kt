package com.hb.di

import com.hb.dao.dept.DeptDao
import com.hb.dao.dept.DeptDaoImpl
import com.hb.dao.follows.FollowsDao
import com.hb.dao.follows.FollowsDaoImpl
import com.hb.dao.post.PostDao
import com.hb.dao.post.PostDaoImpl
import com.hb.dao.post_comments.PostCommentsDao
import com.hb.dao.post_comments.PostCommentsDaoImpl
import com.hb.dao.post_likes.PostLikesDao
import com.hb.dao.post_likes.PostLikesDaoImpl
import com.hb.dao.user.UserDao
import com.hb.dao.user.UserDaoImpl
import com.hb.repository.*
import com.hb.service.AwesomeServiceImpl
import org.koin.dsl.module
import repository.*
import service.AwesomeService

val appModule = module {
    single<UserDao> { UserDaoImpl() }
    single<DeptDao> { DeptDaoImpl() }
    single<FollowsDao> { FollowsDaoImpl() }
    single<PostDao> { PostDaoImpl() }
    single<PostLikesDao> { PostLikesDaoImpl() }
    single<PostCommentsDao> { PostCommentsDaoImpl() }

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
    single<ProfileRepository> {
        ProfileRepositoryImpl(userDao = get(), followsDao = get())
    }
    single<PostCommentsRepository> {
        PostCommentsRepositoryImpl(postCommentsDao = get(), postDao = get())
    }
    single<PostLikesRepository> {
        PostLikesRepositoryImpl(likesDao = get(), postDao = get())
    }

    single<AwesomeService>{
        AwesomeServiceImpl(userDao = get())
    }
}
