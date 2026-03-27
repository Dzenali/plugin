package com.github.dzenali.plugin.command

data class TeamAchievementCommand(
    var action: String,
    var payload: TeamAchievementCommandData
)

data class TeamAchievementCommandData(var dragon: Int)