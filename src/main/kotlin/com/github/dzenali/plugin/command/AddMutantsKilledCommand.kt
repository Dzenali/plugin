package com.github.dzenali.plugin.command

import com.github.dzenali.plugin.util.Mutation

data class AddMutantsKilledCommand(val payload: AddMutantsKilledCommandData){
    val action="updateKilledMutants"
}

data class AddMutantsKilledCommandData(val id: String, val killedMutants: List<Mutation>)
