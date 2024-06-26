package com.undefinedcreation.runServer

import com.undefinedcreation.runServer.lib.DownloadLib
import com.undefinedcreation.runServer.lib.DownloadResult
import java.io.File
import java.util.concurrent.CompletableFuture

enum class ServerType {
    SPIGOT,
    CRAFTBUKKIT,
    PAPERMC,
    PUFFERFISH,
    PURPUR,
    BUNGEECORD,
    WATERFALL,
    VELOCITY,
    FOLIA
}

fun ServerType.downloadJar(mcVersion: String, folder: File): DownloadResult =
    when(this) {
        ServerType.SPIGOT -> DownloadLib.downloadSpigot(folder, mcVersion)
        ServerType.CRAFTBUKKIT -> DownloadLib.downloadBukkit(folder, mcVersion)
        ServerType.PAPERMC -> DownloadLib.downloadPaper(folder, mcVersion)
        ServerType.PUFFERFISH -> DownloadLib.downloadPufferFish(folder, mcVersion)
        ServerType.PURPUR -> DownloadLib.downloadPurper(folder, mcVersion)
        ServerType.BUNGEECORD -> DownloadLib.downloadBungeecord(folder, mcVersion)
        ServerType.WATERFALL -> DownloadLib.downloadWaterfall(folder, mcVersion)
        ServerType.VELOCITY -> DownloadLib.downloadVelocity(folder, mcVersion)
        ServerType.FOLIA -> DownloadLib.downloadFolia(folder, mcVersion)
    }
