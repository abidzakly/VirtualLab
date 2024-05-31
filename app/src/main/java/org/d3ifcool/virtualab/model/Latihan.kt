package org.d3ifcool.virtualab.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "latihan")
data class Latihan(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: Int,
    val titleData: Int,
    val route: String
)
