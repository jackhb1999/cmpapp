package components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.AddComment
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import fake_data.Post

@Composable
fun PostListItem(
    modifier: Modifier = Modifier,
    post: Post,
    onPostClick: (Post) -> Unit,
    onProfileClick: (Int) -> Unit,
    onLikeClick: (Int) -> Unit,
    onCommentClick: (Int) -> Unit,
    isDetailScreen: Boolean = false
) {
    Column(
        modifier = modifier.fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface)
            .clickable {
                onPostClick(post)
            }
            .padding(bottom = ExtraLargeSpacing)
    ) {
        PostItemHeader(name = post.authorName, profileUrl = post.authorImage, date = post.createdAt) {
            onProfileClick(post.authorId)
        }

        AsyncImage(
            model = post.imageUrl,
            contentDescription = null,
            modifier = modifier.fillMaxWidth().aspectRatio(ratio = 1.0f),
            contentScale = ContentScale.Crop,
//            placeholder = if(MaterialTheme.colors.isLight){
//
//            }else{
//
//            }
        )
        PostLikesRow(
            likesCount = post.likeCount,
            commentsCount = post.commentCount,
            onLikeClick = { onLikeClick(post.id) },
            onCommentClick = { onCommentClick(post.id) }
        )

        Text(
            text = post.text,
            style = MaterialTheme.typography.bodySmall,
            modifier = modifier.padding(horizontal = LargeSpacing),
            maxLines = if (isDetailScreen) {
                20
            } else {
                2
            },
            overflow = TextOverflow.Ellipsis
        )

    }

}

@Composable
fun PostItemHeader(
    modifier: Modifier = Modifier,
    name: String,
    profileUrl: String,
    date: String,
    onProfileClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = LargeSpacing, vertical = MediumSpacing),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MediumSpacing)
    ) {
        CircleImage(imageUrl = profileUrl, modifier = modifier.size(30.dp)) {
            onProfileClick()
        }
        Text(text = name, style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onSurface)
        Box(
            modifier = modifier.size(4.dp).clip(CircleShape)
        )
        Text(
            text = date, style = MaterialTheme.typography.bodyLarge.copy(
                textAlign = TextAlign.Start,
                fontSize = 12.sp,
            ),
            modifier = modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Default.AddAPhoto,
            contentDescription = null,
        )

    }
}


@Composable
fun PostLikesRow(
    modifier: Modifier = Modifier,
    likesCount: Int,
    commentsCount: Int,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit
) {

    Row(
        modifier = modifier.fillMaxWidth().padding(vertical = 0.dp, horizontal = MediumSpacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onLikeClick) {
            Icon(
                imageVector = Icons.Default.LiveTv,
                contentDescription = null,

            )
        }
        Text(
            text = "$likesCount",
            style = MaterialTheme.typography.titleSmall.copy(fontSize = 18.sp)
        )
        Spacer(modifier = modifier.width(MediumSpacing))
        IconButton(onClick = onCommentClick) {
            Icon(
                imageVector = Icons.Default.AddComment,
                contentDescription = null,
            )
        }
        Text(
            text = "$commentsCount",
            style = MaterialTheme.typography.titleSmall.copy(fontSize = 18.sp)
        )
    }

}