package com.hb.di

import com.hb.dao.user.UserDao
import com.hb.dao.user.UserDaoImpl
import repository.UserRepository
import com.hb.repository.UserRepositoryImpl
import org.koin.dsl.module

val appModule = module {
    single<UserRepository> {
        UserRepositoryImpl(get())
    }
    single<UserDao> { UserDaoImpl() }
}
