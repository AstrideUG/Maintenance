package de.astride.maintenance.config

import net.darkdevelopers.darkbedrock.darkness.general.configs.ConfigData
import net.darkdevelopers.darkbedrock.darkness.general.configs.gson.GsonService
import net.kyori.text.Component
import net.kyori.text.serializer.ComponentSerializers
import java.io.File

/**
 * @author Lars Artmann | LartyHD
 * Created by Lars Artmann | LartyHD on 15.02.2019 00:10.
 * Current Version: 1.0 (15.02.2019 - 15.02.2019)
 */
class ConfigProvider(var directory: File) {

    /* SubClass */
    val config by lazy { Config() }
    val motd by lazy { Motd() }

    inner class Config internal constructor() {

        /* Main */
        private val configData by lazy { ConfigData(directory, "config.json") }
        private val config by lazy { GsonService.loadAsJsonObject(configData) }
        /* Values */
        val isActive by lazy { config["IsActive"]?.asBoolean ?: false }
        val motdFileName by lazy { config["MotdFileName"]?.asString ?: "motd.json" }
        val byPassPermission by lazy { config["ByPassPermission"]?.asString ?: "maintenance.bypass" }
        val kickMessage: Component by lazy {
            ComponentSerializers.LEGACY.deserialize(config["KickMessage"]?.asString ?: "§cCosmicSky.net | Maintenance")
        }

    }

    inner class Motd internal constructor() {

        /* Main */
        private val configData by lazy { ConfigData(directory, this@ConfigProvider.config.motdFileName) }
        private val config by lazy { GsonService.loadAsJsonObject(configData) }
        /* Values */
        val versionName: String by lazy { config["VerisonName"]?.asString ?: "CosmicProxy" }
        val description: Component by lazy {
            ComponentSerializers.LEGACY.deserialize(
                config["Description"]?.asString?.replace("\\n", "\n") ?: "§cCosmicSky.net | Maintenance"
            )
        }

    }

}