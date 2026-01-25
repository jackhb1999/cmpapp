package components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.AddComment
import androidx.compose.material.icons.filled.LiveTv
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
import fake_data.Post

@Composable
fun PostListItem(
    modifier: Modifier = Modifier,
    post: Post,
    onPostClick: (Post) -> Unit,
    onProfileClick: (Int) -> Unit,
    onLikeClick: () -> Unit,
    onCommentClick: (Int) -> Unit,
    isDetailScreen: Boolean = false
) {
    Column(
        modifier = modifier.fillMaxWidth()
            .aspectRatio(ratio = 0.7f)
            .background(color = MaterialTheme.colors.surface)
            .clickable {
                onPostClick(post)
            }
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
            onLikeClick = onLikeClick,
            onCommentClick = { onCommentClick(post.id) }
        )

        Text(
            text = post.text,
            style = MaterialTheme.typography.body2,
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
        Text(text = name, style = MaterialTheme.typography.h4, color = MaterialTheme.colors.onSurface)
        Box(
            modifier = modifier.size(4.dp).clip(CircleShape).background(
                color = if (MaterialTheme.colors.isLight) {
                    Color.LightGray
                } else {
                    Color.DarkGray
                }
            )
        )
        Text(
            text = date, style = MaterialTheme.typography.caption.copy(
                textAlign = TextAlign.Start,
                fontSize = 12.sp,
                color = if (MaterialTheme.colors.isLight) {
                    Color.LightGray
                } else {
                    Color.DarkGray
                }
            ),
            modifier = modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Default.AddAPhoto,
            contentDescription = null,
            tint = if (MaterialTheme.colors.isLight) {
                Color.LightGray
            } else {
                Color.DarkGray
            }
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
                tint = if (MaterialTheme.colors.isLight) {
                    Color.LightGray
                } else {
                    Color.DarkGray
                }
            )
        }
        Text(
            text = "$likesCount",
            style = MaterialTheme.typography.subtitle2.copy(fontSize = 18.sp)
        )
        Spacer(modifier = modifier.width(MediumSpacing))
        IconButton(onClick = onCommentClick) {
            Icon(
                imageVector = Icons.Default.AddComment,
                contentDescription = null,
                tint = if (MaterialTheme.colors.isLight) {
                    Color.LightGray
                } else {
                    Color.DarkGray
                }
            )
        }
        Text(
            text = "$commentsCount",
            style = MaterialTheme.typography.subtitle2.copy(fontSize = 18.sp)
        )
    }

}