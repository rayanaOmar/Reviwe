package com.example.includesallideas

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.includesallideas.retrofit.APIClient
import com.example.includesallideas.retrofit.APIInterface
import com.example.includesallideas.retrofit.Users
import com.example.includesallideas.roomDatabase.UserEntity
import com.example.includesallideas.rvAdapter.RVAdapter
import com.example.includesallideas.viewModel.MyViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class mainFragments : Fragment() {

    private lateinit var addNewBtn: Button
    private lateinit var favoriteBtn: Button
    private lateinit var recyclerView: RecyclerView

    private var users=ArrayList<Users>()
    private lateinit var rvAdapter: RVAdapter

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main_fragments, container, false)

        //initialization
        addNewBtn = view.findViewById(R.id.addNew_ED)
        favoriteBtn = view.findViewById(R.id.fav_btn)
        recyclerView = view.findViewById(R.id.recycler_View)

        //To access MainActivity here
        sharedPreferences = requireActivity().getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )

        //RecyclerView adapter section
        rvAdapter = RVAdapter(this,users)
        recyclerView.adapter = rvAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        GlobalScope.launch {
            requestAPI()
        }

//        //function to Recycler view
//        requestAPI()
        //OnClick Section
        addNewBtn.setOnClickListener {
            findNavController()
                .navigate(R.id.action_mainFragments_to_addNewFragment2)
        }

        favoriteBtn.setOnClickListener {
            //add id to shared preferences
            with(sharedPreferences.edit()) {
                putInt("userId", -2)
                apply()
            }
            findNavController()
                .navigate(R.id.action_mainFragments_to_favoriteFragments)
        }
        return view
    }
    private fun requestAPI(){
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val call: Call<List<Users>> = apiInterface!!.getAllUsers()

        call?.enqueue(object : Callback<List<Users>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<List<Users>> ,
                response: Response<List<Users>>
            ) {
                val resource: List<Users>? = response.body()
                for(i in resource!!) {
                    //add to RV
                    users.add(i)
                }
                recyclerView.adapter?.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<Users>>, t: Throwable) {
                call.cancel()
            }
        })
    }
}