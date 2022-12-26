package com.kproject.chatgpt.presentation.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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

@Composable
fun ApiKeyAlertDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    shape: Shape = RoundedCornerShape(14.dp),
    apiKey: String,
    onApiKeyChange: (String) -> Unit
) {
    if (showDialog) {
        var currentApiKey by rememberSaveable { mutableStateOf(apiKey) }

        Dialog(
            onDismissRequest = onDismiss,
            content = {
                Column(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colors.background,
                            shape = shape
                        )
                        .padding(18.dp)
                ) {
                    // Title
                    Text(
                        text = stringResource(id = R.string.api_key),
                        fontSize = 18.sp,
                        color = MaterialTheme.colors.onPrimary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(Modifier.height(16.dp))

                    // Content
                    TextField(
                        value = currentApiKey,
                        onValueChange = { value ->
                            currentApiKey = value
                        },
                        textStyle = TextStyle(
                            color = MaterialTheme.colors.onPrimary,
                            fontSize = 18.sp
                        ),
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.insert_api_key),
                                color = MaterialTheme.colors.onPrimary.copy(alpha = 0.4f)
                            )
                        },
                        maxLines = 1,
                        shape = RoundedCornerShape(12.dp),
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

                    Spacer(Modifier.height(16.dp))

                    // Action buttons
                    val saveButtonIsEnabled = currentApiKey.isNotBlank()
                    Row(
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        TextButton(onClick = onDismiss) {
                            Text(
                                text = stringResource(id = R.string.button_cancel).uppercase(),
                                color = MaterialTheme.colors.secondary
                            )
                        }
                        Spacer(Modifier.width(6.dp))
                        TextButton(
                            onClick = {
                                onDismiss.invoke()
                                onApiKeyChange.invoke(currentApiKey)
                            },
                            enabled = saveButtonIsEnabled
                        ) {
                            Text(
                                text = stringResource(id = R.string.button_save).uppercase(),
                                color = if (saveButtonIsEnabled) {
                                    MaterialTheme.colors.secondary
                                } else {
                                    MaterialTheme.colors.secondary.copy(alpha = 0.5f)
                                }
                            )
                        }
                    }
                }
            }
        )
    }
}