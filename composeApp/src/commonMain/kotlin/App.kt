
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val server = "WIL-EMITTER-SRV"
const val port = "5000"
@Composable
fun App() {
    MaterialTheme {
        var statusMessage by remember { mutableStateOf("") }
        var expiration by remember { mutableStateOf("0") }
        val client = HttpClient(CIO)
        val scope = rememberCoroutineScope()

        scope.launch {
            withContext(Dispatchers.IO) {
                while (true) {
                    try {
                        val onResponse = client.get("http://$server:$port/state")
                        statusMessage = onResponse.bodyAsText()
                        println(statusMessage)
                        delay(5000)
                    } catch(e: Exception) {
                        statusMessage = "Error: ".plus(e.message.toString())
                    }
                }
            }
        }

        Column(modifier = Modifier
            .padding(20.dp)
            .fillMaxHeight()
        ) {
            Row(modifier = Modifier.padding(bottom = 20.dp)) {
                Button(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .size(100.dp, 100.dp),
                    onClick = {
                        scope.launch {
                            withContext(Dispatchers.IO) {
                                try {
                                    val response =
                                        client.post("http://$server:$port/reboot?target=server")
                                    statusMessage = response.bodyAsText()
                                    println(statusMessage)
                                } catch(e: Exception) {
                                    statusMessage = "Error: ".plus(e.message.toString())
                                }
                            }
                        }
                    }
                ) {
                    Text("Server ▶")
                }
                Button(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .size(100.dp, 100.dp),
                    onClick = {
                        scope.launch {
                            withContext(Dispatchers.IO) {
                                try {
                                    val response =
                                        client.post("http://$server:$port/reboot?target=nodes")
                                    statusMessage = response.bodyAsText()
                                    println(statusMessage)
                                } catch(e: Exception) {
                                    statusMessage = "Error: ".plus(e.message.toString())
                                }
                            }
                        }
                    }
                ) {
                    Text("Nodes ▶")
                }
                Button(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .size(100.dp, 100.dp),
                    onClick = {
                        scope.launch {
                            withContext(Dispatchers.IO) {
                                try {
                                    val rebootNodesResponse =
                                        client.post("http://$server:$port/reboot?target=nodes")
                                    statusMessage = rebootNodesResponse.bodyAsText()
                                    println(statusMessage)
                                    val rebootServerResponse =
                                        client.post("http://$server:$port/reboot?target=server")
                                    statusMessage = rebootServerResponse.bodyAsText()
                                    println(statusMessage)
                                } catch(e: Exception) {
                                    statusMessage = "Error: ".plus(e.message.toString())
                                }
                            }
                        }
                    }
                ) {
                    Text("All ▶")
                }

            }
            Spacer(modifier = Modifier.height(40.dp))
            Row(modifier = Modifier.padding(bottom = 20.dp))  {
                Button(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .size(100.dp, 100.dp),
                    onClick = {
                        scope.launch {
                            withContext(Dispatchers.IO) {
                                try {
                                    val expResponse =
                                        client.post("http://$server:$port/exp?seconds=30")
                                    statusMessage = expResponse.bodyAsText()
                                    println(statusMessage)
                                    val onResponse = client.post("http://$server:$port/on")
                                    statusMessage = onResponse.bodyAsText()
                                    println(statusMessage)
                                } catch(e: Exception) {
                                    statusMessage = "Error: ".plus(e.message.toString())
                                }
                            }
                        }
                    }
                ) {
                    Text("30s")
                }
                Button(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .size(100.dp, 100.dp),
                    onClick = {
                        scope.launch {
                            withContext(Dispatchers.IO) {
                                try {
                                    val expResponse = client.post("http://$server:$port/exp?seconds=60")
                                    statusMessage = expResponse.bodyAsText()
                                    println(statusMessage)
                                    val onResponse = client.post("http://$server:$port/on")
                                    statusMessage = onResponse.bodyAsText()
                                    println(statusMessage)
                                } catch(e: Exception) {
                                    statusMessage = "Error: ".plus(e.message.toString())
                                }
                            }
                        }
                    }
                ) {
                    Text("1m")
                }
                Button(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .size(100.dp, 100.dp),
                    onClick = {
                        scope.launch {
                            withContext(Dispatchers.IO) {
                                try {
                                    val expResponse = client.post("http://$server:$port/exp?seconds=300")
                                    statusMessage = expResponse.bodyAsText()
                                    println(statusMessage)
                                    val onResponse = client.post("http://$server:$port/on")
                                    statusMessage = onResponse.bodyAsText()
                                    println(statusMessage)
                                } catch(e: Exception) {
                                    statusMessage = "Error: ".plus(e.message.toString())
                                }
                            }
                        }
                    }
                ) {
                    Text("5m")
                }
            }
            Row {
                TextField(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .size(100.dp, 100.dp),
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
                    value = expiration,
                    onValueChange = {
                        if (it.length <= 3) expiration = it
                    }
                )
                Button(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .size(100.dp, 100.dp),
                    onClick = {
                        scope.launch {
                            withContext(Dispatchers.IO) {
                                try {
                                    val expResponse =
                                        client.post("http://$server:$port/exp?seconds=${expiration.toInt() * 60}")
                                    statusMessage = expResponse.bodyAsText()
                                    println(statusMessage)
                                    val onResponse = client.post("http://$server:$port/on")
                                    statusMessage = onResponse.bodyAsText()
                                    println(statusMessage)
                                } catch(e: Exception) {
                                    statusMessage = "Error: ".plus(e.message.toString())
                                }
                            }
                        }
                    }
                ) {
                    Text("On")
                }
                Button(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .size(100.dp, 100.dp),
                    onClick = {
                        scope.launch {
                            withContext(Dispatchers.IO) {
                                try {
                                    val response = client.post("http://$server:$port/off")
                                    statusMessage = response.bodyAsText()
                                    println(statusMessage)
                                } catch(e: Exception) {
                                    statusMessage = "Error: ".plus(e.message.toString())
                                }
                            }
                        }
                    }
                ) {
                    Text("Off")
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