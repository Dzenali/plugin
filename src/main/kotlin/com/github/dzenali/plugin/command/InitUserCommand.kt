package com.github.dzenali.plugin.command

import com.github.dzenali.plugin.components.User

data class InitUsersCommand(
    val action: String,
    val payload: List<User>
)