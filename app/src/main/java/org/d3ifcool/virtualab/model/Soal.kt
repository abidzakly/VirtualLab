package org.d3ifcool.virtualab.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "soal")
data class Soal(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: Int,
    val question: Int,
    val firstAnswer: Int,
    val secondAnswer: Int,
    val route: String
)