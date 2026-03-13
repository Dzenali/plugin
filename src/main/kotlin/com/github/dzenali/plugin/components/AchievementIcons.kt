package com.github.dzenali.plugin.components

import com.github.dzenali.plugin.achievements.Achievement
import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

object AchievementIcons {
    private val iconMap: Map<String, Icon> = mapOf(
        "Kill10MutantsAchievement" to IconLoader.getIcon("/icons/FirstCommitAchievementIcon.png", javaClass),
        "HundredCommitsAchievement" to IconLoader.getIcon("/icons/HundredCommitsAchievementIcon.png", javaClass),
        "FirstTestAchievement" to IconLoader.getIcon("/icons/FirstTestAchievementIcon.png", javaClass),
        // add all your achievements here
    )

    val lockedIcon: Icon = IconLoader.getIcon("/icons/AchievementLockedIcon.png", javaClass)

    fun getIcon(achievement: Achievement): Icon {
        return iconMap[achievement::class.simpleName] ?: lockedIcon
    }
}