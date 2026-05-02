package components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.AddComment
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormat

import model.Post


@Composable
fun PostListItem(
    modifier: Modifier = Modifier,
    post: Post,
    onPostClick: (Post) -> Unit,
    onProfileClick: (String) -> Unit,
    onLikeClick: (Post) -> Unit,
    onCommentClick: (String) -> Unit,
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
        PostItemHeader(name = post.username, profileUrl = post.userImageUrl, date = post.createdAt) {
            onProfileClick(post.userId)
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
            isLiked = post.isLiked,
            likesCount = post.likesCount,
            commentsCount = post.commentsCount,
            onLikeClick = { onLikeClick(post) },
            onCommentClick = { onCommentClick(post.postId) },
            isDetailScreen = isDetailScreen
        )

        Text(
            text = post.caption,
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
    profileUrl: String?,
    date: LocalDateTime,
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
            text = date.toString(), style = MaterialTheme.typography.bodyLarge.copy(
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
    isLiked: Boolean,
    likesCount: Int,
    commentsCount: Int,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
    isDetailScreen: Boolean = false
) {

    Row(
        modifier = modifier.fillMaxWidth().padding(vertical = 0.dp, horizontal = MediumSpacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onLikeClick) {
            if (isLiked) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite",
                    tint = Color.Red
                )
            } else {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "FavoriteBorder",
                    tint = Black24
                )
            }
        }
        Text(
            text = "$likesCount",
            style = MaterialTheme.typography.titleSmall.copy(fontSize = 18.sp)
        )
        if (!isDetailScreen) {
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

}