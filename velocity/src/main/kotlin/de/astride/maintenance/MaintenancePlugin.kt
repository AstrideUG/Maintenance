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
import org.slf4j.Logger
import java.nio.file.Path

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 13.02.2019 09:52.
 * Current Version: 1.0 (13.02.2019 - 02.03.2019)
 */
@Suppress("UNUSED_PARAMETER")
@Plugin(
    id = "registry",
    name = "Registry",
    version = "@version@",
    url = "Astride.de",
    authors = ["Lars Artmann | LartyHD"]
)
class MaintenancePlugin @Inject constructor(
    private val eventManager: EventManager,
    @DataDirectory val path: Path,
    private val logger: Logger
) {

    private lateinit var registry: Registry

    @Subscribe
    fun on(event: ProxyInitializeEvent) {
        registry = Registry(this, eventManager, path.toFile(), logger)
        registry.register()
        logger.info("Maintenance loaded")
        //proxyServer.allServers.first().serverInfo.address.address.isReachable(10)
    }

    @Subscribe
    fun on(event: ProxyReloadEvent) {
        registry.register()
        logger.info("Maintenance reloaded")
    }

}