package com.undefinedcreations.nova.lib.links

import com.google.gson.JsonParser
import java.io.File
import java.io.FileOutputStream
import java.net.URI
import java.util.Scanner
import java.util.UUID


/**
 * This class is used to download the server jar selected.
 *
 * @since 1.0.0
 */

object DownloadLib {

    /**
     * This method is used to download PaperMC.
     *
     * @param folder The folder to download the jar to
     * @param minecraftVersion The minecraft version target
     */
    fun paper(folder: File, minecraftVersion: String) =
        downloadFromPaperMCRepo(folder, minecraftVersion, "paper")

    /**
     * This method is used to download Waterfall proxy.
     *
     * @param folder The folder to download the jar to
     * @param minecraftVersion The minecraft version target
     */
    fun waterfall(folder: File, minecraftVersion: String) =
        downloadFromPaperMCRepo(folder, minecraftVersion, "waterfall")

    /**
     * This method is used to download Velocity proxy.
     *
     * @param folder The folder to download the jar to
     * @param minecraftVersion The minecraft version target
     */
    fun velocity(folder: File, minecraftVersion: String) =
        downloadFromPaperMCRepo(folder, minecraftVersion, "velocity")

    /**
     * This method is used to download Folia.
     *
     * @param folder The folder to download the jar to
     * @param minecraftVersion The minecraft version target
     */
    fun folia(folder: File, minecraftVersion: String) =
        downloadFromPaperMCRepo(folder, minecraftVersion, "folia")


    /**
     * This method is used to download Spigot.
     *
     * @param folder The folder to download the jar to
     * @param minecraftVersion The minecraft version target
     */
    fun spigot(folder: File, minecraftVersion: String) =
        downloadFromSpigotUndefinedCreations(folder, minecraftVersion)

    /**
     * This method is used to download hytale jar.
     *
     * @param folder The folder to download the jar to
     */
    fun hytaleJar(folder: File) =
        downloadFromHytaleJarUndefinedCreations(folder)
    /**
     * This method is used to download hytale assets.
     *
     * @param folder The folder to download the jar to
     */
    fun hytaleAssets(folder: File) =
        downloadFromHytaleAssetsUndefinedCreations(folder)

    /**
     * This method is used to download Bungeecord proxy.
     *
     * @param folder The folder to download the jar to
     * @param minecraftVersion The minecraft version target
     */
    fun bungeecord(folder: File): DownloadResult =
        downloadFile(folder, Repositories.BUNGEECORD_REPO, "Bungeecord.jar")

    /**
     * This method is used to download Purpur.
     *
     * @param folder The folder to download the jar to
     * @param minecraftVersion The minecraft version target
     */
    fun purpur(folder: File, minecraftVersion: String): DownloadResult =
        downloadFile(folder, "${Repositories.PURPUR_REPO}/$minecraftVersion/latest/download", "Purpur.jar")

    /**
     * This method is used to download Leaf.
     *
     * @param folder The folder to download the jar to
     * @param minecraftVersion The minecraft version target
     */
    fun leaf(folder: File, minecraftVersion: String): DownloadResult  {
        val information = URI.create("${Repositories.LEAF_REPO}/versions/$minecraftVersion")
        val input = information.toURL().readText()
        val json = JsonParser.parseString(input)
        val lastestBuild = json.asJsonObject.get("builds").asJsonArray.maxOf { it.asInt }

        val fileName = "leaf-$minecraftVersion-$lastestBuild.jar"

        return downloadFile(folder, "${Repositories.LEAF_REPO}/versions/$minecraftVersion/builds/$lastestBuild/downloads/$fileName", fileName)
    }

    /**
     * This method is used to download AdvancedSlimePaper.
     *
     * @param folder The folder to download the jar to
     * @param minecraftVersion The minecraft version target
     */
    fun asp(folder: File, minecraftVersion: String): DownloadResult {
        val informationURL = URI.create("${Repositories.ASP_REPO}/mcversion/$minecraftVersion/latest")
        val input = informationURL.toURL().readText()
        val scanner = Scanner(input).useDelimiter("\\A")
        val result = if (scanner.hasNext()) scanner.next() else ""

        val json = JsonParser.parseString(result).asJsonObject
        val id = UUID.fromString(json["id"].asString)
        val files = json["files"].asJsonArray
        val fileId = UUID.fromString(files.first { "server" in it.asJsonObject["fileName"].asString }.asJsonObject["id"].asString)

        return downloadFile(folder, "${Repositories.ASP_REPO}/$id/download/$fileId", "AdvancedSlimePaper.jar")
    }

    /**
     * This method is used to download Pufferfish.
     *
     * @param folder The folder to download the jar to
     * @param minecraftVersion The minecraft version target
     */
    fun pufferfish(folder: File, minecraftVersion: String): DownloadResult {
        val mainUrl = URI("${Repositories.PUFFERFISH_REPO}/Pufferfish-$minecraftVersion/lastSuccessfulBuild")
        val buildUrl = URI("$mainUrl/api/json")

        val path = JsonParser.parseString(buildUrl.toURL().readText())
            .asJsonObject.getAsJsonArray("artifacts")
            .get(0).asJsonObject.get("relativePath").asString

        return downloadFile(folder, "$mainUrl/artifact/$path", "PufferFish.jar")
    }


