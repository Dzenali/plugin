package com.github.dzenali.plugin.components

import com.github.dzenali.plugin.achievements.Achievement
import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

object AchievementIcons {
    private val iconMap: Map<String, Icon> = mapOf(
        "Kill10MutantsAchievement" to IconLoader.getIcon("/icons/Kill10MutantsAchievementIcon.png", javaClass),
        "Cover100LinesAchievement" to IconLoader.getIcon("/icons/Cover100LinesAchievementIcon.png", javaClass),
        "KillAllMutantsAchievement" to IconLoader.getIcon("/icons/KillAllMutantsAchievementIcon.png", javaClass),
        "Add10TestsAchievement" to IconLoader.getIcon("/icons/Add10TestsAchievementIcon.png", javaClass),
    )

    val lockedIcon: Icon = IconLoader.getIcon("/icons/AchievementLockedIcon.png", javaClass)

    fun getIcon(achievement: Achievement): Icon {
        return iconMap[achievement::class.simpleName] ?: lockedIcon
    }
}