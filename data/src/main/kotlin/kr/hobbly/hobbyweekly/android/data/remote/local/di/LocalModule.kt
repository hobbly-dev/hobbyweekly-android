package kr.hobbly.hobbyweekly.android.data.remote.local.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kr.hobbly.hobbyweekly.android.data.remote.local.SharedPreferencesManager
import kr.hobbly.hobbyweekly.android.data.remote.local.database.HobbyWeeklyDatabase
import kr.hobbly.hobbyweekly.android.data.remote.local.database.sample.SampleDao

@Module
@InstallIn(SingletonComponent::class)
internal object LocalModule {

    @Provides
    @Singleton
    fun provideSharedPreferencesManager(
        @ApplicationContext context: Context
    ): SharedPreferencesManager {
        return SharedPreferencesManager(context)
    }

    @Provides
    @Singleton
    fun provideHobbyWeeklyDatabase(
        @ApplicationContext context: Context
    ): HobbyWeeklyDatabase {
        return Room.databaseBuilder(
            context,
            HobbyWeeklyDatabase::class.java,
            HobbyWeeklyDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideSampleDao(
        hobbyWeeklyDatabase: HobbyWeeklyDatabase
    ): SampleDao {
        return hobbyWeeklyDatabase.sampleDao()
    }
}
