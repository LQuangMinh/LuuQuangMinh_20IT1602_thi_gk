package com.example.thi_gk


import kotlin.random.Random

data class CustomerModal(
        var id:Int = getAutoId(),
        var name:String ="",
        var age:Int= 1,
        var phone:Int= 1,
        var email:String =""
) {
    companion object{
        fun getAutoId():Int{
            val random = Random
            return random.nextInt(100)
        }
    }
}