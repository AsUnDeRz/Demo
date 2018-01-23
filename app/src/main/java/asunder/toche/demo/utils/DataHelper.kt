package asunder.toche.demo.utils

import android.content.SharedPreferences
import android.util.Log
import android.widget.Filter
import asunder.toche.demo.model.MasCustomer
import asunder.toche.demo.model.MasProducts
import java.text.SimpleDateFormat
import java.util.*


/**
 *Created by ToCHe on 23/1/2018 AD.
 */
object DataHelper {

    fun getDateString(date: Date):String{
        val fmtOut = SimpleDateFormat("dd-MM-yyyy",Locale("th"))
        return fmtOut.format(date)
    }


    fun genCustomerID(preferences: SharedPreferences): String{
        val code = "C"
        val c = Calendar.getInstance()
        c.time = Date()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)+1
        val id = PreferHelper.getLastID(preferences,PreferHelper.KEYCUSID)
        var monthString = ""
        monthString = if(month.toString().length ==2){
            month.toString()
        }else{
            "0$month"
        }
        Log.d("GENID","$code $year $monthString $id")

        return "$code$year$monthString$id"
    }

    fun genProductID(preferences: SharedPreferences): String{
        val code = "P"
        val c = Calendar.getInstance()
        c.time = Date()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)+1
        val id = PreferHelper.getLastID(preferences,PreferHelper.KEYPROID)
        var monthString = ""
        monthString = if(month.toString().length ==2){
            month.toString()
        }else{
            "0$month"
        }
        Log.d("GENID","$code $year $monthString $id")

        return "$code$year$monthString$id"
    }

    fun genOrderID(preferences: SharedPreferences): String{
        val code = "O-"
        val c = Calendar.getInstance()
        c.time = Date()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)+1
        val id = PreferHelper.getLastID(preferences,PreferHelper.KEYORDERID)
        var monthString = ""
        monthString = if(month.toString().length ==2){
            month.toString()
        }else{
            "0$month"
        }
        Log.d("GENID","$code $year $monthString $id")

        return "$code$year$monthString$id"
    }


    interface OnFindCustomerListener {
        fun onResults(results: ArrayList<MasCustomer>)
    }
    interface OnFindProductListener {
        fun onResults(results: ArrayList<MasProducts>)
    }

    /*
    interface OnFindSuggestionsListener {
        fun onResults(results: List<ColorSuggestion>)
    }



    fun findSuggestions(context: Context, query: String, limit: Int, simulatedDelay: Long,
                        listener: OnFindSuggestionsListener?) {
        object : Filter() {

             override fun performFiltering(constraint: CharSequence?): FilterResults {

                try {
                    Thread.sleep(simulatedDelay)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                DataHelper.resetSuggestionsHistory()
                val suggestionList = ArrayList()
                if (!(constraint == null || constraint.length == 0)) {

                    for (suggestion in sColorSuggestions) {
                        if (suggestion.getBody().toUpperCase()
                                        .startsWith(constraint.toString().toUpperCase())) {

                            suggestionList.add(suggestion)
                            if (limit != -1 && suggestionList.size == limit) {
                                break
                            }
                        }
                    }
                }

                val results = FilterResults()
                Collections.sort(suggestionList, object : Comparator<ColorSuggestion>() {
                    fun compare(lhs: ColorSuggestion, rhs: ColorSuggestion): Int {
                        return if (lhs.getIsHistory()) -1 else 0
                    }
                })
                results.values = suggestionList
                results.count = suggestionList.size

                return results
            }

            protected override fun publishResults(constraint: CharSequence, results: FilterResults) {

                listener?.onResults(results.values as List<ColorSuggestion>)
            }
        }.filter(query)

    }

*/

    fun findCustomer(query: String, listener: OnFindCustomerListener, masterData:ArrayList<MasCustomer>) {

        object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {


                val suggestionList = arrayListOf<MasCustomer>()

                if (!(constraint == null || constraint.isEmpty())) {

                    for (customer in masterData) {
                        if (customer.FistName.startsWith(constraint.toString())) {
                            suggestionList.add(customer)
                        }
                    }

                }

                val results = FilterResults()
                results.values = suggestionList
                results.count = suggestionList.size
                return results
            }

             override fun publishResults(constraint: CharSequence, results: FilterResults) {
                if (results.count != 0) {
                    listener?.onResults(results.values as ArrayList<MasCustomer>)
                }else{
                    listener?.onResults(masterData)
                }
            }
        }.filter(query)

    }

    fun findProduct(query: String, listener: OnFindProductListener?,masterData:ArrayList<MasProducts>) {
        object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {


                val suggestionList = arrayListOf<MasProducts>()

                if (!(constraint == null || constraint.isEmpty())) {
                    for (customer in masterData) {
                        if (customer.ProductName.startsWith(constraint.toString())) {
                            suggestionList.add(customer)
                        }
                    }
                }

                val results = FilterResults()
                results.values = suggestionList
                results.count = suggestionList.size
                return results
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                if (results.count != 0) {
                    listener?.onResults(results.values as ArrayList<MasProducts>)
                }else{
                    listener?.onResults(masterData)
                }
            }
        }.filter(query)

    }




}