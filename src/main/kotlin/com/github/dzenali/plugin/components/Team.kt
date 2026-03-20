package com.github.dzenali.plugin.components

object Team {
    private val users = ArrayList<User>()

    init {
        println("Init team")
    }

    fun addUser(user: User) {
        users.add(user)
    }

    private fun removeUser(user: User){
        users.remove(user)
    }

    fun getUsers(): List<User> {
        return users
    }

    fun updateUser(newUser: User){
        val user = users.find { it.id == newUser.id }
        if(user != null) {
            removeUser(user)
            addUser(newUser)
        }
    }

    fun setUsers(users : List<User>) {
        reset()
        for(user in users){
            this.users.add(user)
        }
    }

    private fun reset(){
        users.clear()
    }
}