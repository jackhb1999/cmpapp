package com.hb.di

import com.hb.dao.dept.DeptDao
import com.hb.dao.dept.DeptDaoImpl
import com.hb.dao.user.UserDao
import com.hb.dao.user.UserDaoImpl
import com.hb.repository.DeptRepositoryImpl
import repository.UserRepository
import com.hb.repository.UserRepositoryImpl
import org.koin.dsl.module

val appModule = module {
    single<UserRepository> {
        UserRepositoryImpl(get())
    }
    single{
        DeptRepositoryImpl(get())
    }
    single<UserDao> { UserDaoImpl() }
    single<DeptDao> { DeptDaoImpl() }
}
