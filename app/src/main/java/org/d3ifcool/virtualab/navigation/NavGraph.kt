package org.d3ifcool.virtualab.navigation

import UserRepository
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.d3ifcool.virtualab.data.network.ApiService
import org.d3ifcool.virtualab.data.network.apis.AuthorizedLatihanApi
import org.d3ifcool.virtualab.data.network.apis.AuthorizedMateriApi
import org.d3ifcool.virtualab.data.network.apis.AuthorizedUserApi
import org.d3ifcool.virtualab.repository.AuthRepository
import org.d3ifcool.virtualab.repository.ExerciseRepository
import org.d3ifcool.virtualab.repository.MaterialRepository
import org.d3ifcool.virtualab.ui.screen.AboutScreen
import org.d3ifcool.virtualab.ui.screen.AuthViewModel
import org.d3ifcool.virtualab.ui.screen.admin.dashboard.AdminDashboardScreen
import org.d3ifcool.virtualab.ui.screen.admin.approval.content.CheckFileScreen
import org.d3ifcool.virtualab.ui.screen.admin.approval.account.CheckUserScreen
import org.d3ifcool.virtualab.ui.screen.admin.approval.content.FileInfoScreen
import org.d3ifcool.virtualab.ui.screen.admin.introduction.ManageContentScreen
import org.d3ifcool.virtualab.ui.screen.admin.introduction.UpdateIntroContentScreen
import org.d3ifcool.virtualab.ui.screen.admin.approval.account.UsersInfoScreen
import org.d3ifcool.virtualab.ui.screen.murid.dashboard.MuridDashboardScreen
import org.d3ifcool.virtualab.ui.screen.murid.introduction.IntroductionScreen
import org.d3ifcool.virtualab.ui.screen.LandingScreen
import org.d3ifcool.virtualab.ui.screen.murid.latihan.MuridLatihanScreen
import org.d3ifcool.virtualab.ui.screen.RegisterScreen
import org.d3ifcool.virtualab.ui.screen.LoginScreen
import org.d3ifcool.virtualab.ui.screen.guru.dashboard.GuruDashboardScreen
import org.d3ifcool.virtualab.ui.screen.guru.latihan.AddLatihanScreen
import org.d3ifcool.virtualab.ui.screen.guru.latihan.AddSoalScreen
import org.d3ifcool.virtualab.ui.screen.guru.latihan.DetailLatihanScreen
import org.d3ifcool.virtualab.ui.screen.guru.latihan.GuruLatihanScreen
import org.d3ifcool.virtualab.ui.screen.guru.materi.AddMateriScreen
import org.d3ifcool.virtualab.ui.screen.guru.materi.DetailMateriScreen
import org.d3ifcool.virtualab.ui.screen.guru.materi.GuruMateriScreen
import org.d3ifcool.virtualab.ui.screen.murid.latihan.CekJawabanScreen
import org.d3ifcool.virtualab.ui.screen.murid.latihan.MuridDetailLatihanScreen
import org.d3ifcool.virtualab.ui.screen.murid.materi.MuridDetailMateriScreen
import org.d3ifcool.virtualab.ui.screen.murid.materi.MuridMateriScreen
import org.d3ifcool.virtualab.ui.screen.murid.nilai.NilaiScreen
import org.d3ifcool.virtualab.ui.screen.ProfileScreen
import org.d3ifcool.virtualab.ui.screen.ProfileViewModel
import org.d3ifcool.virtualab.ui.screen.murid.reaksi.ReaksiScreen
import org.d3ifcool.virtualab.ui.screen.RoleScreen
import org.d3ifcool.virtualab.ui.screen.TermsConditionScreen
import org.d3ifcool.virtualab.ui.screen.admin.approval.account.CheckUsersViewModel
import org.d3ifcool.virtualab.ui.screen.admin.approval.account.UserInfoViewModel
import org.d3ifcool.virtualab.ui.screen.admin.approval.content.CheckFileViewModel
import org.d3ifcool.virtualab.ui.screen.admin.approval.content.FileInfoViewModel
import org.d3ifcool.virtualab.ui.screen.guru.dashboard.GuruDashboardViewModel
import org.d3ifcool.virtualab.ui.screen.guru.latihan.LatihanListViewModel
import org.d3ifcool.virtualab.ui.screen.guru.materi.DetailMateriViewModel
import org.d3ifcool.virtualab.ui.screen.guru.materi.GuruMateriViewModel
import org.d3ifcool.virtualab.utils.UserDataStore
import org.d3ifcool.virtualab.utils.ViewModelFactory

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    val userDataStore = UserDataStore(LocalContext.current)
    val userType by userDataStore.userTypeFlow.collectAsState(-1)
    val accessToken by userDataStore.accessTokenFlow.collectAsState("")
    val id by userDataStore.userIdFlow.collectAsState(-1)

    val unauthedApi = ApiService.unauthedService
    val userApi: AuthorizedUserApi?
    var latihanApi: AuthorizedLatihanApi?
    var materiApi: AuthorizedMateriApi?
    val authRepository = AuthRepository(userDataStore, unauthedApi)
    var userRepository: UserRepository? = null
    var exerciseRepository: ExerciseRepository? = null
    var materialRepository: MaterialRepository? = null

    if (accessToken != "") {
        ApiService.createAuthorizedService(accessToken)
        userApi = ApiService.userService!!
        latihanApi = ApiService.latihanService!!
        materiApi = ApiService.materiService!!
        userRepository = UserRepository(userDataStore, userApi)
        exerciseRepository = ExerciseRepository(latihanApi)
        materialRepository = MaterialRepository(materiApi)
    }
    val isLoggedIn by userDataStore.loginStatusFlow.collectAsState(false)


    Log.d("NavGraph", "userType: $userType")
    Log.d("NavGraph", "is LoggedIn: $isLoggedIn")
    Log.d("NavGraph", "accessToken: $accessToken")
    NavHost(
        navController = navController, startDestination =
        if (isLoggedIn) {
            when (userType) {
                0 -> Screen.MuridDashboard.route
                1 -> Screen.GuruDashboard.route
                else -> Screen.AdminDashboard.route
            }
        } else {
            Screen.Landing.route
        }
    ) {
        composable(route = Screen.Landing.route) {
            LandingScreen(navController)
        }
        composable(route = Screen.About.route) {
            AboutScreen(navController)
        }
        composable(route = Screen.Profile.route) {
            val profileFactory = ViewModelFactory(userRepository = userRepository)
            val authFactory = ViewModelFactory(authRepository = authRepository)

            val profileViewModel: ProfileViewModel = viewModel(factory = profileFactory)
            val authViewModel: AuthViewModel = viewModel(factory = authFactory)

            ProfileScreen(navController, profileViewModel, authViewModel, userDataStore)
        }
        composable(route = Screen.TermsCondition.route) {
            TermsConditionScreen(navController)
        }

        //  Auth
        composable(route = Screen.Role.route) {
            RoleScreen(navController)
        }
        composable(route = Screen.Register.route,
            arguments = listOf(
                navArgument(KEY_USER_TYPE) {
                    type = NavType.IntType
                }
            )) {
            val roleId = it.arguments!!.getInt(KEY_USER_TYPE)
            val factory = ViewModelFactory(authRepository = authRepository)
            val viewModel: AuthViewModel = viewModel(factory = factory)
            RegisterScreen(navController, roleId, viewModel)
        }
        composable(route = Screen.Login.route) {
            val factory = ViewModelFactory(authRepository = authRepository)
            val viewModel: AuthViewModel = viewModel(factory = factory)
            LoginScreen(navController, viewModel)
        }

        //  Murid
        composable(route = Screen.MuridDashboard.route) {
            MuridDashboardScreen(navController)
        }
        composable(route = Screen.Introduction.route) {
            IntroductionScreen(navController)
        }
        composable(route = Screen.MuridMateri.route) {
            MuridMateriScreen(navController)
        }
        composable(route = Screen.MuridDetailMateri.route) {
            MuridDetailMateriScreen(navController)
        }
        composable(route = Screen.MuridLatihan.route) {
            MuridLatihanScreen(navController)
        }
        composable(route = Screen.MuridDetailLatihan.route) {
            MuridDetailLatihanScreen(navController)
        }
        composable(route = Screen.Reaksi.route) {
            ReaksiScreen(navController)
        }
        composable(route = Screen.Nilai.route) {
            NilaiScreen(navController)
        }
        composable(route = Screen.CekJawaban.route) {
            CekJawabanScreen(navController)
        }

        //  Guru
        composable(route = Screen.GuruDashboard.route) {
            val factory = ViewModelFactory(userRepository = userRepository, id = id)
            val viewModel: GuruDashboardViewModel = viewModel(factory = factory)
            GuruDashboardScreen(navController, viewModel)
        }
        composable(route = Screen.AddMateri.route) {
            AddMateriScreen(navController)
        }
        composable(route = Screen.AddLatihan.route) {
            AddLatihanScreen(navController)
        }
        composable(route = Screen.GuruMateri.route) {
            val factory = ViewModelFactory(materialRepository = materialRepository)
            val viewModel: GuruMateriViewModel = viewModel(factory = factory)
            GuruMateriScreen(navController, viewModel)
        }
        composable(route = Screen.GuruLatihan.route) {
            val factory = ViewModelFactory(exerciseRepository = exerciseRepository)
            val viewModel: LatihanListViewModel = viewModel(factory = factory)
            GuruLatihanScreen(navController, viewModel)
        }
        composable(route = Screen.GuruDetailMateri.route,
            arguments = listOf(
                navArgument(KEY_ID_TYPE) {
                    type = NavType.IntType
                }
            )
        ) {
            val materiId = it.arguments!!.getInt(KEY_ID_TYPE)
            Log.d("NavGraph", "material ID: $materiId")
            val factory = ViewModelFactory(id = materiId, materialRepository = materialRepository)
            val viewModel: DetailMateriViewModel = viewModel(factory = factory)
            DetailMateriScreen(navController, viewModel)
        }
        composable(route = Screen.GuruDetailLatihan.route,
            arguments = listOf(
                navArgument(KEY_EXERCISE_ID) {
                    type = NavType.IntType
                }
            )) {
            val exerciseId = it.arguments!!.getInt(KEY_EXERCISE_ID)
            DetailLatihanScreen(navController, exerciseId)
        }

        // Admin
        composable(route = Screen.AdminDashboard.route) {
            AdminDashboardScreen(navController)
        }
        composable(route = Screen.CheckUser.route) {
            val factory = ViewModelFactory(userRepository = userRepository)
            val viewModel: CheckUsersViewModel = viewModel(factory = factory)
            CheckUserScreen(navController, viewModel)
        }
        composable(route = Screen.CheckFile.route) {
            val factory = ViewModelFactory(userRepository = userRepository)
            val viewModel: CheckFileViewModel = viewModel(factory = factory)
            CheckFileScreen(navController, viewModel)
        }
        composable(route = Screen.UsersInfo.route,
            arguments = listOf(
                navArgument(KEY_USER_ID) {
                    type = NavType.IntType
                }
            )) {
            val userId = it.arguments!!.getInt(KEY_USER_ID)
            val factory = ViewModelFactory(id = userId, userRepository = userRepository)
            val viewModel: UserInfoViewModel = viewModel(factory = factory)
            UsersInfoScreen(navController, viewModel)
        }
        composable(route = Screen.FileInfo.route,
            arguments = listOf(
                navArgument(KEY_ID_TYPE) {
                    type = NavType.IntType
                },
                navArgument(KEY_STR_TYPE) {
                    type = NavType.StringType
                }
            )) {
            val postId = it.arguments!!.getInt(KEY_ID_TYPE)
            val postType = it.arguments!!.getString(KEY_STR_TYPE)
            val factory = ViewModelFactory(
                id = postId,
                str = postType,
                materialRepository = materialRepository,
                exerciseRepository = exerciseRepository
            )
            val viewModel: FileInfoViewModel = viewModel(factory = factory)
            FileInfoScreen(navController, postType!!, viewModel)
        }
        composable(route = Screen.ManageIntroContent.route) {
            ManageContentScreen(navController)
        }
        composable(route = Screen.UpdateIntroContent.route) {
            UpdateIntroContentScreen(navController)
        }
        composable(route = Screen.AddSoal.route,
            arguments = listOf(
                navArgument(KEY_EXERCISE_ID) {
                    type = NavType.IntType
                }
            )) {
            val exerciseId = it.arguments!!.getInt(KEY_EXERCISE_ID)
            AddSoalScreen(navController, exerciseId = exerciseId)
        }
    }
}