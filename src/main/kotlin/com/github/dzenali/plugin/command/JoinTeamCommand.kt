package com.github.dzenali.plugin.command

data class JoinTeamCommand(val payload: JoinTeamCommandData){
    val action = "joinTeam"
}
data class JoinTeamCommandData(val userId: String, val userName: String, val teamName: String)