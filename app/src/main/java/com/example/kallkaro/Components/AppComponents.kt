@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.kallkaro.Components

import android.content.ContentValues.TAG
import android.nfc.Tag
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DrawerDefaults
import androidx.compose.material.DrawerValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.TextButton
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kallkaro.Data.HomeScreen.HomeScreenViewModel
import com.example.kallkaro.Data.HomeScreen.HomeUIEvents
import com.example.kallkaro.Data.Registration.RegistrationViewModel
import com.example.kallkaro.Navigation.Router
import com.example.kallkaro.Navigation.Screen
import com.example.kallkaro.ui.theme.Purple40
import com.example.kallkaro.ui.theme.bgcol
import com.example.kallkaro.ui.theme.colorText
import com.example.kallkaro.ui.theme.ng2
import com.example.kallkaro.Data.Login.LoginViewModel
import androidx.compose.material.TextField
import androidx.compose.material.icons.filled.MobileFriendly
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WarningAmber
import com.example.kallkaro.Data.Rules.Validator
import com.example.kallkaro.MainActivity
import com.example.kallkaro.ui.theme.bg
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NormalTextComponent (value: String) {
    Text(text = value,
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .heightIn(40.dp),
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal),
        color = colorText, textAlign = TextAlign.Center
    )
}

@Composable
fun BtnText(value: String) {
    Text(text = value,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold)
}

@Composable
fun HeadingTextComponent (value: String) {
    Text(text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(),
        style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal),
        color = colorText, textAlign = TextAlign.Center
    )
}

@Composable
fun TextField (labelvalue: String, onTextSelected: (String) -> Unit) {
    var txt = rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .size(60.dp)
            .clip(RoundedCornerShape(4.dp)),
        label = { Text(text = labelvalue) },
        value = txt.value,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = bgcol,
            focusedLabelColor = Purple40,
            focusedBorderColor = Purple40,
            cursorColor = Purple40
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, capitalization = KeyboardCapitalization.Sentences),
        singleLine = true,
        onValueChange = {
            txt.value=it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(Icons.Filled.Person, contentDescription = "")
        }
    )
}

@Composable
fun EmTextField (labelvalue: String, onTextSelected: (String) -> Unit) {
    var txt = rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .size(60.dp)
            .clip(RoundedCornerShape(4.dp)),
        label = { Text(text = labelvalue) },
        value = txt.value,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = bgcol,
            focusedLabelColor = Purple40,
            focusedBorderColor = Purple40,
            cursorColor = Purple40
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        onValueChange = {
            txt.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(Icons.Filled.Email, contentDescription = "")
        }
    )
}

@Composable
fun PswdTextField (labelvalue: String, onTextSelected: (String) -> Unit) {
    var txt = rememberSaveable { mutableStateOf("") }
    var focusManager = LocalFocusManager.current

    var visib = remember {
        mutableStateOf(false)
    }
    OutlinedTextField(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .size(60.dp)
            .clip(RoundedCornerShape(4.dp)),
        label = { Text(text = labelvalue) },
        value = txt.value,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = bgcol,
            focusedLabelColor = Purple40,
            focusedBorderColor = Purple40,
            cursorColor = Purple40
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send,
            keyboardType = KeyboardType.Password),
        keyboardActions = KeyboardActions(
            onSend = {
                focusManager.clearFocus()
            }),
        singleLine = true,
        onValueChange = {
            txt.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(Icons.Filled.Password, contentDescription = "")
        },
        trailingIcon = {
            IconButton(onClick = { visib.value = !visib.value }) {
                Icon(
                    imageVector = if (visib.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = "Toggle password visibility",
                    tint = if (visib.value) Purple40 else Color.DarkGray
                )
            }
        },
        visualTransformation = if (visib.value) VisualTransformation.None else PasswordVisualTransformation()
    )
}

@Composable
fun RegButton (viewModel: RegistrationViewModel, onButtonSelected: () -> Unit, fNameSt: Boolean, lNameSt: Boolean, EmSt: Boolean, PswdSt: Boolean, ChkSt: Boolean) {
    val st = fNameSt && lNameSt && EmSt && PswdSt && ChkSt
    Log.d(st.toString(),"value of reg status")
    Button(onClick = { onButtonSelected.invoke() },
        enabled = st,
        //enabled = isEmailValid(emState.value) && checkedState.value && pswdState.value.length>5,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(18.dp),
        colors = ButtonDefaults.buttonColors(ng2)
    ) {
        BtnText(value = "Register")
    }
}

