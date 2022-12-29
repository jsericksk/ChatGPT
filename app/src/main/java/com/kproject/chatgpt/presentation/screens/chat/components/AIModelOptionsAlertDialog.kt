package com.kproject.chatgpt.presentation.screens.chat.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kproject.chatgpt.R
import com.kproject.chatgpt.presentation.model.AIModel
import com.kproject.chatgpt.presentation.model.AIModelOptions
import com.kproject.chatgpt.presentation.theme.PreviewTheme
import com.kproject.chatgpt.presentation.theme.SimplePreview

@Composable
fun AIModelOptionsAlertDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    aiModelOptions: AIModelOptions
) {
    if (showDialog) {
        var iaModel by rememberSaveable { mutableStateOf(aiModelOptions.iaModel) }
        var maxTokens by rememberSaveable { mutableStateOf(aiModelOptions.maxTokens.toFloat()) }
        var temperature by rememberSaveable { mutableStateOf(aiModelOptions.temperature) }

        Dialog(
            onDismissRequest = onDismiss,
            content = {
                Column(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colors.background,
                            shape = RoundedCornerShape(14.dp)
                        )
                        .padding(18.dp)
                ) {
                    // Title
                    Text(
                        text = stringResource(id = R.string.ia_model_options),
                        fontSize = 18.sp,
                        color = MaterialTheme.colors.onPrimary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(Modifier.height(16.dp))

                    // Content
                    Column {
                        IAModelOption(
                            iaModel = iaModel,
                            onIAModelOptionChanged = {
                                iaModel = it
                            }
                        )
                        Spacer(Modifier.height(10.dp))
                        MaxTokensOption(
                            maxTokens = maxTokens,
                            onMaxTokensValueChange = {
                                maxTokens = it
                            }
                        )
                        Spacer(Modifier.height(10.dp))
                        TemperatureOption(
                            temperature = temperature,
                            onTemperatureValueChange = {
                                temperature = it
                            }
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    // Action buttons
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
                            }
                        ) {
                            Text(
                                text = stringResource(id = R.string.button_save).uppercase(),
                                color = MaterialTheme.colors.secondary
                            )
                        }
                    }
                }
            }
        )
    }
}

@Composable
private fun IAModelOption(
    iaModel: AIModel,
    onIAModelOptionChanged: (AIModel) -> Unit
) {
    var showOptionsMenu by remember { mutableStateOf(false) }
    val modelDescriptionResId = if (iaModel == AIModel.TextDavinci003) {
        R.string.ia_model_option_text_davinci_003_description
    } else {
        R.string.ia_model_option_text_curie_001_description
    }

    val dropdownIcon = if (showOptionsMenu) {
        ImageVector.vectorResource(id = R.drawable.ic_arrow_drop_up)
    } else {
        ImageVector.vectorResource(id = R.drawable.ic_arrow_drop_down)
    }

    CardOptionItem {
        OptionTitle(
            textResId = R.string.ia_model_option_model,
            descriptionResId = modelDescriptionResId
        )
        Spacer(Modifier.height(6.dp))
        Box {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { showOptionsMenu = true }
                    .background(
                        color = MaterialTheme.colors.secondary,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(12.dp)
            ) {
                Text(
                    text = iaModel.name,
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 16.sp
                )
                Spacer(Modifier.width(8.dp))
                Icon(
                    imageVector = dropdownIcon,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onSurface
                )
            }
        }

        IAModelOptionCustomDropdownMenu(
            showDropdownMenu = showOptionsMenu,
            onDismiss = { showOptionsMenu = false },
            onOptionSelected = { option ->
                onIAModelOptionChanged.invoke(option)
            }
        )
    }
}

@Composable
private fun IAModelOptionCustomDropdownMenu(
    modifier: Modifier = Modifier,
    showDropdownMenu: Boolean,
    onDismiss: () -> Unit,
    onOptionSelected: (AIModel) -> Unit
) {
    val options = listOf(AIModel.TextDavinci003.name, AIModel.TextCurie001.name)

    DropdownMenu(
        expanded = showDropdownMenu,
        onDismissRequest = onDismiss,
        modifier = modifier
            .background(MaterialTheme.colors.surface)
    ) {
        options.forEachIndexed { index, title ->
            DropdownMenuItem(
                onClick = {
                    onDismiss.invoke()
                    val selectedIAModel = if (index == 0) {
                        AIModel.TextDavinci003
                    } else {
                        AIModel.TextCurie001
                    }
                    onOptionSelected.invoke(selectedIAModel)
                },
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier
                    .padding(4.dp)
                    .background(
                        color = MaterialTheme.colors.primary,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Text(
                    text = title,
                    color = MaterialTheme.colors.onSurface
                )
            }
        }
    }
}

@Composable
private fun MaxTokensOption(
    maxTokens: Float,
    onMaxTokensValueChange: (Float) -> Unit
) {
    CardOptionItem {
        OptionTitle(
            textResId = R.string.ia_model_option_max_tokens,
            descriptionResId = R.string.ia_model_option_max_tokens_description
        )

        Slider(
            value = maxTokens,
            onValueChange = {
                onMaxTokensValueChange.invoke(it)
            },
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colors.secondary,
                activeTrackColor = MaterialTheme.colors.onSecondary,
            ),
            valueRange = 7f..700f
        )

        Text(
            text = maxTokens.toInt().toString(),
            color = MaterialTheme.colors.onSurface,
            fontSize = 14.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
private fun TemperatureOption(
    temperature: Float,
    onTemperatureValueChange: (Float) -> Unit
) {
    CardOptionItem {
        OptionTitle(
            textResId = R.string.ia_model_option_temperature,
            descriptionResId = R.string.ia_model_option_temperature_description
        )

        Slider(
            value = temperature,
            onValueChange = {
                onTemperatureValueChange.invoke(it)
            },
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colors.secondary,
                activeTrackColor = MaterialTheme.colors.onSecondary,
            ),
            valueRange = 0f..1f
        )

        Text(
            text = temperature.toInt().toString(),
            color = MaterialTheme.colors.onSurface,
            fontSize = 14.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
private fun CardOptionItem(content: @Composable (ColumnScope.() -> Unit)) {
    Card(
        backgroundColor = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            content = content
        )
    }
}

@Composable
private fun OptionTitle(
    @StringRes textResId: Int,
    @StringRes descriptionResId: Int
) {
    var showInfoPopup by remember { mutableStateOf(false) }

    Box {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = textResId),
                color = MaterialTheme.colors.onSurface,
                fontSize = 16.sp
            )
            Spacer(Modifier.width(4.dp))
            IconButton(onClick = { showInfoPopup = true }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_info),
                    contentDescription = null,
                    tint = MaterialTheme.colors.onSurface
                )
            }
        }

        // Info popup
        DropdownMenu(
            expanded = showInfoPopup,
            onDismissRequest = { showInfoPopup = false },
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .padding(12.dp)
        ) {
            Text(
                text = stringResource(id = descriptionResId),
                color = MaterialTheme.colors.onSurface,
                fontSize = 16.sp
            )
        }
    }
}

@SimplePreview
@Composable
private fun Preview() {
    PreviewTheme {
        AIModelOptionsAlertDialog(
            showDialog = true,
            aiModelOptions = AIModelOptions(),
            onDismiss = {}
        )
    }
}