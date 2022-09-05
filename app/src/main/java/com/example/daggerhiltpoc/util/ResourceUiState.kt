package com.example.daggerhiltpoc.util

import com.example.daggerhiltpoc.model.Users

sealed class ResourceUiState{
    data class Success(val users:Users):ResourceUiState()
    data class Failed (val errorMsg:String?):ResourceUiState()
}
