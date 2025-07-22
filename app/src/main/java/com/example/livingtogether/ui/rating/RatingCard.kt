package com.example.livingtogether.ui.rating

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.livingtogether.R
import com.example.livingtogether.ui.UserToRatingData

@Composable
fun RatingCard(
    userToRatings: UserToRatingData,
    modifier: Modifier = Modifier,
) {
    val image = when (userToRatings.ratingPlace) {
        1 -> painterResource(R.drawable.first_place)
        2 -> painterResource(R.drawable.second_place)
        3 -> painterResource(R.drawable.third_place)
        else -> painterResource(R.drawable.another_place)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(vertical = 4.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier
                    .width(120.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = userToRatings.userName,
                    fontSize = 24.sp,
                )
                Text(
                    text = userToRatings.totalRating.toString(),
                )
            }
            Image(
                painter = image,
                contentDescription = "rating_place",
            )
        }
    }
}
