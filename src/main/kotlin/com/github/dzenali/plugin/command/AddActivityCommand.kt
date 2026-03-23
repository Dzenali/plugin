package com.github.dzenali.plugin.command

data class AddActivityCommand(val payload: AddActivityCommandData){
    val action = "addActivity"
}
data class AddActivityCommandData(val id: String, val achievement: String, val gameMode: Int)
