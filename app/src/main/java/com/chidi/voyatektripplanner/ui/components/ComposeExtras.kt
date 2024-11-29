package com.chidi.voyatektripplanner.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.chidi.voyatektripplanner.R
import com.chidi.voyatektripplanner.ui.theme.LocalCustomColors

@Composable
fun AddDetailsBox(
    @StringRes titleResId: Int,
    @StringRes detailsResId: Int,
    @StringRes buttonTextResId: Int,
    backColor: Color,
    textColor: Color,
    buttonTextColor: Color,
    buttonBackColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(
                top = 12.dp
            )
            .drawBehind {
                drawRoundRect(
                    color = backColor,
                    cornerRadius = CornerRadius(12f)
                )
            }
            .padding(
                start = 16.dp, end = 16.dp,
                top = 18.dp, bottom = 16.dp
            )
    ) {
        Text(
            stringResource(titleResId),
            color = textColor,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            stringResource(detailsResId),
            color = textColor,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(
                top = 12.dp
            )
        )
        MainButton(
            modifier = Modifier.padding(top = 42.dp),
            buttonTextResId = buttonTextResId,
            buttonTextColor = buttonTextColor,
            buttonBackColor = buttonBackColor,
            onClick = onClick
        )
    }
}

@Composable
fun TitleBox(
    @StringRes titleResId: Int,
    @StringRes subTextResId: Int
) {
    Text(
        stringResource(titleResId),
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(
            top = 26.dp, bottom = 6.dp
        )
    )
    Text(
        stringResource(subTextResId),
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopTitleBar(
    @StringRes titleResId: Int,
    onClickAction: () -> Unit
) {
    val borderColor = LocalCustomColors.current.blueLight80
    TopAppBar(
        title = {
            Text(
                stringResource(titleResId),
                style = MaterialTheme.typography.titleMedium
            )
        },
        navigationIcon = {
            IconButton(onClick = onClickAction) {
                Icon(
                    painter = painterResource(R.drawable.close),
                    contentDescription = null
                )
            }
        },
        modifier = Modifier.drawBehind {
            drawLine(
                color = borderColor,
                strokeWidth = 6f,
                start = Offset(0f, size.height),
                end = Offset(size.width, size.height)
            )
        }
    )
}

@Composable
fun MainButton(
    modifier: Modifier = Modifier,
    @StringRes buttonTextResId: Int,
    textStyle: TextStyle = MaterialTheme.typography.titleSmall,
    buttonTextColor: Color = LocalCustomColors.current.white,
    buttonBackColor: Color = LocalCustomColors.current.blue60,
    enabled: Boolean = true,
    padding: Dp = 16.dp,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = buttonBackColor,
            contentColor = buttonTextColor,
            disabledContentColor = LocalCustomColors.current.blueDark10,
            disabledContainerColor = LocalCustomColors.current.blueLight80
        ),
        shape = RoundedCornerShape(4.dp),
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        contentPadding = PaddingValues(vertical = padding)
    ) {
        Text(
            stringResource(buttonTextResId),
            style = textStyle
        )
    }
}

@Composable
fun MainOutlinedButton(
    onClick: () -> Unit,
    @DrawableRes iconResId: Int,
    @StringRes textResId: Int,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        border = BorderStroke(
            width = 1.dp,
            color = LocalCustomColors.current.blue60
        ),
        colors = ButtonDefaults.outlinedButtonColors().copy(
            contentColor = LocalCustomColors.current.blue60
        ),
        shape = RoundedCornerShape(4.dp),
        contentPadding = PaddingValues(
            vertical = 16.dp,
            horizontal = 6.dp
        )
    ) {
        Icon(
            painter = painterResource(iconResId),
            contentDescription = stringResource(textResId)
        )
        Text(
            stringResource(textResId),
            modifier = Modifier.padding(
                start = 6.dp
            )
        )
    }
}

@Composable
fun InputButton(
    @StringRes textResId: Int,
    @StringRes subTextResId: Int,
    @DrawableRes iconResId: Int,
    text: String?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        border = BorderStroke(
            width = 1.dp,
            color = Color.LightGray
        ),
        colors = CardDefaults.cardColors().copy(
            containerColor = LocalCustomColors.current.white80
        ),
        shape = RoundedCornerShape(4.dp),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 12.dp,
                    vertical = 26.dp
                )
        ) {
            Icon(
                painter = painterResource(iconResId),
                contentDescription = null,
                modifier = Modifier.size(22.dp)
            )
            Column(
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(
                    stringResource(textResId),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text ?: stringResource(subTextResId),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
fun DetailsViewBox(
    @StringRes titleResId: Int,
    @DrawableRes iconResId: Int,
    @DrawableRes imageResId: Int,
    @StringRes buttonTextResId: Int,
    backColor: Color,
    titleColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(top = 12.dp)
            .drawBehind {
                drawRoundRect(
                    color = backColor,
                    cornerRadius = CornerRadius(12f)
                )
            }
            .padding(
                top = 18.dp, bottom = 16.dp,
                start = 16.dp, end = 16.dp
            )
    ) {
        Row {
            Icon(
                painter = painterResource(iconResId),
                contentDescription = null,
                tint = titleColor
            )
            Text(
                stringResource(titleResId),
                color = titleColor,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(
                    start = 6.dp
                )
            )
        }
        Column(
            modifier = Modifier
                .padding(top = 12.dp)
                .drawBehind {
                    drawRoundRect(
                        color = Color.White,
                        cornerRadius = CornerRadius(8f)
                    )
                }
                .padding(horizontal = 42.dp, vertical = 56.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(imageResId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .requiredSize(140.dp)
            )
            Text(
                stringResource(R.string.no_request),
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(
                    vertical = 8.dp
                )
            )
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = LocalCustomColors.current.blue60
                ),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                Text(
                    stringResource(buttonTextResId),
                    color = LocalCustomColors.current.white,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

fun inlineIcon(
    @DrawableRes iconResId: Int,
    width: TextUnit = 2.em,
    height: TextUnit? = null,
    iconSize: Dp = 25.dp,
    placeholderVerticalAlign: PlaceholderVerticalAlign =
        PlaceholderVerticalAlign.Center,
    modifier: Modifier = Modifier
) = Pair(
    "$iconResId",
    InlineTextContent(
        Placeholder(
            width = width,
            height = height ?: width,
            placeholderVerticalAlign = placeholderVerticalAlign
        )
    ) {
        Icon(
            painter = painterResource(
                iconResId
            ),
            contentDescription = null,
            modifier = modifier
                .padding(end = 6.dp, top = 6.dp, bottom = 6.dp)
                .clickable {/* Remove Extra Padding*/ }
                .size(iconSize)
        )
    }
)

@Composable
fun RetryBox(
    @StringRes titleResId: Int = R.string.error_message,
    onRetry: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        Text(
            stringResource(titleResId),
            Modifier.padding(bottom = 10.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
        ElevatedButton(
            onClick = onRetry,
            Modifier
                .widthIn(min = 150.dp),
            colors = ButtonDefaults.elevatedButtonColors().copy(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(stringResource(R.string.retry))
        }
    }
}