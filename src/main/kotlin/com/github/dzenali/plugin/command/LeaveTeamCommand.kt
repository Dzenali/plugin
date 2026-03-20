package com.github.dzenali.plugin.command

data class LeaveTeamCommand(val payload: LeaveTeamCommandData){
    val action = "leaveTeam"
}
data class LeaveTeamCommandData(val userId: String, val userName: String, val teamName: String)