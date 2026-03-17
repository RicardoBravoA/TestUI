package com.example.myapplication.login

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.assertIsDisplayed
import com.example.myapplication.MainActivity
import org.junit.Rule
import org.junit.Test

/**
 * UI tests para LoginScreen según ui.MD:
 * - Contenido por defecto
 * - Validaciones (email inválido, contraseña corta)
 * - Flujo exitoso (navegación a Home)
 */
class LoginScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun loginScreen_defaultContent_elementsAreDisplayed() {
        composeTestRule.onNodeWithTag("login_title").assertIsDisplayed()
        composeTestRule.onNodeWithTag("login_email").assertIsDisplayed()
        composeTestRule.onNodeWithTag("login_password").assertIsDisplayed()
        composeTestRule.onNodeWithTag("login_submit").assertIsDisplayed()
        composeTestRule.onNodeWithText("Iniciar sesión").assertIsDisplayed()
        composeTestRule.onNodeWithText("Ingresar").assertIsDisplayed()
    }

    @Test
    fun loginScreen_invalidEmail_showsErrorAndDoesNotNavigate() {
        composeTestRule.onNodeWithTag("login_email").performTextInput("invalid")
        composeTestRule.onNodeWithTag("login_password").performTextInput("123456")
        composeTestRule.onNodeWithTag("login_submit").performClick()

        composeTestRule.onNodeWithText("Introduce un email válido").assertIsDisplayed()
    }

    @Test
    fun loginScreen_emptyEmail_showsError() {
        composeTestRule.onNodeWithTag("login_password").performTextInput("123456")
        composeTestRule.onNodeWithTag("login_submit").performClick()

        composeTestRule.onNodeWithText("El email es obligatorio").assertIsDisplayed()
    }

    @Test
    fun loginScreen_shortPassword_showsErrorAndDoesNotNavigate() {
        composeTestRule.onNodeWithTag("login_email").performTextInput("user@example.com")
        composeTestRule.onNodeWithTag("login_password").performTextInput("12345")
        composeTestRule.onNodeWithTag("login_submit").performClick()

        composeTestRule.onNodeWithText("La contraseña debe tener al menos 6 caracteres").assertIsDisplayed()
    }

    @Test
    fun loginScreen_emptyPassword_showsError() {
        composeTestRule.onNodeWithTag("login_email").performTextInput("user@example.com")
        composeTestRule.onNodeWithTag("login_submit").performClick()

        composeTestRule.onNodeWithText("La contraseña es obligatoria").assertIsDisplayed()
    }

    @Test
    fun loginScreen_validCredentials_navigatesToHome() {
        composeTestRule.onNodeWithTag("login_email").performTextInput("user@example.com")
        composeTestRule.onNodeWithTag("login_password").performTextInput("123456")
        composeTestRule.onNodeWithTag("login_submit").performClick()

        composeTestRule.onNodeWithTag("home_message").assertIsDisplayed()
        composeTestRule.onNodeWithText("Hola mundo!").assertIsDisplayed()
    }
}
