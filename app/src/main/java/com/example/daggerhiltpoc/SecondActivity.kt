package com.example.daggerhiltpoc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import com.example.daggerhiltpoc.model.UsersItem
import com.example.daggerhiltpoc.ui.theme.DaggerHiltPOCTheme

class SecondActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DaggerHiltPOCTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                        .wrapContentSize(align = Alignment.Center),
                    color = MaterialTheme.colors.background
                ) {
                    val data = intent.extras?.getParcelable<UsersItem>("userDetail")?.name
                    Greeting("Android $data")
                }
            }
        }
    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!", fontSize = TextUnit(18.0f,TextUnitType.Sp), fontFamily = FontFamily.Cursive)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DaggerHiltPOCTheme {
        Greeting("Android")
    }
}