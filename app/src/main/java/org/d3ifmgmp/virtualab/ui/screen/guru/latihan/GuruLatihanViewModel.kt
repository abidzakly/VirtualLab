package org.d3ifmgmp.virtualab.ui.screen.guru.latihan

import androidx.lifecycle.ViewModel
import org.d3ifmgmp.virtualab.model.Materi

class GuruLatihanViewModel: ViewModel() {
    val data = getDataDummy()
    private fun getDataDummy(): List<Materi> {
        val data = mutableListOf<Materi>()
        for (i in 29 downTo 20) {
            data.add(
                Materi(
                    i.toLong(),
                    "Penyetaraan reaksi kimia",
                    "",

                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed ultricies lectus nec ultricies accumsan. Praesent consectetur rhoncus metus, in vestibulum nulla. Quisque vehicula lectus auctor, cursus nunc mollis, aliquet mi. In sollicitudin, augue quis posuere venenatis, ex nulla scelerisque massa, non semper augue velit a odio. "
                )
            )
        }
        return data
    }
}