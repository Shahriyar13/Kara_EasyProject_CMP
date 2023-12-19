package app.duss.easyproject.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.duss.easyproject.domain.entity.Person

@Composable
fun PersonItemContent(
    modifier: Modifier = Modifier,
    person: Person,
    onChange: (Person) -> Unit,
    editState: Boolean = false,
    brush: Brush,
    onDelete: () -> Unit,
) {
    val personState by remember { mutableStateOf(person) }
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            focusedElevation = 0.dp,
            hoveredElevation = 0.dp,
            draggedElevation = 0.dp,
            disabledElevation = 0.dp,
        ),
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .background(brush = brush, alpha = .4f)
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(8.dp),
        ) {
            Column(
                modifier = Modifier
                    .weight(1F),
            ) {


                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    BasicEditableText(
                        label = "Firstname",
                        readOnly = !editState,
                        value = personState.firstName ?: "",
                        onValueChange = {
                            personState.firstName = it
                            onChange(personState)
                        },
                        singleLine = true,
                        modifier = Modifier.weight(1F)
                    )
                    BasicEditableText(
                        label = "Lastname",
                        readOnly = !editState,
                        value = personState.lastName ?: "",
                        onValueChange = {
                            personState.lastName = it
                            onChange(personState)
                        },
                        singleLine = true,
                        modifier = Modifier.weight(1F)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    BasicEditableText(
                        label = "Job Title",
                        readOnly = !editState,
                        value = personState.jobTitle ?: "",
                        onValueChange = {
                            personState.jobTitle = it
                            onChange(personState)
                        },
                        singleLine = true,
                        modifier = Modifier.weight(1F)
                    )
                    BasicEditableText(
                        label = "Email",
                        readOnly = !editState,
                        value = personState.email ?: "",
                        onValueChange = {
                            personState.email = it
                            onChange(personState)
                        },
                        singleLine = true,
                        modifier = Modifier.weight(1F)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    BasicEditableText(
                        label = "Telephone",
                        readOnly = !editState,
                        value = personState.telephone ?: "",
                        onValueChange = {
                            personState.telephone = it
                            onChange(personState)
                        },
                        singleLine = true,
                        modifier = Modifier.weight(1F)
                    )
                    BasicEditableText(
                        label = "Mobile",
                        readOnly = !editState,
                        value = personState.mobile ?: "",
                        onValueChange = {
                            personState.mobile = it
                            onChange(personState)
                        },
                        singleLine = true,
                        modifier = Modifier.weight(1F)
                    )
                }
            }
        }
    }
}