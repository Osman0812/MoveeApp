package com.example.myapplication.ui.theme.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.data.remote.model.ValidationRequest
import com.example.myapplication.util.extension.bottomBorder
import com.example.myapplication.ui.theme.theme.bottomViewColor
import com.example.myapplication.ui.theme.theme.vibrantBlue
import com.example.myapplication.viewmodel.AuthViewModel

@Composable

fun LoginScreen(authViewModel: AuthViewModel) {

    val username by remember {
        mutableStateOf("")
    }
    val password by remember {
        mutableStateOf("")
    }


    LoginScreenBackground()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 35.dp, end = 35.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.padding(40.dp))
        LoginScreenLogo()
        Spacer(modifier = Modifier.padding(40.dp))
        EmailText()
        Spacer(modifier = Modifier.padding(15.dp))
        PasswordText()
        Spacer(modifier = Modifier.padding(5.dp))
        ForgotPasswordText()
        Spacer(modifier = Modifier.padding(40.dp))
        LoginButton(authViewModel)
        Spacer(modifier = Modifier.padding(bottom = 25.dp))
        BottomView()
    }
}

@Composable
private fun LoginScreenLogo() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "content_description_logo",
            modifier = Modifier
        )
    }
}

@Composable
private fun LoginScreenBackground() {
    Image(
        painter = painterResource(id = R.drawable.ic_login_background),
        contentDescription = "content_description_login_screen_background",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
    )
}

@Composable
private fun EmailText() {
    var email by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.email),
            color = Color.White,
            fontSize = 12.sp,
            modifier = Modifier,
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.padding(10.dp))
        BasicTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .bottomBorder(1.dp, bottomViewColor),
            visualTransformation = VisualTransformation.None,
            textStyle = TextStyle(Color.White),
        )
    }
}

@Composable
private fun PasswordText() {
    var password by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.password),
            color = Color.White,
            fontSize = 12.sp,
        )
        Spacer(modifier = Modifier.padding(6.dp))
        Image(
            painterResource(
                id = R.drawable.ic_eye
            ),
            "content_description_ic_eye",
            modifier = Modifier
                .fillMaxWidth(),
            alignment = Alignment.CenterEnd
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            BasicTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .bottomBorder(1.dp, bottomViewColor),
                visualTransformation = PasswordVisualTransformation(),
                textStyle = TextStyle(Color.White),
                singleLine = true
            )
        }
    }
}

@Composable
private fun ForgotPasswordText() {
    Text(
        text = stringResource(id = R.string.forgot_password),
        style = TextStyle(Color.White),
        fontSize = 12.sp,
        modifier = Modifier.fillMaxSize(),
        textAlign = TextAlign.End
    )
}

@Composable
private fun LoginButton(authViewModel: AuthViewModel) {

    val name = "Osman0812"
    val password = "Memati2013"

    val scope = rememberCoroutineScope()
    val tokenState by authViewModel.requestToken.observeAsState()
    val validation by authViewModel.validationResponse.observeAsState()
    Button(
        onClick = {

           authViewModel.createRequestToken()

                  },
        colors = ButtonDefaults.buttonColors(Color.White),
        modifier = Modifier.fillMaxWidth(),
        shape = RectangleShape,
    ) {
        Text(
            text = stringResource(id = R.string.login),
            color = vibrantBlue,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontWeight = FontWeight.Bold
            )
        )
    }


    /*

            var token = ""
            if (tokenState != null){
                tokenState?.let {state ->
                    if (state.isSuccessful) {
                        scope.launch {
                            token = state.body()?.request_token.toString()
                            if (!token.isNullOrEmpty()) {
                                println("Received Token: $token")
                            } else {
                                println("Failed to get a valid token!")
                            }
                        }.isCompleted.let {
                            if (it){
                                val user = ValidationRequest(name,password,token)
                                authViewModel.validateRequestToken(user)
                                validation?.let {response ->
                                    if (response.isSuccessful){
                                        val success = response.body()?.success
                                        if (success == true){
                                            println("Succeed!")

                                        }else{
                                            println("Failed response")
                                        }
                                    }
                                }
                            }
                        }


                    } else {
                        println("Failed to get token: ${state.errorBody()?.string()}")
                    }
                }
            }else{
                println("Failed get token!")
            }

     */


    tokenState?.let { state ->
        if (state.isSuccessful) {
            val token = state.body()?.request_token
            if (!token.isNullOrEmpty()) {
                println("Received Token: $token")
                val user = ValidationRequest(name, password, token)
                authViewModel.performValidation(name,password,token)
            } else {
                println("Failed to get a valid token!")
            }
        } else {
            println("Ez")
        }
    }
/*
// Observing validation LiveData
    validation?.let { response ->
        if (response.isSuccessful) {
            val success = response.body()?.success
            if (success == true) {
                println("Succeed!")
            } else {
                println("Failed response")
            }
        }
    }


 */





}


@Composable
private fun BottomView() {
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 35.dp)
    ) {
        Text(
            text = stringResource(id = R.string.dont_have_an_account),
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                color = bottomViewColor
            )
        )
        Spacer(modifier = Modifier.padding(2.dp))
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




