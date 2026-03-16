package com.github.dzenali.plugin.command

import com.github.dzenali.plugin.components.User

data class OnUsernameUpdatedCommand(val action: String, val payload: User)