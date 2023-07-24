package com.sefa.countries.ui.main

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sefa.countries.R
import com.sefa.countries.data.util.Resource
import com.sefa.countries.databinding.FragmentMainBinding
import com.sefa.countries.ui.main.adapter.CountryAdapter
import com.sefa.countries.ui.main.viewmodel.GetDataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val getDataViewModel : GetDataViewModel by viewModels()
    private lateinit var countrAdapter: CountryAdapter
    private var currentTime : Long? = null
    private var isTimeChanging : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_main,container,false)
        setupRV()
        showSnackbar(container,"Oops!, No Internet Connection...")
        return binding.root
    }

    private fun showSnackbar(container: ViewGroup?, text : String)
    {
        if (!isOnline(requireContext().applicationContext))
        {
            container?.let {
                Snackbar.make(it,text,Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        isTimeChanging=false
    }

    private fun setupRV()
    {
        countrAdapter = CountryAdapter()
        binding.recyclerView.apply {
            adapter=countrAdapter
            layoutManager =LinearLayoutManager(context)
            setHasFixedSize(true)
        }
        dataFromRepo()
    }

    fun isOnline(context: Context): Boolean
    {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    private fun dataFromRepo()
    {
        getDataViewModel.getCountries.observe(viewLifecycleOwner){ countriesList->
            when(countriesList)
            {
                is Resource.Success -> {
                    countriesList.data?.get(0).let { Log.e("inMainFragmentdataFromRepo", it.toString()) }
                    countriesList.data?.let { countrAdapter.submitList(it) }
                }

                is Resource.Error -> {
                    Toast.makeText(context,"Oops!, data didn't pull...",Toast.LENGTH_SHORT).show()
                }

                is Resource.Loading ->
                {

                }
            }
        }
    }
}