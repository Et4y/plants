package com.softxpert.plants.data.di

import android.app.Activity
import com.softxpert.plants.ui.util.ProgressUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object UiModule {

    @Provides
    @ActivityScoped
    fun provideProgressUtil(activity: Activity): ProgressUtil {
        return ProgressUtil(activity)
    }


}