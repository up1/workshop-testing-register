package com.demo.registerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.demo.registerapp.ui.theme.RegisterAppTheme
import java.util.regex.Pattern

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegisterAppTheme {
                RegistrationForm()
            }
        }
    }
}

@Composable
fun RegistrationForm() {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf("") }

    fun validate(): Boolean {
        errorMessage = ""

        if (!name.matches(Regex("^[ก-๙\\s]+$"))) {
            errorMessage = "กรุณากรอกภาษาไทยเท่านั้น"
            return false
        }

        val ageValue = age.toIntOrNull()
        if (ageValue == null || ageValue > 60) {
            errorMessage = "อายุไม่เกิน 60 ปี"
            return false
        }

        if (!phone.matches(Regex("^\\d+$"))) {
            errorMessage = "กรุณากรอกตัวเลขเท่านั้น"
            return false
        }

        val emailPattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
        if (!emailPattern.matcher(email).matches()) {
            errorMessage = "อีเมลไม่ถูกต้อง"
            return false
        }

        if (password.length < 8) {
            errorMessage = "รหัสผ่านต้องมีอย่างน้อย 8 ตัวอักษร"
            return false
        }

        return true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("ชื่อ (ภาษาไทย)") },
            modifier = Modifier.testTag("nameField")
        )
        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("อายุ") },
            modifier = Modifier.testTag("ageField")
        )
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("เบอร์โทร") },
            modifier = Modifier.testTag("phoneField")
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.testTag("emailField")
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("รหัสผ่าน") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.testTag("passwordField")
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                if (validate()) {
                    errorMessage = "สมัครสมาชิกสำเร็จ!"
                }
            },
            modifier = Modifier.testTag("submitButton")
        ) {
            Text("สมัครสมาชิก")
        }

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = if (errorMessage.contains("สำเร็จ")) Color.Green else Color.Red,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .testTag("errorText")
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RegisterAppTheme {
        RegistrationForm()
    }
}