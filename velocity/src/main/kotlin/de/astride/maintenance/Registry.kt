/*
 * © Copyright - Lars Artmann aka. LartyHD 2019.
 */

package de.astride.maintenance

import com.velocitypowered.api.event.EventManager
import de.astride.maintenance.config.ConfigProvider
import de.astride.maintenance.listener.Listener
import de.astride.maintenance.webinterface.WebInjector
import org.slf4j.Logger
import java.io.File

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 02.03.2019 09:02.
 * Current Version: 1.0 (02.03.2019 - 02.03.2019)
 */
@Suppress("UNUSED_PARAMETER")
class Registry(
    private val plugin: Any,
    private val eventManager: EventManager,
    private val file: File,
    private val logger: Logger
) {

    companion object {
        var isActive: Boolean = false
        lateinit var provider: ConfigProvider
    }

    private lateinit var listener: Listener
    private lateinit var webInjector: WebInjector

    fun register(boolean: Boolean = true) {
        registerConfigProvider(boolean)
        if (provider.webInterface.isActive && boolean) registerWebInjector()
        updateListener()
    }

    private fun registerConfigProvider(boolean: Boolean = true) {
        provider = ConfigProvider(file)
        if (boolean) isActive = provider.config.isActive
    }

    private fun updateListener() {
        if (::listener.isInitialized) eventManager.unregisterListener(plugin, listener)
        if (isActive) registerListener()
    }

    private fun registerListener() {
        listener = Listener(provider)
        eventManager.register(plugin, listener)
    }

    private fun registerWebInjector() {
        logger.info("register WebInjector")
        if (::webInjector.isInitialized) return//webInjector.stop()
        webInjector = WebInjector(this)
        webInjector.start()
        logger.info(webInjector.server.toString())
        logger.info(webInjector.server.address.toString())
        logger.info("registered WebInjector")
    }


}