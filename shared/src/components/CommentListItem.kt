package components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddChart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
                    style = MaterialTheme.typography.titleSmall,
                    modifier = modifier.alignByBaseline()
                )

                Text(
                    text = comment.date,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.surface,
                    modifier = modifier.alignByBaseline().weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.AddChart,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.surface,
                    modifier = modifier.clickable { onMoreIconClick() }
                )
            }
            Text(
                text = comment.comment,
                style = MaterialTheme.typography.bodySmall
            )
        }

    }
}


