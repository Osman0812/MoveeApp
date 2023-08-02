package com.example.myapplication.ui.screen.login


import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.data.remote.model.ValidationRequest
import com.example.myapplication.ui.Screen
import com.example.myapplication.ui.theme.bottomViewColor
import com.example.myapplication.ui.theme.vibrantBlue
import com.example.myapplication.data.datastore.SessionManagerDataStore
import com.example.myapplication.util.extension.Constants
import com.example.myapplication.util.extension.ResultOf

@Composable
fun LoginScreen(authViewModel: LoginViewModel, navHostController: NavHostController) {
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
        val userName = emailText()
        Spacer(modifier = Modifier.padding(15.dp))
        val password = passwordText()
        Spacer(modifier = Modifier.padding(5.dp))
        ForgotPasswordText(navHostController)
        Spacer(modifier = Modifier.padding(40.dp))
        LoginButton(authViewModel, userName, password, navHostController)
        Spacer(modifier = Modifier.padding(bottom = 25.dp))
        BottomView(navHostController)
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
private fun emailText(): String {
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
                .background(Color.Transparent),
            visualTransformation = VisualTransformation.None,
            textStyle = TextStyle(Color.White),
            singleLine = true
        )
        IndicatorLine(
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth()
                .height(1.dp)
        )
    }
    return email
}

@Composable
private fun passwordText(): String {
    var password by remember {
        mutableStateOf("")
    }
    var passwordVisible by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = stringResource(id = R.string.password),
            color = Color.White,
            fontSize = 12.sp,
        )
        Spacer(modifier = Modifier.padding(6.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                BasicTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier
                        .background(Color.Transparent)
                        .fillMaxWidth(),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    textStyle = TextStyle(Color.White),
                    singleLine = true
                )
                Image(
                    painterResource(
                        id = R.drawable.ic_eye
                    ),
                    "content_description_ic_eye",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable {
                            passwordVisible = !passwordVisible
                        },
                    alignment = Alignment.CenterEnd
                )
            }
            IndicatorLine(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth()
                    .height(1.dp)
            )
        }
    }
    return password
}

@Composable
private fun ForgotPasswordText(navHostController: NavHostController) {
    ClickableText(
        modifier = Modifier.fillMaxWidth(),
        text = AnnotatedString(stringResource(id = R.string.forgot_password)),
        onClick = {
            navHostController.navigate("${Screen.WebViewScreen.route}/${Constants.FORGOT_PASSWORD_URL}")
        },
        style = TextStyle(
            color = Color.White,
            fontSize = 12.sp,
            textAlign = TextAlign.End
        )
    )
}

@Composable
private fun LoginButton(
    authViewModel: LoginViewModel,
    userName: String,
    password: String,
    navHostController: NavHostController
) {
    val validation by authViewModel.sessionId.observeAsState(initial = ResultOf.Initial)
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }
    LaunchedEffect(validation) {
        when (validation) {
            is ResultOf.Initial -> {
                isLoading = false
            }
            is ResultOf.Loading -> {
                isLoading = true
            }
            is ResultOf.Success -> {
                println(validation.toString())
                saveSessionId(context, validation.toString())
                navigationToMainScreen(navHostController)
                isLoading = false
            }
            is ResultOf.Error -> {
                Toast.makeText(context, "Failed!", Toast.LENGTH_LONG).show()
                isLoading = false
            }
        }
    }
    Button(
        onClick = {
            val user = ValidationRequest(userName, password)
            isLoading = true
            authViewModel.performValidation(user)
        },
        colors = ButtonDefaults.buttonColors(Color.White),
        modifier = Modifier.fillMaxWidth(),
        shape = RectangleShape,
    ) {
        if (isLoading) {
            LoadingIndicator()
        } else {
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
    }
}

@Composable
private fun LoadingIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun BottomView(navHostController: NavHostController) {
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
        ClickableText(
            text = AnnotatedString(stringResource(id = R.string.register_now)),
            onClick = {
                navHostController.navigate("${Screen.WebViewScreen.route}/${Constants.REGISTER_URL}")
            },
            style = TextStyle(
                color = Color.White,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
private fun IndicatorLine(modifier: Modifier = Modifier) {
    Canvas(
        modifier = modifier
    ) {
        drawLine(
            color = Color(0x4cabb4bd),
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            strokeWidth = 1.dp.toPx(),
            cap = StrokeCap.Butt
        )
    }
}

private fun navigationToMainScreen(navHostController: NavHostController) {
    navHostController.navigate(Screen.MainScreen.route)
}

private suspend fun saveSessionId(context: Context, sessionId: String) {
    val store = SessionManagerDataStore()
    store.writeToDataStore(context, sessionId)
}





