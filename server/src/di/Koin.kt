package com.hb.di

import di.getSharedModules
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin


fun Application.configureDI(){
    install(Koin){
        modules(appModule + getSharedModules())
    }
}