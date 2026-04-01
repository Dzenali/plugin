package com.github.dzenali.plugin.command

data class OnTeamAchievementsUnlocked(val action: String, val payload: OnTeamAchievementsUnlockedData)
data class OnTeamAchievementsUnlockedData(val t1: Boolean, val t2: Boolean)
