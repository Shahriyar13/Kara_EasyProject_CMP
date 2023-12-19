package app.duss.easyproject.presentation.ui.company.store

import app.duss.easyproject.domain.entity.Company
import app.duss.easyproject.domain.entity.Person
import app.duss.easyproject.domain.entity.RegionCity
import app.duss.easyproject.presentation.ui.company.CompanyFilter
import com.arkivanov.mvikotlin.core.store.Store

interface CompanyStore: Store<CompanyStore.Intent, CompanyStore.State, Nothing> {

    sealed class Intent {
        data class LoadByPage(val page: Int): Intent()
        data class UpdateSearchValue(val searchValue: String): Intent()
        data class Filter(val filter: CompanyFilter): Intent()
        data object New: Intent()
        data object Refresh: Intent()
        data class Update(val item: Company): Intent()
        data class UpdateSelected(val item: Company): Intent()
        data class Delete(val deletedId: Long): Intent()
        data class Edit(val id: Long): Intent()
        data object EditDone: Intent()
    }
    data class State(
        var page: Int = 0,
        var detail: Company? = null,
        val selectMode: Boolean = false,
        val filter: CompanyFilter = CompanyFilter.ALL,
        val isLoading: Boolean = false,
        val isLastPageLoaded: Boolean = false,
        val error: String? = null,
        val list: List<Company> = emptyList(),
        val cities: List<RegionCity> = emptyList(),
        val people: List<Person> = emptyList(),
        val selected: List<Company> = emptyList(),
        val selectingDone: Boolean= false,
        val searchValue: String = "",
    )

}