package com.github.dzenali.plugin.components

import com.github.dzenali.plugin.achievements.Achievement
import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

object AchievementIcons {
    private val iconMap: Map<String, Icon> = mapOf(
        "Kill1MutantsAchievement" to IconLoader.getIcon("/icons/Kill1MutantsAchievementIcon.png", javaClass),
        "Kill5MutantsAchievement" to IconLoader.getIcon("/icons/Kill5MutantsAchievementIcon.png", javaClass),
        "Kill10MutantsAchievement" to IconLoader.getIcon("/icons/Kill10MutantsAchievementIcon.png", javaClass),
        "Kill75MutantsAchievement" to IconLoader.getIcon("/icons/Kill75MutantsAchievementIcon.png", javaClass),
        "Kill200MutantsAchievement" to IconLoader.getIcon("/icons/Kill200MutantsAchievementIcon.png", javaClass),
        "Cover10LinesAchievement" to IconLoader.getIcon("/icons/Cover10LinesAchievementIcon.png", javaClass),
        "Cover100LinesAchievement" to IconLoader.getIcon("/icons/Cover100LinesAchievementIcon.png", javaClass),
        "Cover300LinesAchievement" to IconLoader.getIcon("/icons/Cover300LinesAchievementIcon.png", javaClass),
        "KillAllMutantsAchievement" to IconLoader.getIcon("/icons/KillAllMutantsAchievementIcon.png", javaClass),
        "Add1TestAchievement" to IconLoader.getIcon("/icons/Add1TestAchievementIcon.png", javaClass),
        "Add10TestsAchievement" to IconLoader.getIcon("/icons/Add10TestsAchievementIcon.png", javaClass),
        "Add50TestsAchievement" to IconLoader.getIcon("/icons/Add50TestsAchievementIcon.png", javaClass),
        "CleaDragonAchievement" to IconLoader.getIcon("/icons/CleanDragonAchievementIcon.png", javaClass),

        )

    val lockedIcon: Icon = IconLoader.getIcon("/icons/AchievementLockedIcon.png", javaClass)

    fun getIcon(achievement: Achievement): Icon {
        return iconMap[achievement::class.simpleName] ?: lockedIcon
    }
}