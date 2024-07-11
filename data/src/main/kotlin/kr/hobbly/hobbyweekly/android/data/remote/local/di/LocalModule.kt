package kr.hobbly.hobbyweekly.android.data.remote.local.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kr.hobbly.hobbyweekly.android.data.remote.local.database.HobbyWeeklyDatabase
import kr.hobbly.hobbyweekly.android.data.remote.local.database.sample.SampleDao
import kr.hobbly.hobbyweekly.android.data.remote.local.preferences.PreferencesConstant

@Module
@InstallIn(SingletonComponent::class)
internal object LocalModule {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = PreferencesConstant.PREFERENCES_NAME
    )

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return context.dataStore
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
