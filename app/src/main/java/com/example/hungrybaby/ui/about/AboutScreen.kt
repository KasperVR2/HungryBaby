package com.example.hungrybaby.ui.about

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.hungrybaby.R

@Composable
fun About() {
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.mediumSpacer)),
        modifier = Modifier.verticalScroll(rememberScrollState()),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(id = R.string.logo_description),
            )
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineLarge,
            )
        }
        Text(
            text = stringResource(id = R.string.about_text),
            style = MaterialTheme.typography.bodyLarge,
        )
        Button(onClick = { context.sendMail(to = "kasper.vanremoortere@student.hogent.be", subject = "Feedback Hungry Baby") }) {
            Text(text = stringResource(id = R.string.send_feedback))
        }
    }
}

fun Context.sendMail(
    to: String,
    subject: String,
) {
    try {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "vnd.android.cursor.item/email"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        println(e.message)
    } catch (t: Throwable) {
        println(t.message)
    }
}
