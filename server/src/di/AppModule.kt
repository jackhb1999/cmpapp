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
import com.hb.repository.DeptRepositoryImpl
import com.hb.repository.FollowsRepositoryImpl
import com.hb.repository.PostCommentsRepositoryImpl
import com.hb.repository.PostRepositoryImpl
import com.hb.repository.ProfileRepositoryImpl
import repository.UserRepository
import com.hb.repository.UserRepositoryImpl
import fake_data.Post
import org.koin.dsl.module
import repository.FollowsRepository
import repository.PostCommentsRepository
import repository.PostRepository
import repository.ProfileRepository

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
}
