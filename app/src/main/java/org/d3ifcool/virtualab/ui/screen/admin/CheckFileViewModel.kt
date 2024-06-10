package org.d3ifcool.virtualab.ui.screen.admin

import androidx.lifecycle.ViewModel
import org.d3ifcool.virtualab.data.model.Latihan

class CheckFileViewModel: ViewModel() {
    val data = getDataDummy()
    private fun getDataDummy(): List<Latihan> {
        val data = mutableListOf<Latihan>()
//        for (i in 30 downTo 1) {
//            data.add(
//                Latihan(
//                    1,
//                    1,
//                    1,
//                    route = ""
//                )
//            )
//        }
        return data
    }
}