package com.example.includesallideas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.includesallideas.retrofit.APIClient
import com.example.includesallideas.retrofit.APIInterface
import com.example.includesallideas.retrofit.Users
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback


class AddNewFragment : Fragment() {
    lateinit var nameEditText: EditText
    lateinit var locationEditText: EditText
    lateinit var saveBtn: Button
    lateinit var backBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_add_new, container, false)

        nameEditText = view.findViewById(R.id.addName_ED)
        locationEditText = view.findViewById(R.id.addLoction_ED)
        saveBtn = view.findViewById(R.id.add_btn)
        backBtn = view.findViewById(R.id.back_btn)

        saveBtn.setOnClickListener {
            if(nameEditText.text.toString().isNotEmpty() && locationEditText.text.toString().isNotEmpty()){
                val user = Users(0,nameEditText.text.toString(),locationEditText.text.toString())
                addNewUser(user, onResult = {
                    nameEditText.setText("")
                    locationEditText.setText("")
                })
            }else{
                Toast.makeText(requireContext(), "Error",Toast.LENGTH_LONG).show()
            }

            nameEditText.clearFocus()
            locationEditText.clearFocus()
        }

        backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_addNewFragment_to_mainFragments)
        }
        return view
    }

    private fun addNewUser(user: Users, onResult: () -> Unit){
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        if(apiInterface != null) {
            apiInterface.addUser(user).enqueue(object : Callback<Users?> {
                override fun onResponse(call: Call<Users?>, response: Response<Users?>) {
                    onResult()
                    Toast.makeText(requireContext(), "Save Success!", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<Users?>, t: Throwable) {
                    onResult()
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}