package ui.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ui.components.LargeSpacing
import ui.components.MediumSpacing
import util.Constants

internal fun LazyListScope.loadingMoreItem(modifier: Modifier = Modifier) {
    item(key = Constants.LOADING_MORE_ITEM_KEY) {
        Box(
            modifier = modifier.fillMaxWidth().padding(
                vertical = MediumSpacing,
                horizontal = LargeSpacing
            ), contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}