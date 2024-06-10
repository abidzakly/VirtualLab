package org.d3ifcool.virtualab.ui.screen.guru.materi

import androidx.lifecycle.ViewModel
import org.d3ifcool.virtualab.model.Materi

class GuruMateriViewModel: ViewModel() {
    val data = getDataDummy()
    private fun getDataDummy(): List<Materi> {
        val data = mutableListOf<Materi>()
//        for (i in 29 downTo 20) {
//            data.add(
//                Materi(
//                    i.toLong(),
//                    "Penyetaraan reaksi kimia",
//                    "",
//                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed ultricies lectus nec ultricies accumsan. Praesent consectetur rhoncus metus, in vestibulum nulla. Quisque vehicula lectus auctor, cursus nunc mollis, aliquet mi. In sollicitudin, augue quis posuere venenatis, ex nulla scelerisque massa, non semper augue velit a odio. "
//                )
//            )
//        }
        return data
    }
}