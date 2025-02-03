package com.example.todo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.ui.theme.TodoTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoTheme {
                TodoList()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun TodoList() {
    var isChecked = remember { mutableStateOf(false) }
    var tasks by remember { mutableStateOf(mutableListOf<String>()) }
    if(shouldShowDialog.value) {
        AddTaskDialog(shouldShowDialog = shouldShowDialog);
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "TODO List") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6200EE),
                    titleContentColor = Color.White
                )
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp),
            ){
            Column(
                modifier = Modifier.padding(top = 65.dp)
            ) {
                LazyColumn{
                    tasks(tasks) { task ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Checkbox(
                                checked = isChecked.value,
                                onCheckedChange = {isChecked.value = it} )
                            Text(
                                fontSize = 25.sp,
                                text = "Project setup for android project."
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ){
                    FloatingActionButton(
                        modifier = Modifier
                            .align(Alignment.BottomEnd),
                        onClick = { shouldShowDialog.value = true },
                        containerColor = Color(0xFF6200EE),
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Favorite",
                        )
                    }
                }

            }

        }
    }
}

@Composable
fun taskRow() {

}

@Composable
fun AddTaskDialog(shouldShowDialog: MutableState<Boolean>) {
    if (shouldShowDialog.value) {
        val taskNameState = remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { shouldShowDialog.value = false },
            title = {Text(text = "Add task")},
            text = {
                TextField(
                    value = taskNameState.value,
                    onValueChange = {taskNameState.value = it},
                    label = { Text("Task name")},
                    singleLine = true
                    )
            },
            confirmButton = {
                Button(
                    onClick = {
                        shouldShowDialog.value = false
                        if (taskNameState.value.isNotBlank()){
                            tasks.add(taskNameState.value)
                            taskNameState.value = ""
                        }

                              },
                ) {
                    Text(
                        text = "Add",
                        color = Color.White
                    )
                }
            })
    }
}