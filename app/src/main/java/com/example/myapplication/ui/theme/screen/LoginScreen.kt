package com.example.myapplication.ui.theme.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.bottom_view_color
import com.example.myapplication.ui.theme.vibrant_blue

@Composable
fun LoginScreen(){
    LoginScreenBackground()
    LoginScreenLogo()
    EmailText()
    PasswordText()
    ForgotPasswordText()
    LoginButton()
    BottomView()
}

@Composable
fun LoginScreenLogo(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "content_description_logo",
            modifier = Modifier
                .height(149.dp)
                .width(106.dp)
                .offset(y = (-165).dp)
        )
    }
}
@Composable
fun LoginScreenBackground(){
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.ic_login_background),
            contentDescription = "content_description_login_screen_background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun EmailText(){
    var email by remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .offset(x = 32.dp, y = 342.dp)
    ){
        Text(
            text = stringResource(id = R.string.email),
            color = Color.White,
            fontSize = 12.sp,
            modifier = Modifier
                .padding(bottom = 4.dp)
        )
        BasicTextField(
            value = email,
            onValueChange = {email = it},
            modifier = Modifier
                .width(310.dp)
                .height(32.dp)
                .background(Color.Transparent)
                .bottomBorder(1.dp, bottom_view_color),
            visualTransformation = VisualTransformation.None,
            textStyle = TextStyle(Color.White),
        )
    }
}

@Composable
private fun PasswordText(){
    var password by remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .offset(x = 32.dp, y = 415.dp)
    ){
        Text(
            text = stringResource(id = R.string.password),
            color = Color.White,
            fontSize = 12.sp,
            modifier = Modifier
                .padding(bottom = 4.dp)
        )
        BasicTextField(
            value = password,
            onValueChange = {password = it},
            modifier = Modifier
                .width(310.dp)
                .height(32.dp)
                .background(Color.Transparent)
                .bottomBorder(1.dp, bottom_view_color),
            visualTransformation = PasswordVisualTransformation(),
            textStyle = TextStyle(Color.White),
        )
        Image(painterResource(
            id = R.drawable.ic_eye),
            "content_description_ic_eye",
            modifier = Modifier.offset(x = 290.dp, y = (-22).dp)
        )
    }
}

@Composable
fun ForgotPasswordText(){
    Text(
        text = stringResource(id = R.string.forgot_password),
        style = TextStyle(Color.White),
        modifier = Modifier.offset(x = 265.dp, y = 495.dp),
        fontSize = 12.sp
    )
}

@Composable
fun LoginButton(){
    Button(onClick = { /*TODO*/ },
        colors = ButtonDefaults.buttonColors(Color.White),
        modifier = Modifier
            .padding(46.dp)
            .fillMaxWidth()
            .offset(y = 515.dp),
        shape = RectangleShape
    ) {
        Text(
            text = stringResource(id = R.string.login),
            color = vibrant_blue,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
private fun BottomView(){
    Row(
        modifier = Modifier
            .padding(start = 105.dp)
            .fillMaxWidth()
            .offset(y = 630.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        Text(
            text = stringResource(id = R.string.dont_have_an_account),
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                color = bottom_view_color
            )
        )
        Text(
            text = stringResource(id = R.string.register_now),
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            style = TextStyle(
                color = Color.White
            )
        )
    }
}


fun Modifier.bottomBorder(strokeWidth: Dp, color: Color) = composed { composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }
        Modifier.drawBehind {
            val width = size.width
            val height = size.height - strokeWidthPx
            drawLine(
                color = color,
                start = Offset(x = 0f, y = height),
                end = Offset(x = width , y = height),
                strokeWidth = strokeWidthPx
            )
        }
    }
)
}


