package com.open.exchange.cconverter.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.open.exchange.cconverter.R
import com.open.exchange.cconverter.presentation.ui.theme.CConverterTheme
import com.open.exchange.domain.models.ResultModel

val boxShape: Shape = CutCornerShape(
    bottomStartPercent = 0,
    topStartPercent = 0,
    topEndPercent = 25,
    bottomEndPercent = 0
)

@Composable
fun CurrencyShapeSelection(shadowText: String,
                  text: String,
                  isSelected: Boolean,
                  onSelect: () -> Unit?){
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier//.graphicsLayer(shape = boxShape, clip = true)
            .shadow(
                elevation = 1.dp,
                clip = true,
                shape = boxShape
            )
            .clickable { onSelect() }
            .background(CConverterTheme.colors.primary)
            .fillMaxWidth(0.45f)
            .aspectRatio(2.3f)) {
        Text(
            text = shadowText,
            color = CConverterTheme.colors.onSurface.copy(0.3f),
            style = MaterialTheme.typography.displayLarge
        )
        Text(
            text = text,
            textAlign = TextAlign.Center,
            color = CConverterTheme.colors.onSurface,
            style = MaterialTheme.typography.titleLarge
        )
        if (isSelected) {
            Icon(
                imageVector = Icons.Filled.Done,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(PaddingValues(2.dp))
                    .size(50.dp),
                tint = CConverterTheme.colors.onSurface,
                contentDescription = stringResource(R.string.selected)
            )
        }
    }
}

@Composable
fun CurrencyShapeResult(result: ResultModel) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier//.graphicsLayer(shape = boxShape, clip = true)
            .shadow(
                elevation = 1.dp,
                clip = true,
                shape = boxShape
            )
            .fillMaxWidth(0.9f)
            .aspectRatio(4f)
            .background(CConverterTheme.colors.primary)
    ) {
        Text(
            text = result.currencyCode,
            color = CConverterTheme.colors.onSurface.copy(0.3f),
            style = MaterialTheme.typography.displayLarge
        )
        Text(
            text = result.currencyName,
            modifier = Modifier.align(Alignment.TopCenter),
            textAlign = TextAlign.Center,
            color = CConverterTheme.colors.onSurface,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = if (result.result > 0) {
                String.format("%.2f", result.result)
            } else {
                String()
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .testTag(stringResource(R.string.test_result)),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            color = CConverterTheme.colors.onSurface,
            style = MaterialTheme.typography.displayMedium
        )
    }
}
