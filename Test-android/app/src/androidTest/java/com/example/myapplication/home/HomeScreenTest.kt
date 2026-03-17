package com.example.myapplication.home

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
 * UI tests para HomeScreen según ui.MD:
 * - Contenido por defecto (elementos visibles al llegar a la pantalla)
 */
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun homeScreen_defaultContent_messageIsDisplayed() {
        navigateToHome()

        composeTestRule.onNodeWithTag("home_message").assertIsDisplayed()
        composeTestRule.onNodeWithText("Hola mundo!").assertIsDisplayed()
    }

    private fun navigateToHome() {
        composeTestRule.onNodeWithTag("login_email").performTextInput("user@example.com")
        composeTestRule.onNodeWithTag("login_password").performTextInput("123456")
        composeTestRule.onNodeWithTag("login_submit").performClick()
    }
}
