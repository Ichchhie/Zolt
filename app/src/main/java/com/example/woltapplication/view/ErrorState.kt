package com.example.woltapplication.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.woltapplication.R

@Composable
fun ErrorState(modifier: Modifier = Modifier, message: String, errorImage : Int) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.semantics(mergeDescendants = true) {}) {
            Image(
                painter = painterResource(id = errorImage),
                contentDescription = null
            )
            AddVerticalSpace(height = 16.dp)
            Text(
                text = message,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(24.dp),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}