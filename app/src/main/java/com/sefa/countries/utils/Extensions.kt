package com.sefa.countries.utils

import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.Navigation

fun Navigation.transition(view : View, nav : NavDirections)
{
    findNavController(view).navigate(nav)
}