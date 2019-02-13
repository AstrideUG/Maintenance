/*
 * © Copyright - Lars Artmann aka. LartyHD 2019.
 */

package de.astride.maintenance

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.proxy.ProxyServer
import org.slf4j.Logger

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 13.02.2019 09:52.
 * Current Version: 1.0 (13.02.2019 - 13.02.2019)
 */
@Plugin(
    id = "maintenance",
    name = "Maintenance",
    version = "@version@",
    url = "Astride.de",
    authors = ["Lars Artmann | LartyHD"]
)
class Maintenance private constructor(
    private val proxyServer: ProxyServer,
    private val logger: Logger
) {

    @Suppress("UNUSED_PARAMETER")
    @Subscribe
    fun on(event: ProxyInitializeEvent) {
        logger.info("Maintenance loaded :D")
    }

}