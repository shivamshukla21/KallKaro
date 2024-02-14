@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.kallkaro.Components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.example.kallkaro.Data.RegistrationViewModel
import com.example.kallkaro.Navigation.Router
import com.example.kallkaro.Navigation.Screen
import com.example.kallkaro.ui.theme.Purple40
import com.example.kallkaro.ui.theme.bgcol
import com.example.kallkaro.ui.theme.colorText
import com.example.kallkaro.ui.theme.ng2

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
fun TextField (labelvalue: String, painterResource: ImageVector, onTextSelected: (String) -> Unit) {
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
            Icon(painterResource, contentDescription = "")
        }
    )
}

@Composable
fun EmTextField (labelvalue: String, painterResource: ImageVector, onTextSelected: (String) -> Unit) {
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
            Icon(painterResource, contentDescription = "")
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
            Icon(Icons.Default.Lock, contentDescription = "")
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
        Text(
            text = "Register",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun LogButton () {
    Log.d(true.toString(),"value of log status")
    Button(onClick = { /*TODO*/ },
//        enabled = st,
//        enabled = isEmailValid(emState.value) && pswdState.value.length>5,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(18.dp),
        colors = ButtonDefaults.buttonColors(ng2)
    ) {
        Text(text = "Login",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold)
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
//            .firstOrNull()?.also { span ->
//                if((span.item == term) || (span.item == privpol)){
//                  onTextSelected(span.item)
//                }
//            }
//            Router.navigateTo(Screen.RegisterScreen)
            .firstOrNull()?.let { stringAnnotation ->
                uriHandler.openUri(stringAnnotation.item)
            }
    })
}

@Composable
fun ClickableTextComp2 (value: String, onTextSelected: (String)->Unit) {
    val forgot = "Forgot details? Let us help you "
    val regain = "regain access!"

    val uriHandler = LocalUriHandler.current
    val annotatedString = buildAnnotatedString {
        append(forgot)
        withStyle(style = SpanStyle(color = Purple40, fontSize = TextUnit.Unspecified)){
            pushStringAnnotation(tag = regain, annotation = "https://support.google.com/accounts")
            append(regain)
        }
    }
    ClickableText(text = annotatedString,
        modifier = Modifier.offset(x=10.dp),
        onClick = { offset ->
            annotatedString.getStringAnnotations(offset, offset)
                .firstOrNull()?.let { stringAnnotation ->
                    uriHandler.openUri(stringAnnotation.item)
                }
        })
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

//@Preview(showBackground = true)
//@Composable
//fun Clickabletextprev() {
//
//}
