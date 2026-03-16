package com.github.dzenali.plugin.command

import com.github.dzenali.plugin.components.User

data class OnUserActivityUpdatedCommand(val action: String, val payload: OnUserActivityUpdatedCommandData)
data class OnUserActivityUpdatedCommandData(val user: User, val earnedPoints: Int)