package com.github.dzenali.plugin.command

import com.github.dzenali.plugin.components.User

data class OnTeamUpdatedCommand(
    val teamName: String,
    val payload: List<User>  // or whatever your User looks like
)

data class OnTeamUpdatedCommandData(
    val action: String,
    val payload: OnTeamUpdatedCommand  // object not List<User>
)