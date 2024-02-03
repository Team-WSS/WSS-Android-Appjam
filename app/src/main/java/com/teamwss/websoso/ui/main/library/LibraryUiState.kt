package com.teamwss.websoso.ui.main.library

sealed class LibraryUiState {
    object Loading : LibraryUiState()
    object Resumed : LibraryUiState()
    object Success : LibraryUiState()
    object Error : LibraryUiState()
}