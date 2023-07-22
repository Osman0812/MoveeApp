package com.example.myapplication.ui.theme.screen

import android.annotation.SuppressLint
import android.graphics.drawable.ShapeDrawable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

@Composable
fun LoginScreen(){
    LoginScreenBackground()
    LoginScreenLogo()

   
    EditText()
    //Spacer(modifier = Modifier.padding(5.dp))
    PasswordText()

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
private fun EditText(){

    var email by remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .offset(x = 32.dp, y = 342.dp)


    ){

        Text(
            text = "Email",
            color = Color.White,
            fontSize = 14.sp,
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
                .bottomBorder(1.dp, Color.Gray),
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
        .offset(x = 32.dp, y = 409.dp)


    ){

        Text(
            text = "Password",
            color = Color.White,
            fontSize = 14.sp,
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
                .bottomBorder(1.dp, Color.Gray),
            visualTransformation = PasswordVisualTransformation(),
            textStyle = TextStyle(Color.White),
            
            )
        Image(painterResource(
            id = R.drawable.ic_eye),
            "content_description_ic_eye",
            modifier = Modifier.offset(x = 290.dp, y = (-25).dp)
        )
    }
}

@Composable
fun Modifier.bottomBorder(strokeWidth: Dp, color: Color) = composed(
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


