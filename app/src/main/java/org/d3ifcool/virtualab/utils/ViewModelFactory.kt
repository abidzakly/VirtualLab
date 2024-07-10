package org.d3ifcool.virtualab.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3ifcool.virtualab.repository.ArticleRepository
import org.d3ifcool.virtualab.repository.AuthRepository
import org.d3ifcool.virtualab.repository.ExerciseRepository
import org.d3ifcool.virtualab.repository.IntroRepository
import org.d3ifcool.virtualab.repository.MaterialRepository
import org.d3ifcool.virtualab.repository.StudentRepository
import org.d3ifcool.virtualab.repository.UserRepository
import org.d3ifcool.virtualab.ui.screen.AuthViewModel
import org.d3ifcool.virtualab.ui.screen.ProfileViewModel
import org.d3ifcool.virtualab.ui.screen.admin.approval.account.CheckUsersViewModel
import org.d3ifcool.virtualab.ui.screen.admin.approval.account.UserInfoViewModel
import org.d3ifcool.virtualab.ui.screen.admin.approval.content.CheckFileViewModel
import org.d3ifcool.virtualab.ui.screen.admin.approval.content.FileInfoViewModel
import org.d3ifcool.virtualab.ui.screen.admin.introduction.IntroContentViewModel
import org.d3ifcool.virtualab.ui.screen.admin.introduction.UpdateIntroViewModel
import org.d3ifcool.virtualab.ui.screen.guru.artikel.AddContohReaksiViewModel
import org.d3ifcool.virtualab.ui.screen.guru.artikel.DetailContohReaksiViewModel
import org.d3ifcool.virtualab.ui.screen.guru.artikel.GuruContohReaksiViewModel
import org.d3ifcool.virtualab.ui.screen.guru.dashboard.GuruDashboardViewModel
import org.d3ifcool.virtualab.ui.screen.guru.latihan.AddLatihanViewModel
import org.d3ifcool.virtualab.ui.screen.guru.latihan.AddSoalViewModel
import org.d3ifcool.virtualab.ui.screen.guru.latihan.DetailLatihanViewModel
import org.d3ifcool.virtualab.ui.screen.guru.latihan.GuruLatihanViewModel
import org.d3ifcool.virtualab.ui.screen.guru.materi.AddMateriViewModel
import org.d3ifcool.virtualab.ui.screen.guru.materi.DetailMateriViewModel
import org.d3ifcool.virtualab.ui.screen.guru.materi.GuruMateriViewModel
import org.d3ifcool.virtualab.ui.screen.murid.introduction.IntroductionViewModel
import org.d3ifcool.virtualab.ui.screen.murid.latihan.CekJawabanViewModel
import org.d3ifcool.virtualab.ui.screen.murid.latihan.MuridDetailLatihanViewModel
import org.d3ifcool.virtualab.ui.screen.murid.latihan.MuridListLatihanViewModel
import org.d3ifcool.virtualab.ui.screen.murid.materi.MuridDetailMateriViewModel
import org.d3ifcool.virtualab.ui.screen.murid.materi.MuridMateriViewModel
import org.d3ifcool.virtualab.ui.screen.murid.nilai.NilaiViewModel
import org.d3ifcool.virtualab.ui.screen.murid.reaksi.ContohReaksiScreenViewModel

