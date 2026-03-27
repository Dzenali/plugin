package com.github.dzenali.plugin.util

import com.github.dzenali.plugin.MyBundle
import com.github.dzenali.plugin.achievements.Achievement
import com.github.dzenali.plugin.achievements.Add10TestsAchievement
import com.github.dzenali.plugin.achievements.Add1TestAchievement
import com.github.dzenali.plugin.achievements.Add50TestsAchievement
import com.github.dzenali.plugin.achievements.CleanDragonAchievement
import com.github.dzenali.plugin.achievements.Cover100LinesAchievement
import com.github.dzenali.plugin.achievements.Cover10LinesAchievement
import com.github.dzenali.plugin.achievements.Cover300LinesAchievement
import com.github.dzenali.plugin.achievements.Kill10MutantsAchievement
import com.github.dzenali.plugin.achievements.Kill1MutantAchievement
import com.github.dzenali.plugin.achievements.Kill200MutantsAchievement
import com.github.dzenali.plugin.achievements.Kill75MutantsAchievement
import com.github.dzenali.plugin.achievements.KillAllMutantsAchievement
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import java.io.File
import kotlin.random.Random
import java.io.BufferedOutputStream
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream


val adjectives = listOf("Rapide", "Mystique", "Fou", "Brillant", "Sombre", "Éclair")
val nouns = listOf("Tigre", "Phoenix", "Dragon", "Loup", "Hérisson", "Hibou", "Chat", "Renard")

object Util{
    fun getProject(locationUrl: String?): Project? {
        val projects = ProjectManager.getInstance().openProjects
        var project: Project? = null

        if (projects.size == 1) {
            project = projects[0]
            return project
        }

        for (p in projects) {
            if (p.basePath?.let { locationUrl?.contains(it) } == true) {
                project = p
            }
        }

        return project
    }

    fun getAchievements(): List<Achievement> {
        return listOf(
            Add1TestAchievement,
            Add10TestsAchievement,
            Add50TestsAchievement,
            Kill1MutantAchievement,
            Kill10MutantsAchievement,
            Kill75MutantsAchievement,
            Kill200MutantsAchievement,
            Cover10LinesAchievement,
            Cover100LinesAchievement,
            Cover300LinesAchievement,
            KillAllMutantsAchievement,
            CleanDragonAchievement,
        )
    }

    fun getPersonalAchievements(): List<Achievement> {
        return listOf(
            Add1TestAchievement,
            Add10TestsAchievement,
            Kill1MutantAchievement,
            Kill10MutantsAchievement,
            Cover10LinesAchievement,
            )
    }

    fun getTeamAchievements(): List<Achievement> {
        return listOf(
            Add50TestsAchievement,
            Kill75MutantsAchievement,
            Kill200MutantsAchievement,
            Cover100LinesAchievement,
            Cover300LinesAchievement,
            KillAllMutantsAchievement,
            CleanDragonAchievement,
            )
    }

    fun getCoverageAchievements(): List<Achievement> {
        return listOf(
            Cover10LinesAchievement,
            Cover100LinesAchievement,
            Cover300LinesAchievement,
        )
    }

    fun getTestAchievements(): List<Achievement>{
        return listOf(
            Add1TestAchievement,
            Add10TestsAchievement,
            Add50TestsAchievement,
        )
    }

    fun getMutantAchievements(): List<Achievement>{
        return listOf(
            Kill1MutantAchievement,
            Kill10MutantsAchievement,
            Kill75MutantsAchievement,
            Kill200MutantsAchievement,
            KillAllMutantsAchievement,
            CleanDragonAchievement,
        )
    }

    fun generatePseudo(): String {
        val adj = adjectives.random()
        val noun = nouns.random()
        val number = Random.nextInt(1000)

        return "$adj$noun$number"
    }

    fun getEvaluationDirectoryPath(project: Project): String{
        return project.basePath + File.separator + ".evaluation"
    }

    fun getEvaluationFilePath(project: Project, filename: String): String{
        return getEvaluationDirectoryPath(project) + File.separator + filename
    }

    fun isTestExcluded(testName: String?): Boolean{
        if(testName == null){
            return false
        }

        return MyBundle.getMessage("excludedTestClasses")
            .split(",")
            .map{ it.trim().replace("/", ".").replace("\\", ".") }
            .any { testName.contains(it, true) }
    }

    fun zipFolder(sourceDirPath: String, zipFilePath: String) {
        val sourceDir = File(sourceDirPath)

        ZipOutputStream(BufferedOutputStream(FileOutputStream(zipFilePath))).use { zipOut ->
            zipDirectoryRecursive(sourceDir, sourceDir.name, zipOut)
        }
    }

    private fun zipDirectoryRecursive(fileToZip: File, fileName: String, zipOut: ZipOutputStream) {
        if (fileToZip.isHidden) {
            return
        }

        if (fileToZip.isDirectory) {
            if (fileName.endsWith("/").not()) {
                zipOut.putNextEntry(ZipEntry("$fileName/"))
                zipOut.closeEntry()
            }
            fileToZip.listFiles()?.forEach { childFile ->
                zipDirectoryRecursive(childFile, "$fileName/${childFile.name}", zipOut)
            }

            return
        }

        FileInputStream(fileToZip).use { fis ->
            val zipEntry = ZipEntry(fileName)
            zipOut.putNextEntry(zipEntry)
            fis.copyTo(zipOut)
        }
    }
}