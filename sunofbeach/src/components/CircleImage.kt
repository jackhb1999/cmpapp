package components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource


@Composable
fun CircleImage(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    onClick:()->Unit,
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        modifier = modifier.clip(CircleShape).clickable{ onClick() },
//        placeholder = if(MaterialTheme.colors.isLight){
//            painterResource()
//        },
        contentScale = ContentScale.Crop
    )
}
