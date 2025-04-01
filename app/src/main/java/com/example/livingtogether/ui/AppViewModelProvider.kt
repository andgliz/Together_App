package com.example.livingtogether.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.livingtogether.TogetherApplication
import com.example.livingtogether.ui.housework.HouseworkViewModel
import com.example.livingtogether.ui.profile.ProfileViewModel
import com.example.livingtogether.ui.rating.RatingViewModel
import com.example.livingtogether.ui.today.TodayViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            TodayViewModel(
                (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TogetherApplication).container.togetherRepository
            )
        }
        initializer {
            HouseworkViewModel(
                (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TogetherApplication).container.togetherRepository
            )
        }
        initializer {
            RatingViewModel(
                (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TogetherApplication).container.togetherRepository
            )
        }
        initializer {
            ProfileViewModel(
                (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TogetherApplication).container.togetherRepository
            )
        }
    }
}