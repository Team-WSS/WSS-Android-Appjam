package com.teamwss.websoso.ui.main.library

sealed class LibraryUiState {
    object Uninitialized : LibraryUiState()
    object Loading : LibraryUiState()
    object Resumed : LibraryUiState()
    object Success : LibraryUiState()
    object Error : LibraryUiState()
}