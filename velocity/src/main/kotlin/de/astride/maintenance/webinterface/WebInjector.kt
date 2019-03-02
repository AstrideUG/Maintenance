/*
 * © Copyright - Lars Artmann aka. LartyHD 2019.
 */

package de.astride.maintenance.webinterface

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpServer
import de.astride.maintenance.Registry
import java.net.InetSocketAddress


/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 02.03.2019 07:39.
 * Current Version: 1.0 (02.03.2019 - 02.03.2019)
 */
class WebInjector(private val registry: Registry) {

    val server = HttpServer.create(InetSocketAddress(Registry.provider.webInterface.port), 0)

    fun start() {

        server.createContext("/status") { exchange ->
            val input = Registry.isActive.toString()
            exchange.sendResponseHeaders(200, input.length.toLong())
            exchange.send(input.toByteArray())
        }

        server.createContext("/") { exchange ->
            try {
//                val resourceAsStream = ClassLoader::class.java.getResourceAsStream("acp.html")
                val input = """<!--
  ~ © Copyright - Lars Artmann aka. LartyHD 2019.
  -->

<!DOCTYPE html>
<html lang="en">
<head>
    <!--Import Google Icon Font-->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

    <!-- Compiled and minified CSS -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css" rel="stylesheet">

    <!--Let browser know website is optimized for mobile-->
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <meta content="300" http-equiv="Refresh"/>
    <title>Maintenance | ACP</title>
</head>

<body>

<nav>
    <div class="nav-wrapper">
        <a class="brand-logo" href="#">Maintenance - ACP</a>
        <ul class="right hide-on-med-and-down" id="nav-mobile">
            <li><a href="/">Home</a></li>
            <li><a href="/status">Status</a></li>
            <li><a href="/toggle">Toggle</a></li>
        </ul>
    </div>
</nav>

<form style="height: 100px;background: indianred;color: #fff;size: 50px">
    <label>
        <input id="checkbox" type="checkbox"/>
        <span id="maintenance" onmousedown="toggle()">Maintenance</span>
    </label>
</form>


<footer class="page-footer">
    <div class="container">
        <div class="row">
            <div class="col l6 s12">
            </div>
            <div class="col l4 offset-l2 s12">
            </div>
        </div>
    </div>
    <div class="footer-copyright">
        <div class="container">
            © 2019 Copyright Lars Artmann | LartyHD
            <a class="grey-text text-lighten-4 right" href="#!"></a>
        </div>
    </div>
</footer>

<script>

    M.AutoInit();

    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            document.getElementById("checkbox").checked = this.responseText;
        }
    };
    xhttp.open("GET", "/status", true);
    xhttp.send();

    function toggle() {
        let xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200) {
                M.toast({html: "Changed to " + this.responseText});
                document.getElementById("checkbox").checked = this.responseText;
            }
        };
        xhttp.open("GET", "/toggle", true);
        xhttp.send();
    }

    document.getElementById("maintenance").addEventListener("mousedown", function () {

        let xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200) {
                M.toast({html: "Changed to " + this.responseText});
                document.getElementById("checkbox").checked = this.responseText;
            }
        };
        xhttp.open("GET", "/toggle", true);
        xhttp.send();

    });

</script>
<!-- Compiled and minified JavaScript -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
</body>
</html>

""".toByteArray()


                exchange.sendResponseHeaders(200, input.size.toLong())
                exchange.send(input)
            } catch (ex: Throwable) {
                ex.printStackTrace()
            }
        }
        server.createContext("/toggle") { exchange ->
            Registry.isActive = !Registry.isActive
            registry.register(false)

            val input = Registry.isActive.toString()
            exchange.sendResponseHeaders(200, input.length.toLong())
            exchange.send(input.toByteArray())
        }
        server.executor = null // creates a default executor
        server.start()

    }

    private fun HttpExchange.send(bytes: ByteArray) = responseBody.run {
        write(bytes)
        close()
    }

    fun stop() = server.stop(10)

}

