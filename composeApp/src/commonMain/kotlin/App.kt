
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Button
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fuel.Fuel
import fuel.get
import fuel.post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun App() {
    MaterialTheme {
        var statusMessage by remember { mutableStateOf("") }
        var expiration by remember { mutableStateOf("0") }

        CoroutineScope(Dispatchers.IO).launch {
            while(true) {
                val onResponse = Fuel.get("http://192.168.1.79:5000/state")
                statusMessage = onResponse.body
                println(onResponse.body)
                delay(5000)
            }
        }

        Column(modifier = Modifier
            .padding(20.dp)
            .fillMaxHeight()
        ) {
            Row(modifier = Modifier.padding(bottom = 20.dp)) {
                Button(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    onClick = {
                        runBlocking {
                            val response = Fuel.post("http://192.168.1.79:5000/reboot?target=server", body = "")
                            statusMessage = response.body
                            println(response.body)
                        }
                    }
                ) {
                    Text("Server ▶")
                }
                Button(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    onClick = {
                        runBlocking {
                            val response = Fuel.post("http://192.168.1.79:5000/reboot?target=nodes", body = "")
                            statusMessage = response.body
                            println(response.body)
                        }
                    }
                ) {
                    Text("Nodes ▶")
                }
                Button(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    onClick = {
                        runBlocking {
                            val rebootNodesResponse = Fuel.post("http://192.168.1.79:5000/reboot?target=nodes", body = "")
                            statusMessage = rebootNodesResponse.body
                            println(rebootNodesResponse.body)
                            val rebootServerResponse = Fuel.post("http://192.168.1.79:5000/reboot?target=server", body = "")
                            statusMessage = rebootServerResponse.body
                            println(rebootServerResponse.body)
                        }
                    }
                ) {
                    Text("All ▶")
                }

            }
            Row(modifier = Modifier.padding(bottom = 40.dp)) {
                TextField(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .widthIn(10.dp, 70.dp),
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
                    value = expiration,
                    onValueChange = {
                        if (it.length <= 3) expiration = it
                    }
                )
                Text(
                    modifier = Modifier
                        .align(alignment = Alignment.CenterVertically)
                        .padding(horizontal = 5.dp),
                    text = "(minutes)"
                )
                Button(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    onClick = {
                        runBlocking {
                            val expResponse = Fuel.post("http://192.168.1.79:5000/exp?seconds=${expiration.toInt() * 60}", body = "")
                            statusMessage = expResponse.body
                            println(expResponse.body)
                            val onResponse = Fuel.post("http://192.168.1.79:5000/on", body = "")
                            statusMessage = onResponse.body
                            println(onResponse.body)
                        }
                    }
                ) {
                    Text("On")
                }
                Button(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    onClick = {
                        GlobalScope.async {
                            val response = Fuel.post("http://192.168.1.79:5000/off", body = "")
                            statusMessage = response.body
                            println(response.body)
                        }
                    }
                ) {
                    Text("Off")
                }
            }
            Row {
                Button(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    onClick = {
                        runBlocking {
                            val expResponse = Fuel.post("http://192.168.1.79:5000/exp?seconds=30", body = "")
                            statusMessage = expResponse.body
                            println(expResponse.body)
                            val onResponse = Fuel.post("http://192.168.1.79:5000/on", body = "")
                            statusMessage = onResponse.body
                            println(onResponse.body)
                        }
                    }
                ) {
                    Text("30s")
                }
                Button(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    onClick = {
                        runBlocking {
                            val expResponse = Fuel.post("http://192.168.1.79:5000/exp?seconds=60", body = "")
                            statusMessage = expResponse.body
                            println(expResponse.body)
                            val onResponse = Fuel.post("http://192.168.1.79:5000/on", body = "")
                            statusMessage = onResponse.body
                            println(onResponse.body)
                        }
                    }
                ) {
                    Text("1m")
                }
                Button(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    onClick = {
                        runBlocking {
                            val expResponse = Fuel.post("http://192.168.1.79:5000/exp?seconds=300", body = "")
                            statusMessage = expResponse.body
                            println(expResponse.body)
                            val onResponse = Fuel.post("http://192.168.1.79:5000/on", body = "")
                            statusMessage = onResponse.body
                            println(onResponse.body)
                        }
                    }
                ) {
                    Text("5m")
                }
            }
            Row(
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(
                    modifier = Modifier
                        .align(alignment = Alignment.Bottom)
                        .padding(horizontal = 10.dp),
                    text = statusMessage
                )
            }
        }
    }
}