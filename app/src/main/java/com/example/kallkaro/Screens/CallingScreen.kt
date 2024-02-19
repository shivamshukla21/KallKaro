//package com.example.kallkaro.Screens
//
//import android.content.Intent
//import android.widget.Toast
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.offset
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.CallEnd
//import androidx.compose.material.icons.filled.FlipCameraIos
//import androidx.compose.material.icons.filled.Mic
//import androidx.compose.material.icons.filled.MicOff
//import androidx.compose.material.icons.filled.MobileScreenShare
//import androidx.compose.material.icons.filled.Videocam
//import androidx.compose.material.icons.filled.VideocamOff
//import androidx.compose.material3.BottomSheetScaffold
//import androidx.compose.material3.Button
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.SheetState
//import androidx.compose.material3.SheetValue
//import androidx.compose.material3.Text
//import androidx.compose.material3.rememberBottomSheetScaffoldState
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.example.kallkaro.Navigation.Router
//import kotlinx.coroutines.launch
//
//@Composable
//fun VideoCall() {
//    val scope = rememberCoroutineScope()
//    var cont = LocalContext.current
//    var micOn by remember { mutableStateOf(true) }
//    var vidOn by remember { mutableStateOf(true) }
//    var ss by remember { mutableStateOf(false) }
//    var scaffoldState = rememberBottomSheetScaffoldState(
//        bottomSheetState = SheetState(
//            initialValue = SheetValue.PartiallyExpanded,
//            skipHiddenState = false,
//            skipPartiallyExpanded = false)
//    )
//
//    BottomSheetScaffold(
//        sheetContainerColor = Color.White,
//        sheetShadowElevation = 20.dp,
//        scaffoldState = scaffoldState,
//        sheetPeekHeight = 108.dp,
//
//        sheetContent = {
//            Box(
//                Modifier
//                    .fillMaxWidth()
//                    .height(80.dp)
//                    .offset(y = -20.dp),
//                contentAlignment = Alignment.Center
//            ) {
//                Row(
//                    modifier = Modifier.fillMaxSize(),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceEvenly
//                ) {
//                    IconButton(
//                        onClick = { },
//                        modifier = Modifier
//                            .padding(4.dp)
//                            .size(30.dp)
//                    ) {
//                        Icon(
//                            imageVector = Icons.Filled.FlipCameraIos,
//                            tint = Color.DarkGray,
//                            contentDescription = "Switch Camera",
//                            modifier = Modifier.fillMaxSize()
//                        )
//                    }
//                    IconButton(
//                        onClick = { ss= !ss },
//                        modifier = Modifier
//                            .padding(4.dp)
//                            .size(30.dp)
//                    ) {
//                        Icon(
//                            imageVector = Icons.Filled.MobileScreenShare,
//                            tint = Color.DarkGray,
//                            contentDescription = if (ss) "Screen Sharing" else "Screen not Sharing",
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .background(
//                                    if (ss) Color.LightGray else Color.Transparent,
//                                    CircleShape
//                                )
//                        )
//                    }
//                    IconButton(
//                        onClick = { vidOn = !vidOn},
//                        modifier = Modifier
//                            .padding(4.dp)
//                            .size(50.dp)
//                    ) {
//                        Icon(
//                            imageVector = if (vidOn) Icons.Filled.Videocam else Icons.Filled.VideocamOff,
//                            tint = Color.DarkGray,
//                            contentDescription = if (vidOn) "Vid On" else "Vid Off",
//                            modifier = Modifier
//                                .size(30.dp)
//                                .background(
//                                    if (!vidOn) Color.LightGray else Color.Transparent,
//                                    CircleShape
//                                )
//                        )
//                    }
//                    IconButton(
//                        onClick = { micOn = !micOn },
//                        modifier = Modifier
//                            .padding(4.dp)
//                            .size(30.dp)
//                    ) {
//                        Icon(
//                            imageVector = if (micOn) Icons.Filled.Mic else Icons.Filled.MicOff,
//                            tint = Color.DarkGray,
//                            contentDescription = if (micOn) "Mic On" else "Mic Off",
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .background(
//                                    if (!micOn) Color.LightGray else Color.Transparent,
//                                    CircleShape
//                                )
//                        )
//                    }
//                    IconButton(
//                        onClick = {
//                            Toast.makeText(cont,"Call Ended", Toast.LENGTH_SHORT).show()
//                           Router.navigateTo(Screen)},
//                        modifier = Modifier
//                            .size(30.dp)
//                            .background(Color.Red, CircleShape)
//                    ) {
//                        Icon(
//                            imageVector = Icons.Filled.CallEnd,
//                            tint = Color.White,
//                            contentDescription = "Call End",
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .clip(CircleShape)
//                        )
//                    }
//                }
//            }
//            Column(
//                Modifier
//                    .background(Color.White)
//                    .fillMaxWidth()
//                    .padding(10.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text("Add Participants", fontWeight = FontWeight.Bold)
//                Spacer(Modifier.height(20.dp))
//                Button(
//                    onClick = {
//                        val sendIntent: Intent = Intent().apply {
//                            action = Intent.ACTION_SEND
//                            putExtra(Intent.EXTRA_TEXT, "${generateMeetId(12)}")
//                            type = "text/plain"
//                        }
//                        val shareIntent = Intent.createChooser(sendIntent, null)
//                        cont.startActivity(shareIntent)
//                    }
//                ) {
//                    Text("Share Link")
//                }
//            }
//        }) { innerPadding ->
//        Box(
//            Modifier
//                .background(Color.White)
//                .fillMaxSize()
//                .clickable {
//                    if (scaffoldState.bottomSheetState.isVisible) {
//                        scope.launch { scaffoldState.bottomSheetState.hide() }
//                    } else {
//                        scope.launch { scaffoldState.bottomSheetState.partialExpand() }
//                    }
//                },
//            contentAlignment = Alignment.Center
//        ) {
//            Text(text = "Domegle Video Call".uppercase(), fontWeight = FontWeight.ExtraBold)
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun VideoCallPreview(){
//    VideoCall()
//}
//
//fun generateMeetId(length: Int = 12): String {
//    val allowedChars = ('a'..'z')
//    return (1..3).map { allowedChars.random() }.joinToString("") + "-" +
//            (1..4).map { allowedChars.random() }.joinToString("") + "-" +
//            (1..3).map { allowedChars.random() }.joinToString("")
//}