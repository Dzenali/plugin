package com.github.dzenali.plugin.command

import com.github.dzenali.plugin.components.User


data class OnUserAddedCommand(val action: String, val payload: User)