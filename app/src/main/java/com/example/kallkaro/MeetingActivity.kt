package com.example.kallkaro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import live.videosdk.rtc.android.VideoSDK
import androidx.appcompat.app.AppCompatActivity
import live.videosdk.rtc.android.Meeting

class MeetingActivity : ComponentActivity() {
    var meeting: Meeting? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val token = intent.getStringExtra("token") ?: "cd7b4918-100b-4f5c-9d88-14ed611b85f6"
        val meetingId = intent.getStringExtra("meetingId") ?: ""
        val participantName = "Shiv" // Set participant name here
        val micEnabled = true // Set mic enabled status here
        val webcamEnabled = true // Set webcam enabled status here
        val participants = listOf("Participant 1", "Participant 2", "Participant 3")

        setContent {
            MeetingActivityScreen(
                token = token,
                meetingId = meetingId,
                participantName = participantName,
                micEnabled = micEnabled,
                webcamEnabled = webcamEnabled,
                participants = participants
            )
        }
    }
}

@Composable
fun ParticipantsList(participants: List<String>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Participants List")
        Spacer(modifier = Modifier.height(16.dp))
        participants.forEach { participant ->
            Text(participant)
        }
    }
}

@Composable
fun MeetingActivityScreen(
    token: String,
    meetingId: String,
    participantName: String,
    micEnabled: Boolean,
    webcamEnabled: Boolean,
    participants: List<String>
) {
    // Initialize meeting and join
    val context = LocalContext.current
    var meeting: Meeting? = null
    VideoSDK.config(token)
    meeting = VideoSDK.initMeeting(
        context,
        meetingId,
        participantName,
        micEnabled,
        webcamEnabled,
        null, // participantId
        "wss", // preferredProtocol
        "grid", // mode
        false, // multiStream
        null // customTracks
    )
    meeting?.join()

    // Add UI components for meeting screen
    ParticipantsList(participants)

    // Add UI components for meeting screen
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Meeting Screen")
    }
}

