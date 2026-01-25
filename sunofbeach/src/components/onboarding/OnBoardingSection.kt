package components.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import components.LargeSpacing
import components.MediumSpacing
import components.ShortSpacing
import fake_data.FollowsUser
import io.ktor.http.parameters


@Composable
fun OnBoardingSection(
    modifier: Modifier = Modifier,
    users: List<FollowsUser>,
    onUserClick: (FollowsUser) -> Unit,
    onFollowButtonClick: (Boolean, FollowsUser) -> Unit,
    onBoardingFinish: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
//        Text(
//            text = "可以关注一下",
//            modifier = modifier.fillMaxWidth().padding(top = MediumSpacing),
//            style = MaterialTheme.typography.titleMedium,
//            textAlign = TextAlign.Center
//        )
        Text(
            text = "你可能感兴趣的人",
            modifier = modifier.fillMaxWidth().padding(horizontal = LargeSpacing).padding(top = MediumSpacing),
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = modifier.heightIn(ShortSpacing))
        UsersRow(
            modifier = modifier,
            users = users,
            onUserClick = onUserClick,
            onFollowButtonClick = onFollowButtonClick,
        )

        OutlinedButton(
            onClick = onBoardingFinish,
            modifier = modifier.fillMaxWidth(fraction = 0.5f).align(Alignment.CenterHorizontally)
                .padding(vertical = LargeSpacing),
            shape = RoundedCornerShape(percent = 50)
        ) {
            Text(text = "全部关注")
        }

    }
}

@Composable
fun UsersRow(
    modifier: Modifier = Modifier,
    users: List<FollowsUser>,
    onUserClick: (FollowsUser) -> Unit,
    onFollowButtonClick: (Boolean, FollowsUser) -> Unit,
) {

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(LargeSpacing),
        contentPadding = PaddingValues(horizontal = LargeSpacing),
        modifier = modifier,
    ) {
        items(items = users, key = { followUser -> followUser.id }) {
            OnBoardingUserItem(
                followsUser = it,
                onUserClick = onUserClick,
                onFollowButtonClick = onFollowButtonClick,
            )
        }
    }
}
