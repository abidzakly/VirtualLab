package org.d3ifcool.virtualab.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "materi")
data class Materi(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val judul: String,
    val mediaPembelajaran: String,
    val deskripsi: String
)
