package de.astride.maintenance.listener

import com.velocitypowered.api.event.ResultedEvent
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.LoginEvent
import com.velocitypowered.api.event.proxy.ProxyPingEvent
import com.velocitypowered.api.proxy.server.ServerPing
import de.astride.maintenance.config.ConfigProvider

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 14.02.2019 23:31.
 * Current Version: 1.0 (14.02.2019 - 15.02.2019)
 */
class Listener(private val provider: ConfigProvider) {

    @Subscribe
    fun on(event: ProxyPingEvent) {
        val motd = provider.motd
        val version = ServerPing.Version(event.ping.version.protocol, motd.versionName)

        event.ping = event.ping.asBuilder()
            .version(version)
            .description(motd.description)
            .nullPlayers()
            .build()
    }

    @Subscribe
    fun on(event: LoginEvent) {
        if (event.player.hasPermission(provider.config.byPassPermission)) return
        event.result = ResultedEvent.ComponentResult.denied(provider.config.kickMessage)
    }

}