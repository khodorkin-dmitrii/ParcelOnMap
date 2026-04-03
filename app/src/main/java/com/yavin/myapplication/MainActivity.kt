package com.yavin.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.yavin.myapplication.data.repository.MockParcelRepository
import com.yavin.myapplication.ui.ParcelOnMapApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ParcelOnMapApp(repository = MockParcelRepository())
        }
    }
}
