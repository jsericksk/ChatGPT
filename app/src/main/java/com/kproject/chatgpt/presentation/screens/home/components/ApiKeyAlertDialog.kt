package com.kproject.chatgpt.presentation.screens.home.components

import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.kproject.chatgpt.R
import com.kproject.chatgpt.presentation.screens.components.AlertDialogWithTextField
import com.kproject.chatgpt.presentation.theme.CompletePreview
import com.kproject.chatgpt.presentation.theme.PreviewTheme

// TODO: Corrigir bug da apiKey não aparecendo na primeira exibição da HomeScreen
@Composable
fun ApiKeyAlertDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    apiKey: String,
    onSaveApiKey: (String) -> Unit
) {
    var currentApiKey by remember { mutableStateOf(apiKey) }

    AlertDialogWithTextField(
        showDialog = showDialog,
        onDismiss = onDismiss,
        title = stringResource(id = R.string.api_key),
        textFieldValue = currentApiKey,
        textFieldPlaceholder = stringResource(id = R.string.insert_api_key),
        okButtonEnabled = currentApiKey.isNotBlank(),
        okButtonTitle = stringResource(id = R.string.button_save),
        onTextValueChange = {
            currentApiKey = it
        },
        onClickButtonOk = {
            onSaveApiKey.invoke(currentApiKey)
        }
    )
}

@CompletePreview
@Composable
private fun Preview() {
    PreviewTheme {
       ApiKeyAlertDialog(
           showDialog = true,
           onDismiss = {},
           apiKey = "sdjfjfewow",
           onSaveApiKey = {}
       )
    }
}