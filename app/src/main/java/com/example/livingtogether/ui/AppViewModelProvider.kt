package com.example.livingtogether.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.livingtogether.TogetherApplication
import com.example.livingtogether.TogetherViewModel
import com.example.livingtogether.ui.family.FamilyViewModel
import com.example.livingtogether.ui.housework.HouseworkViewModel
import com.example.livingtogether.ui.login.LoginViewModel
import com.example.livingtogether.ui.profile.ProfileViewModel
import com.example.livingtogether.ui.rating.RatingViewModel
import com.example.livingtogether.ui.today.TodayViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            TodayViewModel(
                (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TogetherApplication).container.togetherRepository,
                (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TogetherApplication).container.authRepository
            )
        }
        initializer {
            HouseworkViewModel(
                (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TogetherApplication).container.togetherRepository
            )
        }
        initializer {
            RatingViewModel(
                (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TogetherApplication).container.authRepository,
                (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TogetherApplication).container.userRepository
            )
        }
        initializer {
            ProfileViewModel(
                (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TogetherApplication).container.authRepository
            )
        }
        initializer {
            LoginViewModel(
                (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TogetherApplication).container.authRepository,
                (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TogetherApplication).container.userRepository
            )
        }
        initializer {
            TogetherViewModel(
                (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TogetherApplication).container.authRepository,
                (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TogetherApplication).container.userRepository
            )
        }
        initializer {
            FamilyViewModel(
                (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TogetherApplication).container.authRepository,
                (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TogetherApplication).container.familyRepository,
                (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TogetherApplication).container.userRepository
            )
        }
    }
}