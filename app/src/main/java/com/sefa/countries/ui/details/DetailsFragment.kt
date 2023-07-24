package com.sefa.countries.ui.details

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import coil.load
import com.sefa.countries.R
import com.sefa.countries.data.model.Countries
import com.sefa.countries.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val args by navArgs<DetailsFragmentArgs>()
    private lateinit var countries: Countries

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_details,container,false)
        countries = args.country
        binding.detailsInstance = this

        binding.collapsingToolbar.setExpandedTitleTextAppearance(R.style.ToolbarExpandedTextAppearance)
        binding.collapsingToolbar.setCollapsedTitleTextAppearance(R.style.ToolbarCollapsedTextAppearance)
        binding.collapsingToolbar.collapsedTitleTextSize = 70F

        countries.let {
            binding.country = it
            binding.imageViewFlag.load(it.image)
        }

        val bitmap : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.tr)
        Palette.from(bitmap).generate{ palette->
            palette?.let {
                binding.collapsingToolbar.setContentScrimColor(palette.getMutedColor(androidx.appcompat.R.attr.colorPrimary))
            }
        }
        return  binding.root
    }

    fun backFromDetay()
    {
        findNavController().popBackStack()
    }
}