    /**
     * This method is used to download file from the PaperMC repository.
     *
     * @param folder The folder to download the jar to
     * @param minecraftVersion The jar version to download
     * @param projectName The project name to download from the repository
     */
    private fun downloadFromPaperMCRepo(folder: File, minecraftVersion: String, projectName: String): DownloadResult {
        val url = URI("${Repositories.PAPERMC_REPO}/$projectName/versions/$minecraftVersion")
        val builds = JsonParser.parseString(url.toURL().readText())
            .asJsonObject.getAsJsonArray("builds")
        val latestBuild = builds.last().asInt

        return downloadFile(folder, "$url/builds/$latestBuild/downloads/$projectName-$minecraftVersion-$latestBuild.jar", "$projectName.jar")
    }

    /**
     * This method is used to download file from the UndefinedCreations repository.
     *
     * @param folder The folder to download the jar to
     * @param minecraftVersion The jar version to download
     */
    private fun downloadFromSpigotUndefinedCreations(folder: File, minecraftVersion: String): DownloadResult =
        downloadFile(folder, "${Repositories.UNDEFINEDCREATIONS_REPO}/spigotmc/spigot-$minecraftVersion.jar", "spigot.jar")

    /**
     * This method is used to download file from the UndefinedCreations repository.
     *
     * @param folder The folder to download the jar to
     */
    private fun downloadFromHytaleJarUndefinedCreations(folder: File): DownloadResult =
        downloadFile(folder, "${Repositories.UNDEFINEDCREATIONS_REPO}/hytale/HytaleServer.jar", "hytale.jar")

    /**
     * This method is used to download file from the UndefinedCreations repository.
     *
     * @param outputFolder The folder to download the jar to
     */
    private fun downloadFromHytaleAssetsUndefinedCreations(outputFolder: File): DownloadResult {
        val baseUrl = "${Repositories.UNDEFINEDCREATIONS_REPO}/hytale/Assets"
        val outputFileName = "Assets.zip"
        val partCount = 5

        val outputFile = File(outputFolder, outputFileName)

        val tempFolder = File(outputFolder, "temp_parts_${System.currentTimeMillis()}")
        tempFolder.mkdirs()

        if (outputFile.exists()) return DownloadResult(DownloadResultType.SUCCESS, null, outputFile)

        println("Downloading assets... (This can take a while)")

        try {
            val partFiles = (1..partCount).map { partNumber ->
                Thread {
                    val partUrl = "$baseUrl.part$partNumber"
                    val partFile = File(tempFolder, "Assets.part$partNumber")
                    
                    URI(partUrl).toURL().openStream().use { input ->
                        FileOutputStream(partFile).use { output ->
                            input.copyTo(output)
                        }
                    }
                }
            }.also { threads ->
                threads.forEach { it.start() }
                threads.forEach { it.join() }
            }.mapIndexed { index, _ -> 
                File(tempFolder, "Assets.part${index + 1}")
            }


            FileOutputStream(outputFile).use { output ->
                partFiles.forEach { partFile ->
                    partFile.inputStream().use { input ->
                        input.copyTo(output)
                    }
                }
            }

            partFiles.forEach { it.delete() }
            tempFolder.delete()
            
            return DownloadResult(DownloadResultType.SUCCESS, null, outputFile)
        } catch (exception: Exception) {
            tempFolder.listFiles()?.forEach { it.delete() }
            tempFolder.delete()
            
            return DownloadResult(DownloadResultType.FAILED, exception.message, null)
        }
    }


    /**
     * This method is used to download a file from an url.
     *
     * @param folder The folder to download the jar to
     * @param downloadURL The url to download from
     * @param name The name to give the downloaded file
     */
    fun downloadFile(folder: File, downloadURL: String, name: String): DownloadResult =
        downloadFile(folder, URI(downloadURL), name)

    /**
     * This method is used to download a file from an url.
     *
     * @param folder The folder to download the jar to
     * @param downloadURL The url to download from
     * @param name The name to give the downloaded file
     *
     * @return This method will return the download result if it fails or not
     */
    private fun downloadFile(folder: File, downloadURL: URI, name: String): DownloadResult {
        val file = File(folder, name)

        return if (file.exists()) {
            DownloadResult(DownloadResultType.SUCCESS, null, file)
        } else {
            try {
                downloadURL.toURL().openStream().use { input ->
                    FileOutputStream(file).use { output ->
                        input.copyTo(output)
                    }
                }
                DownloadResult(DownloadResultType.SUCCESS, null, file)
            } catch (exception: Exception) {
                DownloadResult(DownloadResultType.FAILED, exception.message, null)
            }
        }
    }
}


