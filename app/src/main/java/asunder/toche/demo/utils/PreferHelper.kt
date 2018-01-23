package asunder.toche.demo.utils

import android.content.SharedPreferences

/**
 *Created by ToCHe on 23/1/2018 AD.
 */
object PreferHelper{

    val KEYCUSID = "cus_id"
    val KEYPROID = "pro_id"
    val KEYORDERID = "order_id"

    fun getLastID(preferences: SharedPreferences,key :String) : String {
        var lastId = preferences.getInt(key, 0)
        if (lastId.toString().length <= 4) {
            when (lastId.toString().length) {
                1 -> {
                    return "000" + lastId
                }
                2 -> {
                    return "00" + lastId
                }
                3 -> {
                    return "0" + lastId
                }
                4 -> {
                    return "$lastId"
                }
            }
        } else {
            return "0000"
        }
        return "0000"
    }

    fun increseID(preferences: SharedPreferences,key: String) {
        var lastId = preferences.getInt(key, 0)
        val editor = preferences.edit()
        editor.putInt(key,lastId+1)
        editor.apply()
    }




}