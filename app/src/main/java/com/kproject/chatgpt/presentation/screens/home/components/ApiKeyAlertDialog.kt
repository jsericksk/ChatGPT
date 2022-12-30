package com.kproject.chatgpt.presentation.screens.home.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.kproject.chatgpt.R
import com.kproject.chatgpt.presentation.screens.components.AlertDialogWithTextField
import com.kproject.chatgpt.presentation.theme.CompletePreview
import com.kproject.chatgpt.presentation.theme.PreviewTheme

@Composable
fun ApiKeyAlertDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    apiKey: String,
    onSaveApiKey: (String) -> Unit
) {
    var currentApiKey by rememberSaveable { mutableStateOf(apiKey) }

    AlertDialogWithTextField(
        showDialog = showDialog,
        onDismiss = {
            currentApiKey = apiKey
            onDismiss.invoke()
        },
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