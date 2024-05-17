package org.d3ifcool.virtualab.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import org.d3ifcool.virtualab.model.Guru
import org.d3ifcool.virtualab.model.Siswa

//@Database(entities = [Guru::class, Siswa::class], version = 1, exportSchema = false)
//abstract class VLabDb : RoomDatabase() {
//
////    abstract val dao: VLabDao
//
//    companion object {
//
//        @Volatile
//        private var INSTANCE: VLabDb? = null
//
//        fun getInstance(context: Context): VLabDb {
//            synchronized(this) {
//                var instance = INSTANCE
//
//                if (instance == null) {
//                    instance = Room.databaseBuilder(
//                        context.applicationContext,
//                        VLabDb::class.java,
//                        "vlab.db"
//                    ).build()
//                    INSTANCE = instance
//                }
//                return instance
//            }
//        }
//
//    }
//
//}