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
import androidx.compose.foundation.lazy.items
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
import android.util.Log;
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton


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
    var shouldShowAddTaskDialog = remember { mutableStateOf(false) }
    var shouldShowDelTaskDialog = remember { mutableStateOf(true) }
    var tasks by remember { mutableStateOf(listOf<String>()) }
    if(shouldShowAddTaskDialog.value) {
        AddTaskDialog(
            shouldShowDialog = shouldShowAddTaskDialog,
            onTaskAdded = { newTask ->
                tasks = tasks + newTask
            }
        );
    }
    DeleteTaskDialog(shouldShowDialog = shouldShowDelTaskDialog)
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
                modifier = Modifier.padding(top = 85.dp)
            ) {
                LazyColumn {
                    items(tasks) { task ->
                        TaskRow(taskName =  task)
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
                        onClick = { shouldShowAddTaskDialog.value = true },
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

@Composable
fun TaskRow(taskName : String) {
    var isChecked by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Checkbox(
                checked = isChecked,
                onCheckedChange = { isChecked = it } )
            Text(
                fontSize = 25.sp,
                text = taskName
            )
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = "Delete",
            )
        }
    }
}

@Composable
fun AddTaskDialog(shouldShowDialog: MutableState<Boolean>,onTaskAdded: (String) -> Unit) {
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
                            onTaskAdded(taskNameState.value)
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

@Composable
fun DeleteTaskDialog(shouldShowDialog: MutableState<Boolean>) {
    if(shouldShowDialog.value) {
        AlertDialog(
            onDismissRequest = { shouldShowDialog.value = false },
            title = {Text(text = "Delete Task")},
            confirmButton = {
                    Box(){
                        Button(
                            onClick = { shouldShowDialog.value = false },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                        ) {
                            Text(
                                text = "Cancel",
                                color = Color.White,
                            )
                        }
                        Button(onClick = { shouldShowDialog.value = false }) {
                            Text(
                                text = "Add",
                                color = Color.White
                            )
                        }
                    }
            })
    }
}