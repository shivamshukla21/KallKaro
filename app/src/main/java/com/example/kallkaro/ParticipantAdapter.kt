import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import live.videosdk.rtc.android.Meeting
import live.videosdk.rtc.android.Participant

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ParticipantAdapter(meeting: Meeting) {
    val participants: List<Participant> = meeting.getParticipants()?.values?.toList() ?: emptyList()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        items(participants) { participant ->
            ParticipantItem(participant = participant)
        }
    }
}

@Composable
fun ParticipantItem(participant: Participant) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = participant.displayName)
        // Add more UI elements as needed
    }
}