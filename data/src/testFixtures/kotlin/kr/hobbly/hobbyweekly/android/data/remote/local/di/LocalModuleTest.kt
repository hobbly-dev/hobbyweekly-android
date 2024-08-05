package kr.hobbly.hobbyweekly.android.data.remote.local.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton
import kotlinx.coroutines.test.TestScope
import kr.hobbly.hobbyweekly.android.data.remote.local.database.HobbyWeeklyDatabase
import kr.hobbly.hobbyweekly.android.data.remote.local.database.sample.SampleDao
import kr.hobbly.hobbyweekly.android.data.remote.local.preferences.PreferencesConstant

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [LocalModule::class]
)
object LocalModuleTest {
    @Provides
    @Singleton
    internal fun provideDataStore(
        @ApplicationContext context: Context,
        scope: TestScope
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            scope = scope,
            produceFile =
            { context.preferencesDataStoreFile(PreferencesConstant.PREFERENCES_NAME) }
        )
    }

    @Provides
    @Singleton
    internal fun provideHobbyWeeklyDatabase(
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
    internal fun provideSampleDao(
        hobbyWeeklyDatabase: HobbyWeeklyDatabase
    ): SampleDao {
        return hobbyWeeklyDatabase.sampleDao()
    }
}
