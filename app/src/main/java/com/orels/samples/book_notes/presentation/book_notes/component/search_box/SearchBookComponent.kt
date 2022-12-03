package com.orels.samples.book_notes.presentation.book_notes.component.search_box

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.orels.components.Input
import com.orels.components.InputType
import com.orels.samples.R
import com.orels.samples.book_notes.domain.model.Book

@Composable
fun SearchBookComponent(
    onBookSelected: (Book) -> Unit,
    viewModel: SearchBookViewModel = hiltViewModel(),
) {

    val state = viewModel.state

    Column {
        Input(
            title = stringResource(R.string.search),
            onTextChange = { viewModel.onSearchBoxEvent(SearchBoxEvent.StartSearch(it)) },
            placeholder = stringResource(id = R.string.the_lord_of_the_rings),
            inputType = InputType.Text,
            isLoading = state.isLoading
        )
    }

}