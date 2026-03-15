package com.hb.di

import com.hb.dao.dept.DeptDao
import com.hb.dao.dept.DeptDaoImpl
import com.hb.dao.follows.FollowsDao
import com.hb.dao.follows.FollowsDaoImpl
import com.hb.dao.user.UserDao
import com.hb.dao.user.UserDaoImpl
import com.hb.repository.DeptRepositoryImpl
import com.hb.repository.FollowsRepositoryImpl
import repository.UserRepository
import com.hb.repository.UserRepositoryImpl
import org.koin.dsl.module

val appModule = module {
    single<UserRepository> {
        UserRepositoryImpl(get())
    }
    single {
        DeptRepositoryImpl(get())
    }
    single {
        FollowsRepositoryImpl(userDao = get(), followsDao = get())
    }
    single<UserDao> { UserDaoImpl() }
    single<DeptDao> { DeptDaoImpl() }
    single<FollowsDao> { FollowsDaoImpl() }
}
