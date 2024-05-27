package org.d3ifcool.virtualab.ui.screen.guru.latihan

import androidx.lifecycle.ViewModel
import org.d3ifcool.virtualab.model.Latihan

class GuruLatihanViewModel: ViewModel() {
    val data = getDataDummy()
    private fun getDataDummy(): List<Latihan> {
        val data = mutableListOf<Latihan>()
        for (i in 30 downTo 1) {
            data.add(
                Latihan(
                    1,
                    1,
                    1,
                    route = ""
                )
            )
        }
        return data
    }
}