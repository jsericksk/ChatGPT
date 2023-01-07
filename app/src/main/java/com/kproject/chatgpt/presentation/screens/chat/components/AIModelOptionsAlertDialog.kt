package com.kproject.chatgpt.presentation.screens.chat.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kproject.chatgpt.R
import com.kproject.chatgpt.commom.model.AIModel
import com.kproject.chatgpt.commom.model.AIModelOptions
import com.kproject.chatgpt.presentation.screens.components.CustomAlertDialog
import com.kproject.chatgpt.presentation.theme.PreviewTheme
import com.kproject.chatgpt.presentation.theme.SimplePreview
import java.math.RoundingMode

@Composable
fun AIModelOptionsAlertDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    currentAIModelOptions: AIModelOptions,
    onSaveAIModelOptions: (AIModelOptions) -> Unit
) {
    if (showDialog) {
        var aiModelOptions: AIModelOptions by remember { mutableStateOf(currentAIModelOptions) }

        CustomAlertDialog(
            showDialog = showDialog,
            onDismiss = onDismiss,
            onClickButtonOk = {
                onSaveAIModelOptions.invoke(aiModelOptions)
            },
            okButtonTitle = stringResource(id = R.string.button_save),
            title = stringResource(id = R.string.ia_model_options)
        ) {
            Column {
                AIModelOption(
                    aiModel = aiModelOptions.aiModel,
                    onAIModelChanged = {
                        aiModelOptions = aiModelOptions.copy(aiModel = it)
                    }
                )
                Spacer(Modifier.height(10.dp))
                MaxTokensOption(
                    maxTokens = aiModelOptions.maxTokens.toFloat(),
                    onMaxTokensValueChange = {
                        aiModelOptions = aiModelOptions.copy(maxTokens = it)
                    }
                )
                Spacer(Modifier.height(10.dp))
                TemperatureOption(
                    temperature = aiModelOptions.temperature,
                    onTemperatureValueChange = {
                        aiModelOptions = aiModelOptions.copy(temperature = it)
                    }
                )
            }
        }
    }
}

@Composable
private fun AIModelOption(
    aiModel: AIModel,
    onAIModelChanged: (AIModel) -> Unit
) {
    var showOptionsMenu by remember { mutableStateOf(false) }
    val modelDescriptionResId = if (aiModel == AIModel.TextDavinci003) {
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
                    .background(
                        color = MaterialTheme.colors.secondary,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { showOptionsMenu = true }
                    .padding(12.dp)
            ) {
                Text(
                    text = aiModel.name,
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

        AIModelOptionCustomDropdownMenu(
            showDropdownMenu = showOptionsMenu,
            onDismiss = { showOptionsMenu = false },
            onOptionSelected = { option ->
                onAIModelChanged.invoke(option)
            }
        )
    }
}

@Composable
private fun AIModelOptionCustomDropdownMenu(
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
                        shape = RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp,
                            bottomStart = 14.dp,
                            bottomEnd = 14.dp
                        )
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
    onMaxTokensValueChange: (Int) -> Unit
) {
    CardOptionItem {
        OptionTitle(
            textResId = R.string.ia_model_option_max_tokens,
            descriptionResId = R.string.ia_model_option_max_tokens_description
        )

        Slider(
            value = maxTokens,
            onValueChange = {
                onMaxTokensValueChange.invoke(it.toInt())
            },
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colors.onSecondary,
                activeTrackColor = MaterialTheme.colors.secondary,
                inactiveTrackColor = MaterialTheme.colors.onSecondary.copy(0.5f)
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
                onTemperatureValueChange.invoke(it.roundTemperature())
            },
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colors.onSecondary,
                activeTrackColor = MaterialTheme.colors.secondary,
                inactiveTrackColor = MaterialTheme.colors.onSecondary.copy(0.5f)
            ),
            valueRange = 0f..1f
        )

        Text(
            text = temperature.toString(),
            color = MaterialTheme.colors.onSurface,
            fontSize = 14.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

private fun Float.roundTemperature(): Float {
    return this.toBigDecimal().setScale(2, RoundingMode.UP).toFloat()
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
            onDismiss = {},
            currentAIModelOptions = AIModelOptions(),
            onSaveAIModelOptions = {}
        )
    }
}