@Composable
fun LogButton (viewModel: LoginViewModel, onButtonSelected: () -> Unit, EmSt: Boolean, PswdSt: Boolean) {
    val st = EmSt && PswdSt
    Log.d(st.toString(),"value of login status")
    Button(onClick = { onButtonSelected.invoke() },
        enabled = st,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(18.dp),
        colors = ButtonDefaults.buttonColors(ng2)
    ) {
        BtnText(value = "Login")
    }
}

@Composable
fun LogoutButton (viewModel: RegistrationViewModel, onButtonSelected: () -> Unit) {
    Log.d(true.toString(),"value of logout status")
    Button(onClick = { onButtonSelected.invoke() },
//        enabled = st,
//        enabled = isEmailValid(emState.value) && pswdState.value.length>5,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(18.dp),
        colors = ButtonDefaults.buttonColors(ng2)
    ) {
        BtnText(value = "Logout")
    }
}

@Composable
fun DeleteButton (viewModel: HomeScreenViewModel, onButtonSelected: () -> Unit) {
    Log.d(true.toString(),"value of delete status")
    val openAlertDialog = remember { mutableStateOf(false) }

    Button(onClick = { openAlertDialog.value = true },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(18.dp),
        colors = ButtonDefaults.buttonColors(ng2)
    ) {
        BtnText(value = "Delete")
    }
    when {
        openAlertDialog.value -> {
            AlertDialogExample(
                onDismissRequest = { openAlertDialog.value = false },
                onConfirmation = {
                    openAlertDialog.value = false
                    onButtonSelected.invoke()
                    Log.d("Alert", "Confirmed")
                },
                dialogTitle = "Are You Sure?",
                dialogText = "Your Account and all the details will be Permanently Deleted",
                icon = { Icon(Icons.Filled.Warning, contentDescription = "Warning Icon") }
            )
        }
    }
}

@Composable
fun ConnectButton(viewModel: HomeScreenViewModel, onButtonSelected: () -> Unit) {
    Button(onClick = { onButtonSelected.invoke() },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(18.dp),
        colors = ButtonDefaults.buttonColors(ng2)
    ) {
        BtnText(value = "Connect")
    }
}

@Composable
fun CheckBoxComp (value: String, onCheckBoxTick: (Boolean) -> Unit) {
    var ckState = rememberSaveable { mutableStateOf(false) }
    Row(modifier = Modifier
        .offset(x = -20.dp)
        .fillMaxWidth()
        .heightIn(16.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            colors = CheckboxDefaults.colors(Purple40),
            checked = ckState.value,
            onCheckedChange = {
                ckState.value = !ckState.value
                onCheckBoxTick(it) },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterVertically))
        ClickableTextComp1(value = value, onTextSelected = {
            Router.navigateTo(Screen.LoginScreen)
        })
    }
}

@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: @Composable () -> Unit,
) {
    AlertDialog(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                icon()
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = dialogTitle, fontWeight = FontWeight.Bold)
            }
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Delete", color = Color.Red, fontWeight = FontWeight.ExtraBold)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss", fontWeight = FontWeight.Bold)
            }
        }
    )
}

@Composable
fun ClickableTextComp1 (value: String, onTextSelected: (String)->Unit) {
    val init = "By continuing you accept our\n"
    val privpol = "Privacy Policy"
    val and = " and"
    val term = "Term of Use"

//    val words = value.split("\n")
//    val init = words.getOrNull(0) ?: ""
//    val privpol = words.getOrNull(1) ?: ""
//    val and = words.getOrNull(2) ?: ""
//    val term = words.getOrNull(3) ?: ""

    val uriHandler = LocalUriHandler.current
    val annotatedString = buildAnnotatedString {
        append(init)
        withStyle(style = SpanStyle(color = Purple40, textDecoration = TextDecoration.Underline)){
            pushStringAnnotation(tag = privpol, annotation = "https://policies.google.com/privacy")
            append(privpol)
        }
        append(text = " ")
        append(and)
        append(text = " ")
        withStyle(style = SpanStyle(color = Purple40, textDecoration = TextDecoration.Underline)){
            pushStringAnnotation(tag = term, annotation = "https://policies.google.com/terms")
            append(term)
        }
    }
    ClickableText(text = annotatedString,
        modifier = Modifier.offset(x=-10.dp),
        onClick = { offset ->
        annotatedString.getStringAnnotations(offset, offset)
            .firstOrNull()?.let { stringAnnotation ->
                uriHandler.openUri(stringAnnotation.item)
            }
    })
}

