package com.kaankilic.news.RoomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kaankilic.news.model.News


@Database(entities = [News::class], version = 2)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao


    companion object {

        @Volatile
        private var instance: NewsDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: databaseOlustur(context).also {
                instance = it
            }
        }

        private fun databaseOlustur(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NewsDatabase::class.java,
            "FoodDatabase"

        )
            .addMigrations(MIGRATION_1_2)
            .build()
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE news ADD COLUMN isFavourite INTEGER DEFAULT 0 NOT NULL")
            }
        }



    }
}