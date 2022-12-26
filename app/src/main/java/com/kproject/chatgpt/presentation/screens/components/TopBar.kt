package com.kproject.chatgpt.presentation.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kproject.chatgpt.R
import com.kproject.chatgpt.presentation.theme.PreviewTheme
import com.kproject.chatgpt.presentation.theme.SimplePreview

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: String,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                color = MaterialTheme.colors.onSurface,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        },
        backgroundColor = MaterialTheme.colors.primary,
        navigationIcon = navigationIcon,
        actions = actions,
        elevation = 0.dp,
        modifier = modifier
    )
}

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = title,
        backgroundColor = MaterialTheme.colors.primary,
        navigationIcon = navigationIcon,
        actions = actions,
        elevation = 0.dp,
        modifier = modifier
    )
}

@SimplePreview
@Composable
private fun Preview() {
    PreviewTheme {
        Column {
            TopBar(
                title = stringResource(id = R.string.app_name),
            )
            Spacer(Modifier.height(24.dp))
            TopBar(
                title = stringResource(id = R.string.app_name),
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = null,
                            tint = MaterialTheme.colors.onSurface
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null,
                            tint = MaterialTheme.colors.onSurface
                        )
                    }
                }
            )
        }
    }
}