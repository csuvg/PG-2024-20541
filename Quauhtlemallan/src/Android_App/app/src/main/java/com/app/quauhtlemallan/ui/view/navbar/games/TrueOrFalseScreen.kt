package com.app.quauhtlemallan.ui.view.navbar.games

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.app.quauhtlemallan.R
import com.app.quauhtlemallan.data.model.Question
import com.app.quauhtlemallan.ui.theme.crimsonRed
import com.app.quauhtlemallan.ui.theme.cinzelFontFamily
import com.app.quauhtlemallan.ui.theme.mossGreen
import com.app.quauhtlemallan.ui.theme.navyBlue
import com.app.quauhtlemallan.ui.viewmodel.TrueFalseGameViewModel

@Composable
fun TrueFalseGameScreen(
    viewModel: TrueFalseGameViewModel,
    navController: NavHostController,
    onGameEnd: () -> Unit
) {
    val questions by viewModel.questions.collectAsState()
    val currentQuestionIndex by viewModel.currentQuestionIndex.collectAsState()
    val timer by viewModel.timer.collectAsState()
    val correctAnswers by viewModel.correctAnswers.collectAsState()
    val gameEnded by viewModel.gameEnded.collectAsState()
    val selectedAnswer by viewModel.selectedAnswer.collectAsState()

    var showInfoDialog by remember { mutableStateOf(false) }
    var extraInfo by remember { mutableStateOf("") }

    val chapinismosAciertos = listOf(
        "¡Buenísimo!",
        "¡Sos pilas!",
        "¡Chilerísimo!",
        "¡Sos lo máximo!",
        "¡Estuviste practicando eh!",
        "¡Qué chilero!",
        "¡Súper, sigue así!",
        "¡Ahuevo que sí!",
        "¡Sos la mera tos!",
        "¡Qué maciz@!"
    )

    val chapinismosFallos = listOf(
        "¡Chispudo, hay que practicar!",
        "¡Aguas con esa!",
        "¡No la hiciste!",
        "¡Mmmm casi, sigue intentando!",
        "¡No fregues, intenta de nuevo!",
        "¡Dejate de babosadas, intentalo otra vez!",
        "¡Púchica, ¿qué te pasó?!",
        "¡Andás perdid@, mano!",
        "¡Puchis, ¿qué pasó master?!"
    )

    if (gameEnded) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Respuestas correctas: $correctAnswers / ${questions.size}",
                fontFamily = cinzelFontFamily,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { onGameEnd() }) {
                Text("Volver a juegos", fontFamily = cinzelFontFamily, fontWeight = FontWeight.Normal)
            }
        }
    } else {
        if (questions.isNotEmpty()) {
            val currentQuestion = questions[currentQuestionIndex]

            if (showInfoDialog) {
                viewModel.pauseTimer()
                AlertDialog(
                    onDismissRequest = {
                        showInfoDialog = false
                        viewModel.resumeTimer()
                        viewModel.delayNextQuestion()
                    },
                    title = {
                        Text(text = chapinismosFallos.random())
                    },
                    text = {
                        Text(text = "Respuesta correcta: ${currentQuestion.correcta}. $extraInfo")
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                showInfoDialog = false
                                viewModel.resumeTimer()
                                viewModel.delayNextQuestion()
                            }
                        ) {
                            Text("Cerrar")
                        }
                    }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_quit),
                        contentDescription = "Regresar",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                navController.navigateUp()
                            }
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LinearProgressIndicator(
                            progress = timer / 20f,
                            modifier = Modifier
                                .weight(1f)
                                .height(8.dp),
                            color = Color(0xFF4CAF50)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = "${timer}s",
                            fontSize = 16.sp,
                            fontFamily = cinzelFontFamily,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }

                Text(
                    text = currentQuestion.pregunta,
                    fontSize = 20.sp,
                    fontFamily = cinzelFontFamily,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp)
                )

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        AnswerTOFButton(
                            text = "Verdadero",
                            isSelected = selectedAnswer == "Verdadero",
                            isCorrect = "Verdadero" == currentQuestion.correcta,
                            defaultColor = navyBlue, // Azul
                            isOtherSelected = selectedAnswer != null && selectedAnswer != "Verdadero",
                            modifier = Modifier.weight(1f)
                                .height(450.dp),
                            onClick = {
                                handleAnswer("Verdadero", currentQuestion, viewModel) { info ->
                                    extraInfo = info
                                    showInfoDialog = true
                                }
                            }
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        // Botón "Falso"
                        AnswerTOFButton(
                            text = "Falso",
                            isSelected = selectedAnswer == "Falso",
                            isCorrect = "Falso" == currentQuestion.correcta,
                            defaultColor = crimsonRed,
                            isOtherSelected = selectedAnswer != null && selectedAnswer != "Falso",
                            modifier = Modifier.weight(1f)
                                .height(450.dp),
                            onClick = {
                                handleAnswer("Falso", currentQuestion, viewModel) { info ->
                                    extraInfo = info
                                    showInfoDialog = true
                                }
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "${currentQuestionIndex + 1} / ${questions.size}",
                    fontSize = 18.sp,
                    fontFamily = cinzelFontFamily,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = Color.Black
                )
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

fun handleAnswer(
    answer: String,
    question: Question,
    viewModel: TrueFalseGameViewModel,
    showExtraInfo: (String) -> Unit
) {
    val isCorrect = viewModel.selectAnswer(answer)
    if (!isCorrect) {
        viewModel.pauseTimer()
        showExtraInfo(question.datoExtra)
    } else {
        viewModel.delayNextQuestion()
    }
}

@Composable
fun AnswerTOFButton(
    text: String,
    isSelected: Boolean,
    isCorrect: Boolean,
    defaultColor: Color,
    isOtherSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit) {
    val backgroundColor = when {
        isSelected && isCorrect -> mossGreen
        isSelected && !isCorrect -> crimsonRed
        isOtherSelected -> Color.LightGray
        else -> defaultColor
    }

    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
        )
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontFamily = cinzelFontFamily,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.fillMaxWidth()
        )
    }
}