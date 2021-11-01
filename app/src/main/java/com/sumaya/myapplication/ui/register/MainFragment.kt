package com.sumaya.myapplication.ui.register

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.hbb20.CountryCodePicker
import com.sumaya.myapplication.R
import com.sumaya.myapplication.data.PersonInfo
import com.sumaya.myapplication.ui.displayInfo.InfoFragment
import java.util.*

const val KEY = "F019D26C"

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    //1. Date
    private lateinit var pickDate: TextView

    //2. Country Code
    private lateinit var ccp: CountryCodePicker
    private var countryCode: String? = null
    private var countryName: String? = null
    private lateinit var phone: EditText
    private lateinit var name: EditText

    private lateinit var gender: TextView

    private lateinit var send: Button
    private lateinit var clear: Button
    private lateinit var date: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        name = view.findViewById(R.id.name_main)
        // 1. Date Dialog
        pickDate = view.findViewById(R.id.pickDate)
        pickDate.setOnClickListener {
            getDatePickerDialog()
        }

        //2. Country Code
        phone = view.findViewById(R.id.phone)
        ccp = view.findViewById(R.id.pickCode)
        ccp.setOnCountryChangeListener {
            countryCode = ccp.selectedCountryCode
            countryName = ccp.selectedCountryName
        }

        //3. Alert dialog
        clear = view.findViewById(R.id.clear)
        clear.setOnClickListener {
            getAlertDialog()
        }

        //4. Gender
        gender = view.findViewById(R.id.gender)
        gender.setOnClickListener {
            getSelectionDialog()
        }

        //5. show Info
        send = view.findViewById(R.id.send)
        send.setOnClickListener {
            displayPersonalInfo(view)
        }

    }

    private fun getDatePickerDialog() {
        //create object of Calendar
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        DatePickerDialog(this.requireContext(), DatePickerDialog.OnDateSetListener { _, y, m, d ->
            date = "$d/$m/$y"
            pickDate.setText(date)
        }, year, month, day)
            .show()
    }

    private fun getAlertDialog() {
        val alert = AlertDialog.Builder(this.context)
        alert.setTitle(getString(R.string.reset))
        alert.setIcon(R.drawable.alert)
        alert.setMessage(getString(R.string.clear_q))
        alert.setPositiveButton(R.string.yes) { dialog, which ->
            pickDate.setText(null)
            phone.setText(null)
            ccp.resetToDefaultCountry()
            name.setText(null)
        }
        alert.setNegativeButton(R.string.no) { dialog, which ->
            dialog.cancel()
        }
        alert.setNeutralButton(R.string.cancel) { dialog, which ->
            dialog.cancel()
        }
        alert.show()
    }

    private fun getSelectionDialog() {
        val listItems = arrayOf(getString(R.string.male), getString(R.string.female))
        val select = AlertDialog.Builder(this.context)
        select.setTitle(getString(R.string.choose_gender))
        select.setSingleChoiceItems(listItems, -1) { dialogInterface, i ->
            gender.text = listItems[i]
            dialogInterface.dismiss()
        }
        select.setNeutralButton(getString(R.string.cancel)) { dialog, which ->
            dialog.cancel()
        }
        select.show()
    }

    private fun displayPersonalInfo(view: View) {
        val info = PersonInfo(
            name.text.toString(),
            date,
            "+" + countryCode + phone.text.toString(),
            gender.text.toString()
        )
        val activity = view.context as AppCompatActivity
        val bundle = Bundle()
        bundle.putParcelable(KEY, info)
        val nextFragment = InfoFragment.newInstance()
        nextFragment.arguments = bundle
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.container, nextFragment)
            .addToBackStack("show personal info")
            .commit()
    }

}