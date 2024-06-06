import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kallkaro.Components.BtnText
import com.example.kallkaro.Components.DeleteButton
import com.example.kallkaro.Components.LogoutButton
import com.example.kallkaro.Components.NormalTextComponent
import com.example.kallkaro.Data.HomeScreen.HomeScreenViewModel
import com.example.kallkaro.Data.HomeScreen.HomeUIEvents
import com.example.kallkaro.Data.Registration.RegistrationViewModel
import com.example.kallkaro.MeetingActivity
import com.example.kallkaro.ui.theme.ng2

class JoinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JoinActivityScreen(
                viewModel = RegistrationViewModel(),
                homeScreenViewModel = HomeScreenViewModel(),
                onCreateMeetingClicked = {
                    startActivity(Intent(this, MeetingActivity::class.java))
                },
                onJoinMeetingClicked = { meetingId ->
                    startActivity(Intent(this, MeetingActivity::class.java).apply {
                        putExtra("meetingId", meetingId)
                    }
                    )
                })
        }
    }
}

@Composable
fun JoinActivityScreen(
    viewModel: RegistrationViewModel,
    homeScreenViewModel: HomeScreenViewModel,
    onCreateMeetingClicked: () -> Unit,
    onJoinMeetingClicked: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(28.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.Top) {
            NormalTextComponent(value = "Buzz In \uD83E\uDD19")
        }
        LogoutButton(viewModel = RegistrationViewModel(), onButtonSelected = {homeScreenViewModel.onEvent(
            HomeUIEvents.LogoutButtonClicked)})
        Spacer(modifier = Modifier.size(20.dp))
        DeleteButton(viewModel = HomeScreenViewModel(), onButtonSelected = {homeScreenViewModel.onEvent(
            HomeUIEvents.DeleteButtonClicked)})
        Spacer(modifier = Modifier.size(20.dp))
        Button(
            colors = ButtonDefaults.buttonColors(ng2),
            onClick = {onCreateMeetingClicked()
                Log.d(true.toString(),"create")},
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            BtnText(value = "Create Meeting")
        }
        Text("OR", modifier = Modifier.padding(bottom = 16.dp))
        val context = LocalContext.current
        var meetingId by remember { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current
        OutlinedTextField(
            value = meetingId,
            onValueChange = { meetingId = it },
            label = { Text("Enter Meeting ID") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                onJoinMeetingClicked(meetingId)
                keyboardController?.hide()
            }),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(
            colors = ButtonDefaults.buttonColors(ng2),
            onClick = { Log.d(true.toString(),"join")
                onJoinMeetingClicked(meetingId) },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            BtnText(value = "Join Meeting")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JoinSP() {
   JoinActivityScreen(homeScreenViewModel = HomeScreenViewModel(), viewModel = RegistrationViewModel(), onCreateMeetingClicked ={}, onJoinMeetingClicked = {})
}