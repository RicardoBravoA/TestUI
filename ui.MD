Prompt para automatizar pruebas UI (escalable)
Copia y pega esto cuando quieras generar o actualizar los UI tests (incluyendo pantallas nuevas):

Contexto: App Android con Jetpack Compose, ViewModels y AndroidX Navigation. Las pruebas UI deben ejecutarse en emulador o dispositivo físico con connectedDebugAndroidTest.

Objetivo: Automatizar las pruebas UI de todas las pantallas de la app. El proceso debe ser el mismo para las pantallas actuales y para cualquier pantalla que se agregue después.

Instrucciones:

Inventariar pantallas

Revisar el grafo de navegación (p. ej. NavGraph.kt o equivalente) y listar todas las rutas/destinos.
Para cada destino, identificar: nombre del composable (p. ej. LoginScreen, HomeScreen), elementos interactivos (TextField, Button, etc.), textos visibles relevantes y a qué ruta(s) navega y bajo qué condición (éxito, error, back).
Añadir testTags en la UI

En cada pantalla, añadir Modifier.testTag("id_único") a:
Cada campo de entrada (ej. login_email, login_password).
Cada botón o elemento clickeable (ej. login_submit, home_back).
Opcional: contenedor o texto clave de la pantalla para assertions (ej. login_title, home_message).
Convención de nombres: {pantalla}_{elemento} (ej. login_email, home_primary_button). Usar el mismo nombre de pantalla que en el grafo de navegación (en minúsculas).
Escribir tests instrumentados (androidTest)

Una clase de test por pantalla o por flujo principal, en androidTest/, con nombre {NombrePantalla}ScreenTest o {Flujo}FlowTest.
Usar createAndroidComposeRule<MainActivity>() (o la Activity que hospeda el NavHost) para que todos los tests arranquen la app con navegación real.
Para cada pantalla cubrir:
Contenido por defecto: que los elementos esperados estén visibles (títulos, campos, botón principal).
Validaciones: si la pantalla tiene formulario o reglas (email, longitud, etc.), un test por cada tipo de error que muestre mensaje en UI (ej. email inválido, contraseña corta) y comprobar que el mensaje de error aparece y que no se navega.
Flujo exitoso: si la pantalla lleva a otra (login → home, formulario → lista, etc.), un test que rellene datos válidos, pulse el botón de acción y compruebe que se muestra la pantalla destino (p. ej. por texto característico o por testTag de la nueva pantalla).
Navegación back o secundaria: si aplica (botón “atrás”, “cancelar”), un test que verifique que vuelve a la pantalla anterior o a la ruta esperada.
Usar solo APIs de Compose Test (onNodeWithTag, onNodeWithText, performClick, performTextInput, assertIsDisplayed, etc.) y evitar Thread.sleep; apoyarse en la sincronización de Compose.
No duplicar la lógica de negocio: si el ViewModel ya tiene tests unitarios que validan reglas (email, contraseña), los UI tests solo comprueban que el mensaje correcto aparece en pantalla y que la navegación ocurre o no según el caso.
Cobertura de nuevas pantallas

Si en el proyecto aparecen pantallas nuevas (nuevas rutas en el NavHost), aplicar exactamente los mismos pasos 1–3 para esas pantallas: añadir testTag a elementos interactivos y textos clave, y crear o ampliar la clase {NombrePantalla}ScreenTest con: contenido por defecto, validaciones visibles, flujo exitoso y navegación si aplica.
Mantener la misma convención de testTag ({pantalla}_{elemento}) y la misma estructura de tests (contenido, validaciones, éxito, navegación).
Ejecución

Los tests deben poder ejecutarse con ./gradlew connectedDebugAndroidTest en un emulador o dispositivo físico conectado; no asumir Firebase Test Lab ni servicios externos a menos que se indique.