class ViewModelFactory(
    private val id: Int? = null,
    private val str: String? = null,
    private val authRepository: AuthRepository? = null,
    private val userRepository: UserRepository? = null,
    private val materialRepository: MaterialRepository? = null,
    private val exerciseRepository: ExerciseRepository? = null,
    private val introRepository: IntroRepository? = null,
    private val studentRepository: StudentRepository? = null,
    private val articleRepository: ArticleRepository? = null
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CheckUsersViewModel::class.java)) {
            CheckUsersViewModel(userRepository!!) as T
        } else if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            AuthViewModel(authRepository!!) as T
        } else if (modelClass.isAssignableFrom(CheckUsersViewModel::class.java)) {
            return CheckUsersViewModel(userRepository!!) as T
        } else if (modelClass.isAssignableFrom(UserInfoViewModel::class.java)) {
            return UserInfoViewModel(id!!, userRepository!!) as T
        } else if (modelClass.isAssignableFrom(DetailLatihanViewModel::class.java)) {
            return DetailLatihanViewModel(id!!, exerciseRepository!!) as T
        } else if (modelClass.isAssignableFrom(GuruLatihanViewModel::class.java)) {
            return GuruLatihanViewModel(exerciseRepository!!) as T
        } else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(userRepository!!) as T
        } else if (modelClass.isAssignableFrom(GuruDashboardViewModel::class.java)) {
            return GuruDashboardViewModel(id!!, userRepository!!) as T
        } else if (modelClass.isAssignableFrom(AddMateriViewModel::class.java)) {
            return AddMateriViewModel(id, materialRepository!!) as T
        } else if (modelClass.isAssignableFrom(DetailMateriViewModel::class.java)) {
            return DetailMateriViewModel(id!!, materialRepository!!) as T
        } else if (modelClass.isAssignableFrom(GuruMateriViewModel::class.java)) {
            return GuruMateriViewModel(materialRepository!!) as T
        } else if (modelClass.isAssignableFrom(FileInfoViewModel::class.java)) {
            return FileInfoViewModel(id!!, str!!, materialRepository!!, exerciseRepository!!, articleRepository!!) as T
        } else if (modelClass.isAssignableFrom(CheckFileViewModel::class.java)) {
            return CheckFileViewModel(userRepository!!) as T
        } else if (modelClass.isAssignableFrom(IntroContentViewModel::class.java)) {
            return IntroContentViewModel(introRepository!!) as T
        } else if (modelClass.isAssignableFrom(IntroductionViewModel::class.java)) {
            return IntroductionViewModel(introRepository!!) as T
        } else if (modelClass.isAssignableFrom(UpdateIntroViewModel::class.java)) {
            return UpdateIntroViewModel(introRepository!!) as T
        } else if (modelClass.isAssignableFrom(MuridListLatihanViewModel::class.java)) {
            return MuridListLatihanViewModel(studentRepository!!) as T
        } else if (modelClass.isAssignableFrom(MuridDetailLatihanViewModel::class.java)) {
            return MuridDetailLatihanViewModel(id!!, studentRepository!!) as T
        } else if (modelClass.isAssignableFrom(CekJawabanViewModel::class.java)) {
            return CekJawabanViewModel(id!!, studentRepository!!) as T
        } else if (modelClass.isAssignableFrom(NilaiViewModel::class.java)) {
            return NilaiViewModel(studentRepository!!) as T
        } else if (modelClass.isAssignableFrom(MuridMateriViewModel::class.java)) {
            return MuridMateriViewModel(studentRepository!!) as T
        } else if (modelClass.isAssignableFrom(MuridDetailMateriViewModel::class.java)) {
            return MuridDetailMateriViewModel(id!!, materialRepository!!) as T
        } else if (modelClass.isAssignableFrom(AddLatihanViewModel::class.java)) {
            return AddLatihanViewModel(id, exerciseRepository!!) as T
        } else if (modelClass.isAssignableFrom(AddSoalViewModel::class.java)) {
            return AddSoalViewModel(id!!, exerciseRepository!!) as T
        } else if (modelClass.isAssignableFrom(AddContohReaksiViewModel::class.java)) {
            return AddContohReaksiViewModel(id, articleRepository!!) as T
        } else if (modelClass.isAssignableFrom(DetailContohReaksiViewModel::class.java)) {
            return DetailContohReaksiViewModel(id!!, articleRepository!!) as T
        } else if (modelClass.isAssignableFrom(GuruContohReaksiViewModel::class.java)) {
            return GuruContohReaksiViewModel(articleRepository!!) as T
        } else if (modelClass.isAssignableFrom(ContohReaksiScreenViewModel::class.java)) {
            return ContohReaksiScreenViewModel(studentRepository!!) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}