package com.github.dzenali.plugin.command

data class UpdateUsernameCommand(val payload: UpdateUsernameCommandData){
    val action = "updateUsername"
}
data class UpdateUsernameCommandData(val id: String, val username: String)