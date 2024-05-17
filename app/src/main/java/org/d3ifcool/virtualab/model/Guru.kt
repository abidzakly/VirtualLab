package org.d3ifcool.virtualab.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "guru")
data class Guru(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val username: String,
    val NISN: String,
    val asalSekolah: String,
    val email: String,
    val password: String,
)