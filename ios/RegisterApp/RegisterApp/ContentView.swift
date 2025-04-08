import SwiftUI

struct ContentView: View {
    @State private var name = ""
    @State private var age = ""
    @State private var phone = ""
    @State private var email = ""
    @State private var password = ""
    @State private var address = ""

    // State สำหรับแต่ละ error
    @State private var nameError = ""
    @State private var ageError = ""
    @State private var phoneError = ""
    @State private var emailError = ""
    @State private var passwordError = ""
    @State private var addressError = ""
    @State private var successMessage = ""

    var body: some View {
        NavigationView {
            Form {
                Section(header: Text("สมัครสมาชิก")) {
                    Group {
                        TextField("ชื่อ (ภาษาไทย)", text: $name)
                            .accessibilityIdentifier("nameTextField")
                        if !nameError.isEmpty {
                            Text(nameError).foregroundColor(.red)
                                .accessibilityIdentifier("nameError")
                        }

                        TextField("อายุ", text: $age)
                            .keyboardType(.numberPad)
                            .accessibilityIdentifier("ageTextField")
                        if !ageError.isEmpty {
                            Text(ageError).foregroundColor(.red)
                                .accessibilityIdentifier("ageError")
                        }

                        TextField("เบอร์โทร", text: $phone)
                            .keyboardType(.numberPad)
                            .accessibilityIdentifier("phoneTextField")
                        if !phoneError.isEmpty {
                            Text(phoneError).foregroundColor(.red)
                                .accessibilityIdentifier("phoneError")
                        }

                        TextField("อีเมล", text: $email)
                            .keyboardType(.emailAddress)
                            .accessibilityIdentifier("emailTextField")
                        if !emailError.isEmpty {
                            Text(emailError).foregroundColor(.red)
                                .accessibilityIdentifier("emailError")
                        }

                        SecureField("รหัสผ่าน", text: $password)
                            .accessibilityIdentifier("passwordSecureField")
                        if !passwordError.isEmpty {
                            Text(passwordError).foregroundColor(.red)
                                .accessibilityIdentifier("passwordError")
                        }

                        TextField("ที่อยู่", text: $address)
                            .accessibilityIdentifier("addressTextField")
                        if !addressError.isEmpty {
                            Text(addressError).foregroundColor(.red)
                                .accessibilityIdentifier("addressError")
                        }
                    }
                }

                if !successMessage.isEmpty {
                    Section {
                        Text(successMessage).foregroundColor(.green)
                            .accessibilityIdentifier("successMessage")
                    }
                }

                Button("สมัครสมาชิก") {
                    validateForm()
                }
                .accessibilityIdentifier("submitButton")
            }
            .navigationTitle("Register")
        }
    }

    func validateForm() {
        // เคลียร์ error เดิม
        nameError = ""
        ageError = ""
        phoneError = ""
        emailError = ""
        passwordError = ""
        addressError = ""
        successMessage = ""

        var isValid = true

        if name.isEmpty {
            nameError = "กรุณากรอกชื่อ"
            isValid = false
        } else if !isThai(text: name) {
            nameError = "กรุณากรอกภาษาไทยเท่านั้น"
            isValid = false
        }

        if age.isEmpty {
            ageError = "กรุณากรอกอายุ"
            isValid = false
        } else if let a = Int(age), a > 60 {
            ageError = "อายุไม่เกิน 60 ปี"
            isValid = false
        }

        if phone.isEmpty {
            phoneError = "กรุณากรอกเบอร์โทร"
            isValid = false
        } else if !phone.allSatisfy({ $0.isNumber }) {
            phoneError = "กรุณากรอกตัวเลขเท่านั้น"
            isValid = false
        } else if !phone.hasPrefix("08") && !phone.hasPrefix("09") {
            phoneError = "เบอร์โทรไม่ถูกต้อง ต้องขึ้นต้นด้วย 08, 09"
            isValid = false
        }

        if email.isEmpty {
            emailError = "กรุณากรอกอีเมล"
            isValid = false
        } else if !isValidEmail(email) {
            emailError = "อีเมลไม่ถูกต้อง"
            isValid = false
        }

        if password.isEmpty {
            passwordError = "กรุณากรอกรหัสผ่าน"
            isValid = false
        } else if password.count < 8 {
            passwordError = "รหัสผ่านต้องมีอย่างน้อย 8 ตัวอักษร"
            isValid = false
        }

        if address.isEmpty {
            addressError = "กรุณากรอกที่อยู่"
            isValid = false
        }

        if isValid {
            successMessage = "สมัครสมาชิกสำเร็จ"
            // หรือส่งไป API จริงผ่าน URLSession
        }
    }

    func isThai(text: String) -> Bool {
        let regex = try! NSRegularExpression(pattern: "^[\\u0E00-\\u0E7F\\s]+$")
        return regex.firstMatch(in: text, range: NSRange(location: 0, length: text.utf16.count)) != nil
    }

    func isValidEmail(_ email: String) -> Bool {
        let emailRegEx = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}"
        return NSPredicate(format: "SELF MATCHES %@", emailRegEx).evaluate(with: email)
    }
}


#Preview {
    ContentView()
}
