package com.chidi.voyatektripplanner.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.chidi.voyatektripplanner.R
import com.chidi.voyatektripplanner.ui.theme.LocalCustomColors

@Composable
fun InputBox(
    @StringRes titleResId: Int,
    modifier: Modifier = Modifier,
    @StringRes hintResId: Int,
    hintTextStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    maxLines: Int = 1,
    imeAction: ImeAction = ImeAction.Next,
    onTextChanged: (String) -> Unit
) {
    Text(
        stringResource(titleResId),
        style = MaterialTheme.typography.titleSmall,
        modifier = modifier.padding(
            top = 14.dp, bottom = 6.dp
        )
    )
    OutlineEditBox(
        hintResId = hintResId,
        hintTextStyle = hintTextStyle,
        maxLines = maxLines,
        imeAction = imeAction,
        onTextChanged = onTextChanged
    )
}

@Composable
internal fun OutlineEditBox(
    modifier: Modifier = Modifier,
    @StringRes hintResId: Int,
    hintTextStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    maxLines: Int = 1,
    imeAction: ImeAction = ImeAction.Next,
    onTextChanged: (String) -> Unit
) {
    var newText by remember { mutableStateOf("") }

    OutlinedTextField(
        value = newText,
        onValueChange = {
            newText = it
            onTextChanged(it)
        },
        shape = RoundedCornerShape(4.dp),
        singleLine = maxLines == 1,
        minLines = maxLines,
        maxLines = maxLines,
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = imeAction
        ),
        placeholder = {
            Text(
                stringResource(hintResId),
                style = hintTextStyle
            )
        },
        textStyle = MaterialTheme.typography.titleSmall
    )
}

@Composable
fun OptionsSelector(
    modifier: Modifier = Modifier,
    options: List<Int>,
    @StringRes selectedOption: Int?,
    @StringRes placeHolderTextResId: Int,
    borderStroke: BorderStroke = BorderStroke(
        width = 1.dp,
        color = LocalCustomColors.current.blueDark10
    ),
    onSelected: (Int) -> Unit
) {
    var showMenu by remember {
        mutableStateOf(false)
    }
    var selectIndex by remember {
        mutableIntStateOf(
            options.indexOf(selectedOption)
        )
    }

    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .border(
                    border = borderStroke,
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(horizontal = 18.dp)
                .clickable { showMenu = !showMenu },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                stringResource(
                    if (selectIndex == -1)
                        placeHolderTextResId
                    else options[selectIndex]
                ),
                style = if (selectIndex == -1)
                    MaterialTheme.typography.bodyMedium
                else MaterialTheme.typography.titleSmall
            )
            Icon(
                painterResource(R.drawable.arrow_down_thin),
                contentDescription = null,
                Modifier.size(18.dp),
                tint = LocalCustomColors.current.blueDark40
            )
        }
        DropdownMenu(
            expanded = showMenu,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            onDismissRequest = { showMenu = false }
        ) {
            options.forEachIndexed { index, item ->
                val isSelected = selectIndex == index
                DropdownMenuItem(
                    text = { Text(stringResource(item)) },
                    colors = MenuDefaults.itemColors().copy(
                        textColor = if (isSelected)
                            LocalCustomColors.current.white
                        else LocalCustomColors.current.black
                    ),
                    modifier = Modifier.background(
                        color = if (isSelected)
                            LocalCustomColors.current.blue60
                        else LocalCustomColors.current.white
                    ),
                    trailingIcon = {
                        if (isSelected) Icon(
                            painter = painterResource(R.drawable.check_mark),
                            contentDescription = stringResource(item),
                            tint = LocalCustomColors.current.white
                        )
                    },
                    onClick = {
                        selectIndex = index
                        showMenu = false
                        onSelected(item)
                    }
                )
            }
        }
    }
}

