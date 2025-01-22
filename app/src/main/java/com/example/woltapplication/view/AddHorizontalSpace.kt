package com.example.woltapplication.view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun AddHorizontalSpace(width: Dp) {
    Spacer(modifier = Modifier.width(width))
}