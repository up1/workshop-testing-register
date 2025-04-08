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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.KeyboardType
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

    var nameMsg by remember { mutableStateOf("") }
    var ageMsg by remember { mutableStateOf("") }
    var phoneMsg by remember { mutableStateOf("") }
    var emailMsg by remember { mutableStateOf("") }
    var passwordMsg by remember { mutableStateOf("") }

    fun validate(): Boolean {
        var isValid = true

        // เคลียร์ข้อความก่อน
        nameMsg = ""
        ageMsg = ""
        phoneMsg = ""
        emailMsg = ""
        passwordMsg = ""

        // ตรวจสอบ name
        if (name.isBlank()) {
            nameMsg = "กรุณากรอกชื่อ"
            isValid = false
        } else if (!name.matches(Regex("^[ก-๙\\s]+$"))) {
            nameMsg = "กรุณากรอกภาษาไทยเท่านั้น"
            isValid = false
        }

        // ตรวจสอบ age
        if (age.isBlank()) {
            ageMsg = "กรุณากรอกอายุ"
            isValid = false
        } else {
            val ageValue = age.toIntOrNull()
            if (ageValue == null || ageValue > 60) {
                ageMsg = "อายุไม่เกิน 60 ปี"
                isValid = false
            }
        }

        // ตรวจสอบ phone
        if (phone.isBlank()) {
            phoneMsg = "กรุณากรอกเบอร์โทร"
            isValid = false
        } else if (!phone.matches(Regex("^\\d+$"))) {
            phoneMsg = "กรุณากรอกตัวเลขเท่านั้น"
            isValid = false
        } else if (!phone.startsWith("08") && !phone.startsWith("09")) {
            phoneMsg = "เบอร์โทรไม่ถูกต้อง ต้องขึ้นต้นด้วย 08, 09"
            isValid = false
        }

        // ตรวจสอบ email
        if (email.isBlank()) {
            emailMsg = "กรุณากรอกอีเมล"
            isValid = false
        } else {
            val emailPattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
            if (!emailPattern.matcher(email).matches()) {
                emailMsg = "อีเมลไม่ถูกต้อง"
                isValid = false
            }
        }

        // ตรวจสอบ password
        if (password.isBlank()) {
            passwordMsg = "กรุณากรอกรหัสผ่าน"
            isValid = false
        } else if (password.length < 8) {
            passwordMsg = "รหัสผ่านต้องมีอย่างน้อย 8 ตัวอักษร"
            isValid = false
        }

        return isValid
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // ชื่อ
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("ชื่อ (ภาษาไทย)") },
            modifier = Modifier.testTag("nameField")
        )
        if (nameMsg.isNotEmpty()) {
            Text(
                text = nameMsg,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.testTag("nameMessage")
            )
        }

        // อายุ
        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("อายุ") },
            modifier = Modifier.testTag("ageField"),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        if (ageMsg.isNotEmpty()) {
            Text(
                text = ageMsg,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.testTag("ageMessage")
            )
        }

        // เบอร์โทร
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("เบอร์โทร") },
            modifier = Modifier.testTag("phoneField"),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        if (phoneMsg.isNotEmpty()) {
            Text(
                text = phoneMsg,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.testTag("phoneMessage")
            )
        }

        // Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.testTag("emailField"),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
        )
        if (emailMsg.isNotEmpty()) {
            Text(
                text = emailMsg,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.testTag("emailMessage")
            )
        }

        // Password
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("รหัสผ่าน") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.testTag("passwordField")
        )
        if (passwordMsg.isNotEmpty()) {
            Text(
                text = passwordMsg,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.testTag("passwordMessage")
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                validate()
            },
            modifier = Modifier.testTag("submitButton")
        ) {
            Text("สมัครสมาชิก")
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