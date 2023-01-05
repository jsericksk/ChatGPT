package com.kproject.chatgpt.presentation.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kproject.chatgpt.R
import com.kproject.chatgpt.presentation.theme.CompletePreview
import com.kproject.chatgpt.presentation.theme.PreviewTheme

private val defaultShapeSize = 16.dp

@Composable
fun SimpleAlertDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    showTitle: Boolean = true,
    title: String = "",
    message: String,
    cancelable: Boolean = true,
    okButtonEnabled: Boolean = true,
    showButtonCancel: Boolean = true,
    okButtonTitle: String = stringResource(id = R.string.button_ok),
    cancelButtonTitle: String = stringResource(id = R.string.button_cancel),
    shape: Shape = RoundedCornerShape(defaultShapeSize),
    onClickButtonOk: () -> Unit,
    onClickButtonCancel: () -> Unit = {}
) {
    CustomAlertDialog(
        showDialog = showDialog,
        onDismiss = onDismiss,
        title = title,
        showTitle = showTitle,
        cancelable = cancelable,
        okButtonEnabled = okButtonEnabled,
        showButtonCancel = showButtonCancel,
        okButtonTitle = okButtonTitle,
        cancelButtonTitle = cancelButtonTitle,
        shape = shape,
        onClickButtonOk = onClickButtonOk,
        onClickButtonCancel = onClickButtonCancel
    ) {
        Text(
            text = message,
            fontSize = 16.sp,
            color = MaterialTheme.colors.onPrimary
        )
    }
}

@Composable
fun TextFieldAlertDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    title: String,
    textFieldValue: String,
    onTextValueChange: (String) -> Unit,
    textFieldPlaceholder: String,
    maxLines: Int = 1,
    textFieldShape: Shape = RoundedCornerShape(12.dp),
    cancelable: Boolean = true,
    okButtonEnabled: Boolean = true,
    showButtonCancel: Boolean = true,
    okButtonTitle: String = stringResource(id = R.string.button_ok),
    cancelButtonTitle: String = stringResource(id = R.string.button_cancel),
    shape: Shape = RoundedCornerShape(defaultShapeSize),
    onClickButtonOk: () -> Unit,
    onClickButtonCancel: () -> Unit = {}
) {
    if (showDialog) {
        CustomAlertDialog(
            showDialog = showDialog,
            onDismiss = onDismiss,
            title = title,
            cancelable = cancelable,
            okButtonEnabled = okButtonEnabled,
            showButtonCancel = showButtonCancel,
            okButtonTitle = okButtonTitle,
            cancelButtonTitle = cancelButtonTitle,
            shape = shape,
            onClickButtonOk = onClickButtonOk,
            onClickButtonCancel = onClickButtonCancel
        ) {
            // Content
            TextField(
                value = textFieldValue,
                onValueChange = { value ->
                    onTextValueChange.invoke(value)
                },
                textStyle = TextStyle(
                    color = MaterialTheme.colors.onPrimary,
                    fontSize = 18.sp
                ),
                placeholder = {
                    Text(
                        text = textFieldPlaceholder,
                        color = MaterialTheme.colors.onPrimary.copy(alpha = 0.4f)
                    )
                },
                maxLines = maxLines,
                shape = textFieldShape,
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = MaterialTheme.colors.onPrimary,
                    backgroundColor = MaterialTheme.colors.onSecondary,
                    leadingIconColor = MaterialTheme.colors.onSurface,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun CustomAlertDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    showTitle: Boolean = true,
    title: String = "",
    cancelable: Boolean = true,
    okButtonEnabled: Boolean = true,
    showButtonCancel: Boolean = true,
    okButtonTitle: String = stringResource(id = R.string.button_ok),
    cancelButtonTitle: String = stringResource(id = R.string.button_cancel),
    shape: Shape = RoundedCornerShape(defaultShapeSize),
    onClickButtonOk: () -> Unit,
    onClickButtonCancel: () -> Unit = {},
    content: @Composable () -> Unit
) {
    if (showDialog) {
        val defaultDialogPadding = 24.dp
        Dialog(
            onDismissRequest = { if (cancelable) onDismiss.invoke() },
            content = {
                Column(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colors.background,
                            shape = shape
                        )
                        .padding(
                            start = defaultDialogPadding,
                            end = defaultDialogPadding,
                            top = defaultDialogPadding,
                            bottom = 14.dp
                        )
                ) {
                    // Title
                    if (showTitle) {
                        DialogTitle(title = title)
                        Spacer(Modifier.height(22.dp))
                    }

                    // Content
                    Box(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .weight(1f, fill = false)
                    ) {
                        content.invoke()
                    }

                    Spacer(Modifier.height(20.dp))

                    DialogActionButtons(
                        onDismiss = onDismiss,
                        okButtonEnabled = okButtonEnabled,
                        showButtonCancel = showButtonCancel,
                        okButtonTitle = okButtonTitle,
                        cancelButtonTitle = cancelButtonTitle,
                        onClickButtonOk = onClickButtonOk,
                        onClickButtonCancel = onClickButtonCancel
                    )
                }
            }
        )
    }
}

@Composable
fun ColumnScope.DialogTitle(title: String) {
    Text(
        text = title,
        fontSize = 18.sp,
        color = MaterialTheme.colors.onPrimary,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.align(Alignment.CenterHorizontally)
    )
}

@Composable
fun ColumnScope.DialogActionButtons(
    onDismiss: () -> Unit,
    okButtonEnabled: Boolean,
    showButtonCancel: Boolean,
    okButtonTitle: String,
    cancelButtonTitle: String,
    onClickButtonOk: () -> Unit,
    onClickButtonCancel: () -> Unit
) {
    Row(
        modifier = Modifier.align(Alignment.End)
    ) {
        if (showButtonCancel) {
            TextButton(
                onClick = {
                    onDismiss.invoke()
                    onClickButtonCancel.invoke()
                }
            ) {
                Text(
                    text = cancelButtonTitle.uppercase(),
                    color = MaterialTheme.colors.secondary
                )
            }
            Spacer(Modifier.width(6.dp))
        }
        TextButton(
            onClick = {
                onDismiss.invoke()
                onClickButtonOk.invoke()
            },
            enabled = okButtonEnabled
        ) {
            Text(
                text = okButtonTitle.uppercase(),
                color = if (okButtonEnabled) {
                    MaterialTheme.colors.secondary
                } else {
                    MaterialTheme.colors.secondary.copy(alpha = 0.5f)
                }
            )
        }
    }
}

@CompletePreview
@Composable
private fun AlertDialogWithTextFieldPreview() {
    PreviewTheme {
        TextFieldAlertDialog(
            showDialog = true,
            onDismiss = {},
            title = "Insert your name",
            textFieldValue = "John",
            textFieldPlaceholder = "",
            onTextValueChange = {},
            onClickButtonOk = {}
        )
    }
}