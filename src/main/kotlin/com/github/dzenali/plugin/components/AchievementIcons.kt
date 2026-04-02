package com.github.dzenali.plugin.components

import com.github.dzenali.plugin.achievements.Achievement
import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

object AchievementIcons {
    private val iconMap: Map<String, Icon> = mapOf(
        "Kill1MutantsAchievement" to IconLoader.getIcon("/icons/Kill1MutantAchievementIcon.png", javaClass),
        "Kill5MutantsAchievement" to IconLoader.getIcon("/icons/Kill5MutantsAchievementIcon.png", javaClass),
        "Kill10MutantsAchievement" to IconLoader.getIcon("/icons/Kill10MutantsAchievementIcon.png", javaClass),
        "Kill25MutantsAchievement" to IconLoader.getIcon("/icons/Kill75MutantsAchievementIcon.png", javaClass),
        "Kill75MutantsAchievement" to IconLoader.getIcon("/icons/Kill75MutantsAchievementIcon.png", javaClass),
        "Kill66MutantsAchievement" to IconLoader.getIcon("/icons/Kill200MutantsAchievementIcon.png", javaClass),
        "Kill200MutantsAchievement" to IconLoader.getIcon("/icons/Kill200MutantsAchievementIcon.png", javaClass),
        "KillAllMutantsAchievement" to IconLoader.getIcon("/icons/KillAllMutantsAchievementIcon.png", javaClass),
        "Cover10LinesAchievement" to IconLoader.getIcon("/icons/Cover10LinesAchievementIcon.png", javaClass),
        "Cover33LinesAchievement" to IconLoader.getIcon("/icons/Cover100LinesAchievementIcon.png", javaClass),
        "Cover100LinesAchievement" to IconLoader.getIcon("/icons/Cover100LinesAchievementIcon.png", javaClass),
        "Cover200LinesAchievement" to IconLoader.getIcon("/icons/Cover600LinesAchievementIcon.png", javaClass),
        "Cover300LinesAchievement" to IconLoader.getIcon("/icons/Cover300LinesAchievementIcon.png", javaClass),
        "Cover600LinesAchievement" to IconLoader.getIcon("/icons/Cover600LinesAchievementIcon.png", javaClass),
        "Add1TestAchievement" to IconLoader.getIcon("/icons/Add1TestAchievementIcon.png", javaClass),
        "Add10TestsAchievement" to IconLoader.getIcon("/icons/Add10TestsAchievementIcon.png", javaClass),
        "Add20TestsAchievement" to IconLoader.getIcon("/icons/Add20TestsAchievementIcon.png", javaClass),
        "Add60TestsAchievement" to IconLoader.getIcon("/icons/Add50TestsAchievementIcon.png", javaClass),
        "Add180TestsAchievement" to IconLoader.getIcon("/icons/Add180TestsAchievementIcon.png", javaClass),
        "CleanDragonAchievement" to IconLoader.getIcon("/icons/CleanDragonAchievementIcon.png", javaClass),

        )

    val lockedIcon: Icon = IconLoader.getIcon("/icons/AchievementLockedIcon.png", javaClass)

    fun getIcon(achievement: Achievement): Icon {
        return iconMap[achievement::class.simpleName] ?: lockedIcon
    }
}