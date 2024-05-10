package org.d3ifcool.virtualab.ui.screen.murid.dashboard

import androidx.lifecycle.ViewModel
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.model.Categories
import org.d3ifcool.virtualab.navigation.Screen

class DashboardViewModel : ViewModel() {

    val data = getList()

    private fun getList(): List<Categories> {
        val data = mutableListOf<Categories>()
        data.add(Categories(R.string.cateogry_pengenalan, R.drawable.pengenalan_illustration, Screen.Introduction.route))
        data.add(Categories(R.string.cateogry_materi_belajar, R.drawable.materi_illustration, Screen.Materi.route))
        data.add(Categories(R.string.cateogry_latihan, R.drawable.latihan_illustration, Screen.Latihan.route))
        data.add(Categories(R.string.cateogry_contoh_reaksi, R.drawable.contoh_reaksi_illustration, Screen.Reaksi.route))
        return data
    }

}