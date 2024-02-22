package kr.hobbly.hobbyweekly.android.data.remote.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import kr.hobbly.hobbyweekly.android.data.remote.local.database.sample.SampleDao
import kr.hobbly.hobbyweekly.android.data.remote.local.database.sample.SampleEntity

@Database(entities = [SampleEntity::class], version = 1, exportSchema = false)
abstract class HobbyWeeklyDatabase : RoomDatabase() {
    abstract fun sampleDao(): SampleDao

    companion object {
        const val DATABASE_NAME = "hobby_weekly"
    }
}
