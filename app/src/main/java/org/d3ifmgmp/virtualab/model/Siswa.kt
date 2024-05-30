package org.d3ifmgmp.virtualab.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "siswa")
data class Siswa(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val username: String,
    val NISN: String,
    val asalSekolah: String,
    val email: String,
    val password: String,
)