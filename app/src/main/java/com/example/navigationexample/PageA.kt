package com.example.navigationexample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PageA(userName : String,userAge:Int,userHeight:Float,isSingle:Boolean){
    Column(modifier = Modifier.fillMaxSize().padding(50.dp), horizontalAlignment = Alignment.CenterHorizontally){
        Text("Kullanıcı Adı : $userName")
        Text(text = "Kullnıcı Yaşı : $userAge")
        Text(text = "Kullanıcı Boyu : $userHeight")
        Text(text = "Kullanıcı Bekar mı? : $isSingle")
    }
}

