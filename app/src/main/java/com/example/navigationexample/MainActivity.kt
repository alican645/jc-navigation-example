package com.example.navigationexample

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.navigationexample.ui.theme.NavigationExampleTheme
import com.example.navigationexample.ui.theme.Purple80
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationExampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), containerColor = Purple80) {
                    PageNav()
                }
            }
        }
    }
}

@Composable
fun PageNav(){
    val navController = rememberNavController()
    NavHost(navController=navController,startDestination="homePage"){
            composable("homePage"){
                HomePage(navController = navController)
            }

            composable("pageA/{userName}/{userAge}/{userHeight}/{isSingle}",
                arguments = listOf(
                    navArgument("userName"){type=NavType.StringType},
                    navArgument("userAge"){type=NavType.IntType},
                    navArgument("userHeight"){type=NavType.FloatType},
                    navArgument("isSingle"){type=NavType.BoolType}
                )
            ){
                val userName= it.arguments?.getString("userName")!!
                val userAge= it.arguments?.getInt("userAge")!!
                val userHeight= it.arguments?.getFloat("userHeight")!!
                val isSingle= it.arguments?.getBoolean("isSingle")!!
              PageA(userName = userName, userAge = userAge, userHeight = userHeight, isSingle = isSingle)
            }

            composable("pageB/{object}", arguments = listOf(
                navArgument("object"){type=NavType.StringType}
            )) {
                val objectJson = it.arguments?.getString("object")!!
                val theObject = Gson().fromJson(objectJson,UserModel::class.java)
                PageB(userModel = theObject)
            }

    }
}


@Composable
fun HomePage(modifier: Modifier = Modifier,navController: NavController) {
    var userNameVG = remember { mutableStateOf("") }
    var userAgeVG = remember { mutableStateOf("") } // Yaş için string olarak başlatılır
    var userHeightVG = remember { mutableStateOf("") } // Boy için string olarak başlatılır
    var isSingleVG = remember { mutableStateOf(false) }
    var userNameNG = remember { mutableStateOf("") }
    var userAgeNG = remember { mutableStateOf("") } // Yaş için string olarak başlatılır
    var userHeightNG = remember { mutableStateOf("") } // Boy için string olarak başlatılır
    var isSingleNG = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(vertical = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        veriGonderme(userNameVG, userAgeVG, userHeightVG, isSingleVG, navController)

        Spacer(Modifier.height(50.dp))

        nesneGonderme(userNameNG, userAgeNG, userHeightNG, isSingleNG, navController)


    }
}

@Composable
private fun nesneGonderme(
    userNameNG: MutableState<String>,
    userAgeNG: MutableState<String>,
    userHeightNG: MutableState<String>,
    isSingleNG: MutableState<Boolean>,
    navController: NavController
) {
    Text("Nesne Gönderme")


    // Kullanıcı Adı
    TextField(
        value = userNameNG.value,
        onValueChange = { userNameNG.value = it },
        label = { Text("Kullanıcı Adı") }
    )

    // Kullanıcı Yaşı
    TextField(
        value = userAgeNG.value,
        onValueChange = { userAgeNG.value = it },
        label = { Text("Kullanıcı Yaşı") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )

    // Kullanıcı Boyu
    TextField(
        value = userHeightNG.value,
        onValueChange = { userHeightNG.value = it },
        label = { Text("Kullanıcı Boyu") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Kullanıcı Bekar mı?")
        Checkbox(
            checked = isSingleNG.value,
            onCheckedChange = { isSingleNG.value = it }
        )
    }

    Button(onClick = {

        val age = userAgeNG.value.trim().toIntOrNull()
        val height = userHeightNG.value.trim().replace(" ", "").toFloatOrNull()


        if (age != null && height != null) {
            val user = UserModel(
                userName = userNameNG.value,
                userAge = age,
                userHeight = height,
                isSingle = isSingleNG.value
            )
            val userJson = Gson().toJson(user)
            navController.navigate("pageB/${userJson}")
        } else {

            Log.e("Error", "Geçersiz yaş veya boy formatı")
        }
    }) {
        Text(text = "Nesne Gönderilen Sayfaya")
    }
}

@Composable
private fun veriGonderme(
    userName: MutableState<String>,
    userAge: MutableState<String>,
    userHeight: MutableState<String>,
    isSingle: MutableState<Boolean>,
    navController: NavController
) {
    Text("Veri Gönderme")

    // Kullanıcı Adı
    TextField(
        value = userName.value,
        onValueChange = { userName.value = it },
        label = { Text("Kullanıcı Adı") }
    )

    // Kullanıcı Yaşı
    TextField(
        value = userAge.value,
        onValueChange = { userAge.value = it },
        label = { Text("Kullanıcı Yaşı") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )

    // Kullanıcı Boyu
    TextField(
        value = userHeight.value,
        onValueChange = { userHeight.value = it },
        label = { Text("Kullanıcı Boyu") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text("Kullanıcı Bekar mı?")
        Checkbox(
            checked = isSingle.value,
            onCheckedChange = { isSingle.value = it }
        )
    }

    Button(onClick = {

        val age = userAge.value.trim().toIntOrNull()
        val height = userHeight.value.trim().replace(" ", "").toFloatOrNull()


        if (age != null && height != null) {
            navController.navigate("pageA/${userName.value}/$age/$height/${isSingle.value}")
        } else {

            Log.e("Error", "Geçersiz yaş veya boy formatı")
        }
    }) {
        Text(text = "Veri Gönderilen Sayfaya")
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NavigationExampleTheme {
        PageNav()
    }
}