/*
 * © Copyright - Lars Artmann aka. LartyHD 2019.
 */

package de.astride.maintenance

import com.google.inject.Inject
import com.velocitypowered.api.event.EventManager
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.event.proxy.ProxyReloadEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import de.astride.maintenance.config.ConfigProvider
import de.astride.maintenance.listener.Listener
import org.slf4j.Logger
import java.nio.file.Path

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 13.02.2019 09:52.
 * Current Version: 1.0 (13.02.2019 - 15.02.2019)
 */
@Suppress("UNUSED_PARAMETER")
@Plugin(
    id = "maintenance",
    name = "Maintenance",
    version = "@version@",
    url = "Astride.de",
    authors = ["Lars Artmann | LartyHD"]
)
class Maintenance @Inject private constructor(
    private val eventManager: EventManager,
    @DataDirectory private val path: Path,
    private val logger: Logger
) {

    companion object {
        private lateinit var provider: ConfigProvider
    }

    private lateinit var listener: Listener

    @Subscribe
    fun on(event: ProxyInitializeEvent) {
        register()
        logger.info("Maintenance loaded")
        //proxyServer.allServers.first().serverInfo.address.address.isReachable(10)
    }

    @Subscribe
    fun on(event: ProxyReloadEvent) {
        register()
        logger.info("Maintenance reloaded")
    }

    private fun register() {
        registerConfig()
        if (provider.config.isActive) registerListener() //registerConfig() must be called before
    }

    private fun registerConfig() {
        provider = ConfigProvider(path.toFile())
    }

    private fun registerListener() {
        if (::listener.isInitialized) eventManager.unregisterListener(this, listener)
        listener = Listener(provider)
        eventManager.register(this, listener)
    }


}