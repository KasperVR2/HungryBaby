package com.example.hungrybaby.ui.news.news

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.hungrybaby.R

@Composable
fun NewsItem(news: String) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.smallSpacer))) {
            Text(text = stringResource(R.string.news_item), style = MaterialTheme.typography.titleSmall)
            Row {
                Text(text = news, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
