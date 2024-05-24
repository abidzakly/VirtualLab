package org.d3ifcool.virtualab.ui.screen.guru.materi

import androidx.lifecycle.ViewModel
import org.d3ifcool.virtualab.model.Materi

class GuruMateriViewModel: ViewModel() {
    val data = getDataDummy()
    private fun getDataDummy(): List<Materi> {
        val data = mutableListOf<Materi>()
        for (i in 30 downTo 1) {
            data.add(
                Materi(
                    1,
                    1,
                    ""
                )
            )
        }
        return data
    }
}