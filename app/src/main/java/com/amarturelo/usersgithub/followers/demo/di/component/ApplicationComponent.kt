package com.amarturelo.usersgithub.followers.demo.di.component

import com.amarturelo.usersgithub.followers.demo.App
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import com.amarturelo.usersgithub.followers.demo.di.module.AppModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        AppModule::class,
    ]
)
interface ApplicationComponent : AndroidInjector<App> {
    @Component.Factory
    interface Builder : AndroidInjector.Factory<App>
}