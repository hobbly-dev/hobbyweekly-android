package kr.hobbly.hobbyweekly.android.data.remote.network.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kr.hobbly.hobbyweekly.android.data.remote.local.SharedPreferencesManager
import kr.hobbly.hobbyweekly.android.data.remote.network.environment.BaseUrlProvider
import kr.hobbly.hobbyweekly.android.data.remote.network.environment.ErrorMessageMapper

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkEnvironmentModule {

    @Provides
    @Singleton
    fun provideBaseUrlProvider(
        @ApplicationContext context: Context,
        sharedPreferencesManager: SharedPreferencesManager
    ): BaseUrlProvider {
        return BaseUrlProvider(context, sharedPreferencesManager)
    }

    @Provides
    @Singleton
    fun provideErrorMessageMapper(
        @ApplicationContext context: Context
    ): ErrorMessageMapper {
        return ErrorMessageMapper(context)
    }
}
