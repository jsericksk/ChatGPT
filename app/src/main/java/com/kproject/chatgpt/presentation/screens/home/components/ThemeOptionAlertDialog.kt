package com.kproject.chatgpt.presentation.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kproject.chatgpt.R
import com.kproject.chatgpt.presentation.screens.components.DialogActionButtons
import com.kproject.chatgpt.presentation.screens.components.DialogTitle
import com.kproject.chatgpt.presentation.theme.CompletePreview
import com.kproject.chatgpt.presentation.theme.PreviewTheme
import com.kproject.chatgpt.presentation.theme.custom.ThemeOptions

@Composable
fun ThemeOptionAlertDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    currentSelectedTheme: Int,
    onThemeSelected: (Int) -> Unit
) {
    if (showDialog) {
        var selectedTheme by rememberSaveable { mutableStateOf(currentSelectedTheme) }

        val defaultDialogPadding = 24.dp
        Dialog(
            onDismissRequest = onDismiss,
            content = {
                Column(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colors.background,
                            shape = RoundedCornerShape(defaultDialogPadding)
                        )
                        .padding(
                            start = defaultDialogPadding,
                            end = defaultDialogPadding,
                            top = defaultDialogPadding,
                            bottom = 14.dp
                        )
                ) {
                    // Title
                    DialogTitle(title = stringResource(id = R.string.app_theme))

                    Spacer(Modifier.height(22.dp))

                    // Content
                    ThemeOptionsList(
                        currentSelectedTheme = selectedTheme,
                        onOptionSelected = { option ->
                            selectedTheme = option
                        }
                    )

                    Spacer(Modifier.height(20.dp))

                    DialogActionButtons(
                        onDismiss = onDismiss,
                        okButtonEnabled = true,
                        showButtonCancel = true,
                        okButtonTitle = stringResource(id = R.string.button_save),
                        cancelButtonTitle = stringResource(id = R.string.button_cancel),
                        onClickButtonOk = {
                            onThemeSelected.invoke(selectedTheme)
                        },
                        onClickButtonCancel = onDismiss
                    )
                }
            }
        )
    }
}

@Composable
private fun ThemeOptionsList(
    currentSelectedTheme: Int,
    onOptionSelected: (Int) -> Unit
) {
    val themeOptions = ThemeOptions.darkThemeOptions
    LazyVerticalGrid(
        columns = GridCells.Fixed(3)
    ) {
        itemsIndexed(items = themeOptions) { index, themeOption ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(8.dp)
                    .clip(CircleShape)
                    .clickable {
                        onOptionSelected.invoke(index)
                    }
                    .padding(4.dp)
                    .aspectRatio(1f)
                    .background(themeOption.primary, CircleShape),
            ) {
                if (index == currentSelectedTheme) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_done),
                        contentDescription = null,
                        tint = MaterialTheme.colors.onSurface,
                        modifier = Modifier.wrapContentSize()
                    )
                }
            }
        }
    }
}

@CompletePreview
@Composable
private fun Preview() {
    PreviewTheme {
        ThemeOptionAlertDialog(
            showDialog = true,
            onDismiss = {},
            currentSelectedTheme = ThemeOptions.Option1,
            onThemeSelected = {}
        )
    }
}