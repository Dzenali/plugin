package com.github.dzenali.plugin.util

import com.github.dzenali.plugin.achievements.Achievement
import com.github.dzenali.plugin.achievements.Kill10MutantsAchievement
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager

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
        return listOf(Kill10MutantsAchievement)
    }
}