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
            val component =
                (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TogetherApplication).appComponent

            TodayViewModel(
                component.authRepository(),
                component.usersHouseworkRepository(),
                component.houseworkRepository(),
                component.userRepository(),
                component.getUserHouseworkListUseCase(),
            )
        }
        initializer {
            val component =
                (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TogetherApplication).appComponent

            HouseworkViewModel(
                component.houseworkRepository(),
                component.userRepository(),
                component.authRepository(),
            )
        }
        initializer {
            val component =
                (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TogetherApplication).appComponent

            RatingViewModel(
                component.authRepository(),
                component.userRepository(),
                component.getUserRatingUseCase(),
            )
        }
        initializer {
            val component =
                (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TogetherApplication).appComponent

            ProfileViewModel(
                component.authRepository(),
                component.userRepository(),
                component.familyRepository(),
                component.deleteUserFromFamilyUseCase(),
                component.deleteUserUseCase(),
            )
        }
        initializer {
            val component =
                (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TogetherApplication).appComponent

            LoginViewModel(
                component.authRepository(),
                component.userRepository(),
            )
        }
        initializer {
            val component =
                (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TogetherApplication).appComponent

            TogetherViewModel(
                component.authRepository(),
                component.userRepository(),
            )
        }
        initializer {
            val component =
                (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TogetherApplication).appComponent

            FamilyViewModel(
                component.authRepository(),
                component.familyRepository(),
                component.userRepository(),
            )
        }
    }
}
