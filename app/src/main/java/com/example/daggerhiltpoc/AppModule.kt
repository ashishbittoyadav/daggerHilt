package com.example.daggerhiltpoc

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Named("hiltString")
    fun providesString() = "This string from dagger hilt activity component app module."

    @Provides
    fun providesViewModelRepo() = TestViewModelRepository()
}