@Composable
fun ClickableTextComp2 (isval: Boolean, value: String, onTextSelected: (String)->Unit) {
    val forgot = "Forgot details? Let us help you "
    val regain = "regain access!"
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    val annotatedString = buildAnnotatedString {
        append(forgot)
        withStyle(style = SpanStyle(color = Purple40, fontSize = TextUnit.Unspecified)) {
            pushStringAnnotation(tag = regain, annotation = regain)
            append(regain)
        }
    }

    ClickableText(text = annotatedString,
        modifier = Modifier.offset(x = 10.dp),
        onClick = { offset ->
            annotatedString.getStringAnnotations(offset, offset)
                .firstOrNull()?.let { stringAnnotation ->
                    if (isval) {
                        onTextSelected(stringAnnotation.item)
                        Toast.makeText(context, "Email Sent", Toast.LENGTH_SHORT).show()
                    }
                    else
                        Toast.makeText(context,"Email Required", Toast.LENGTH_SHORT).show()
                }
        }
    )
}

@Composable
fun ClickableTextComp3 (value: String, onTextSelected: (String)->Unit) {
    val dont = "Don't have an account? "
    val signup = "Signup"

    val annotatedString = buildAnnotatedString {
        append(dont)
        withStyle(style = SpanStyle(color = Purple40)){
            pushStringAnnotation(tag = signup, annotation = signup)
            append(signup)
        }
    }
    ClickableText(text = annotatedString,
        modifier = Modifier
            .fillMaxWidth()
            .offset(x = 60.dp),
        onClick = { offset ->
            annotatedString.getStringAnnotations(offset, offset)
                .firstOrNull()?.also { span ->
                    if((span.item == signup)){
                        onTextSelected(span.item)
                    }
                }
            Router.navigateTo(Screen.RegisterScreen)
        }
    )
}

@Composable
fun ClickableTextComp4 (onTextSelected: (String)->Unit) {
    val already = "Already have an account? "
    val login = "Login"

    val annotatedString = buildAnnotatedString {
        append(already)
        withStyle(style = SpanStyle(color = Purple40)){
            pushStringAnnotation(tag = login, annotation = login)
            append(login)
        }
    }
    ClickableText(text = annotatedString,
        modifier = Modifier
            .fillMaxWidth()
            .offset(x = 60.dp),
        onClick = { offset ->
            annotatedString.getStringAnnotations(offset, offset)
                .firstOrNull()?.also { span ->
                    if((span.item == login)){
                        onTextSelected(span.item)
                    }
                }
            Router.navigateTo(Screen.LoginScreen)
        }
    )
}

@Composable
fun StyleText(text: String, color: Color) {
    Text(text = "$text",
        style = TextStyle(
            //drawStyle = Stroke(width = 6f, join = StrokeJoin.Round),
            fontSize = 60.sp,
            fontWeight = FontWeight.ExtraBold,
            fontStyle = FontStyle.Italic,
            fontFamily = FontFamily.Cursive),
        color = color, textAlign = TextAlign.Center
    )
}

@Composable
fun KallKaroComp () {
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center) {
        StyleText(text = "K", color = Color.DarkGray)
        StyleText(text = "all", color = ng2)
        StyleText(text = "K", color = Color.DarkGray)
        StyleText(text = "aro", color = ng2)
    }
}

@Composable
fun DividerComp () {
    Row (modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {
        Divider(modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
            color = Color.Gray,
            thickness = 1.dp)
        Text(text = "OR",
            fontSize = 14.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp))
        Divider(modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
            color = Color.Gray,
            thickness = 1.dp)
    }
}

@Composable
fun CircularProgressIndicatorfun() {
    CircularProgressIndicator()
}


//@Preview(showBackground = true)
//@Composable
//fun Clickabletextprev() {
//
//}
