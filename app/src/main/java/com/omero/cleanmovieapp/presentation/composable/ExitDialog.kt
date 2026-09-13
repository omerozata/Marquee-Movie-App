package com.omero.cleanmovieapp.presentation.composable

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

@Composable
fun ExitConfirmationHandler() {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    BackHandler { showDialog = true }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {showDialog = false},
            title = {Text("Çıkış")},
            text = {Text("Uygulamadan çıkmak istiyor musunuz?")},
            confirmButton = {
                TextButton(onClick = {(context as? Activity)?.finish() }) {
                    Text("Evet")
                }
            },
            dismissButton = {
                TextButton(onClick = {showDialog = false}) {
                    Text("Hayır")
                }
            }

        )
    }


}