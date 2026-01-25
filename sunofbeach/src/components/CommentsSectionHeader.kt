package components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CommentsSectionHeader(
    modifier: Modifier = Modifier,
    onAddCommentClick:()-> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(LargeSpacing),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(text = "评论", style = MaterialTheme.typography.titleSmall)
        OutlinedButton(onClick = onAddCommentClick){
            Text(text="新增评论")
        }
    }
}


