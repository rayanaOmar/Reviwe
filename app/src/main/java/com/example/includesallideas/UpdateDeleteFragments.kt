package com.example.includesallideas


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.includesallideas.retrofit.APIClient
import com.example.includesallideas.retrofit.APIInterface
import com.example.includesallideas.retrofit.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateDeleteFragments : Fragment() {
    lateinit var newName:EditText
    lateinit var newLocation:EditText
    lateinit var deleteById: EditText
    lateinit var updateBtn:Button
    lateinit var deleteBtn:Button
  override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_update_delete_fragments, container, false)
        newName=view.findViewById(R.id.editName_ED)
        newLocation=view.findViewById(R.id.editLoction_ED)
        updateBtn=view.findViewById(R.id.update_btn)
        deleteBtn=view.findViewById(R.id.delete_btn)
        deleteById = view.findViewById(R.id.id_ED)

        updateBtn.setOnClickListener {
        if(newName.text.toString().isNotEmpty() && newLocation.text.toString().isNotEmpty()){
            val newUser= Users(0,newName.text.toString(),newLocation.text.toString())
            updateUser(newUser, onResult = {
                newName.setText("")
                newLocation.setText("")
            })
        }else{
          Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
        }

            newName.clearFocus()
            newLocation.clearFocus()
        }

        deleteBtn.setOnClickListener {
            if (deleteById.text.isNotEmpty()) {
                var user = Users(deleteById.text.toString().toInt(),"","")
                deleteUser(user, onResult = {
                    deleteById.setText("")

                })
            } else {
                Toast.makeText(requireContext(), "Enter ID please", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    fun updateUser(newUser: Users, onResult:() -> Unit){
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        if (apiInterface != null) {
            apiInterface.updateUser(deleteById.text.toString().toInt(),newUser).enqueue(object:
                Callback<Users> {
                override fun onResponse(
                    call: Call<Users>,
                    response: Response<Users>
                ) {
                    onResult()
                    Toast.makeText(requireContext(), "Update Success!", Toast.LENGTH_SHORT).show();
                }

                override fun onFailure(call: Call<Users>, t: Throwable) {
                    onResult()
                    Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show();
                }
            })
        }
    }

    fun deleteUser(user: Users, onResult: () -> Unit) {

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        if (apiInterface != null) {
            apiInterface.deleteUser(user.pk!!).enqueue(object : Callback<Void> {
                override fun onResponse(
                    call: Call<Void>,
                    response: Response<Void>
                ) {
                    onResult()
                    Toast.makeText(requireContext(), "Delete Success!", Toast.LENGTH_SHORT).show();
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    onResult()
                    Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show();
                }
            })
        }
    }
}


