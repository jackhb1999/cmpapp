package components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material.Colors
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddChart
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fake_data.Comment

@Composable
fun CommentListItem(
    modifier: Modifier = Modifier,
    comment: Comment,
    onProfileClick: (Int) -> Unit,
    onMoreIconClick: () -> Unit
) {

    Row(
        modifier = modifier.fillMaxWidth().padding(LargeSpacing),
        horizontalArrangement = Arrangement.spacedBy(MediumSpacing)
    ) {
        CircleImage(
            imageUrl = comment.authorImageUrl,
            modifier = modifier.size(30.dp)
        ) {
            onProfileClick(comment.authorId)
        }
        Column(modifier = modifier.weight(1f)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(MediumSpacing)
            ) {
                Text(
                    text = comment.authorName,
                    style = MaterialTheme.typography.subtitle2,
                    modifier = modifier.alignByBaseline()
                )

                Text(
                    text = comment.date,
                    style = MaterialTheme.typography.caption,
                    color = if (MaterialTheme.colors.isLight) {
                        Color.LightGray
                    } else {
                        Color.DarkGray
                    },
                    modifier = modifier.alignByBaseline().weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.AddChart,
                    contentDescription = null,
                    tint = if (MaterialTheme.colors.isLight) {
                        Color.LightGray
                    } else {
                        Color.DarkGray
                    },
                    modifier = modifier.clickable { onMoreIconClick() }
                )
            }
            Text(
                text = comment.comment,
                style = MaterialTheme.typography.body2
            )
        }

    }
}


