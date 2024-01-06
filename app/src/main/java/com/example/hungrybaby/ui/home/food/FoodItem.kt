package com.example.hungrybaby.ui.home.food

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hungrybaby.R
import com.example.hungrybaby.ui.home.HomeModel

@Composable
fun FoodItem(
    volume: Int,
    dateAndTime: String,
    homeModel: HomeModel = viewModel(factory = HomeModel.Factory),
) {
    Card {
        var expanded by rememberSaveable { mutableStateOf(false) }
        val color by animateColorAsState(
            targetValue =
                if (expanded) {
                    MaterialTheme.colorScheme.tertiaryContainer
                } else {
                    MaterialTheme.colorScheme.secondaryContainer
                },
            label = "colorAnimation",
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier =
                Modifier
                    .animateContentSize(
                        animationSpec =
                            spring(
                                dampingRatio = Spring.DampingRatioNoBouncy,
                                stiffness = Spring.StiffnessMedium,
                            ),
                    )
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth()
                    .background(color)
                    .padding(PaddingValues(8.dp, 0.dp)),
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.botleblack),
                        contentDescription = stringResource(id = R.string.logo_description),
                    )
                    Text(
                        text = "$volume ml",
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    Text(
                        text = "@ $dateAndTime",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                if (expanded) {
                    Row {
                        Spacer(modifier = Modifier.weight(1F))
                        IconButton(
                            onClick = {
                                homeModel.deleteFood(volume, dateAndTime)
                                expanded = !expanded
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Filled.DeleteForever,
                                contentDescription = stringResource(R.string.expand_description),
                                tint = MaterialTheme.colorScheme.secondary,
                            )
                        }
                        Spacer(modifier = Modifier.weight(1F))
                    }
                }
            }
            TaskItemButton(
                expanded = expanded,
                onClick = { expanded = !expanded },
                modifier = Modifier.align(Alignment.Top),
            )
        }
    }
}

@Composable
fun TaskItemButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = stringResource(R.string.expand_description),
            tint = MaterialTheme.colorScheme.secondary,
        )
    }
}
