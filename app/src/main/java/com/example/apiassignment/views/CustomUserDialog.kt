package com.example.apiassignment.views

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.apiassignment.R
import kotlinx.android.synthetic.main.create_user_dialog.view.*
import java.lang.ClassCastException

class CustomUserDialog(val title: String, val action: String): AppCompatDialogFragment() {

    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var emailtAddressEditText: EditText
    private lateinit var listener: ExampleDialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater: LayoutInflater = activity!!.layoutInflater
        val view: View = inflater.inflate(R.layout.create_user_dialog,null)
        builder.setView(view)
            .setTitle(title)
            .setNegativeButton("Cancel"){dialogInterface, it ->
                Toast.makeText(
                    context,
                    "Canceled",
                    Toast.LENGTH_SHORT
                ).show()}
            .setPositiveButton(action){dialogInterface, it ->
                val firstName = firstNameEditText.text.toString()
                val lastName = lastNameEditText.text.toString()
                val emailAddress = emailtAddressEditText.text.toString()
                listener.applyTexts(firstName, lastName, emailAddress)
            }

        firstNameEditText = view.first_name_et
        lastNameEditText = view.last_name_et
        emailtAddressEditText = view.email_et

        return builder.create()
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        try {
            listener = activity as ExampleDialogListener
        }catch (exception: ClassCastException){
            throw ClassCastException(context.toString())
        }

    }

    interface ExampleDialogListener {
        fun applyTexts(firstName: String?, lastName: String?, emailAddress: String?)
    }
}