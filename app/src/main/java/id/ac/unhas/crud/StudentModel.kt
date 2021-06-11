package id.ac.unhas.crud

import java.util.*

data class StudentModel(
    val id: Int = getAutoId(),
    var nama: String = "",
    var email: String = ""

)   {
    companion object{
        fun getAutoId():Int {
            val random = Random()
            return random.nextInt(100)

        }
    }
}