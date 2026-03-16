package com.github.dzenali.plugin.command

data class UserConnectedCommand(val payload: UserConnectedCommandData){
    val action = "userConnected"
}
data class UserConnectedCommandData(val id: String, val username: String)