package com.example.includesallideas

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.includesallideas.roomDatabase.UserDatabase
import com.example.includesallideas.roomDatabase.UserEntity
import com.example.includesallideas.rvAdapter.RVAdapter
import com.example.includesallideas.rvAdapter.RVAdapterFav
import com.example.includesallideas.viewModel.MyViewModel

class FavoriteFragments : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var backBtn: Button

    lateinit var  listViewModel :MyViewModel
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_favorite_fragments, container, false)

        recyclerView = view.findViewById(R.id.recycler_View2)
        backBtn = view.findViewById(R.id.backFav_btn)
        listViewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        //To access MainActivity here
        sharedPreferences = requireActivity().getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )

        val userId = sharedPreferences.getInt("userId", 0)
        val userName = sharedPreferences.getString("userName","")
        val userLocation = sharedPreferences.getString("userLocation","")

        if(userId!=-1 && userId!=-2) {
            listViewModel.addUser(userId, userName!!, userLocation!!)
            //show Toast
            Toast.makeText(requireContext(), "Save Successfully!!", Toast.LENGTH_SHORT).show()
        } else if(userId==-1) {
            Toast.makeText(requireContext(), "didn't add successfully", Toast.LENGTH_SHORT).show()
        }

        //listViewModel
        listViewModel.getAllUserInfo().observe(viewLifecycleOwner, { users ->

            recyclerView.adapter = RVAdapterFav(this, users)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())

        })

        listViewModel.getAllUserInfo()

        backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_favoriteFragments_to_mainFragments2)
        }

        return view
    }


}

//private lateinit var addNewBtn: Button
//private lateinit var favoriteBtn: Button

//
//private lateinit var users: List<UserEntity>


//    //arrayList to store the user enter
//    users = arrayListOf()
//
//    //listViewModel
//    listViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
//    listViewModel.getAllUserInfo().observe(viewLifecycleOwner, { users ->
//        rvAdapter.update(users)
//    })
//    //initialization
//    addNewBtn = view.findViewById(R.id.addNew_ED)
//    favoriteBtn = view.findViewById(R.id.fav_btn)
//
//    //RecyclerView adapter section
//    recyclerView = view.findViewById(R.id.recycler_View)

//
//    //OnClick Section
//    addNewBtn.setOnClickListener {
//        findNavController()
//            .navigate(R.id.action_mainFragments_to_addNewFragment2)
//    }
//
//    favoriteBtn.setOnClickListener {
//        findNavController()
//            .navigate(R.id.action_mainFragments_to_favoriteFragments)
//    }
//    return view
//}
//}