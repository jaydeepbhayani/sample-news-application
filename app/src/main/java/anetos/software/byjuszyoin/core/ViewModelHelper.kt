package anetos.software.byjuszyoin.core

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import anetos.software.byjuszyoin.data.repository.Injection
import anetos.software.byjuszyoin.ui.common.DataViewModel

/**
 * Convenience factory to handle ViewModels with one parameter.
 *
 * Make a factory:
 * ```
 * // Make a factory
 * val FACTORY = viewModelFactory(::MyViewModel)
 * ```
 *
 * Use the generated factory:
 * ```
 * ViewModelProviders.of(this, FACTORY(argument))
 *
 * ```
 *
 * @param constructor A function (A) -> T that returns an instance of the ViewModel (typically pass
 * the constructor)
 * @return a function of one argument that returns ViewModelProvider.Factory for ViewModelProviders
 */
fun <T : ViewModel, A> viewModelFactoryWithSingleArg(constructor: (A) -> T):
            (A) -> ViewModelProvider.NewInstanceFactory {
    return { arg: A ->
        object : ViewModelProvider.NewInstanceFactory() {
            @Suppress("UNCHECKED_CAST")
            override fun <V : ViewModel> create(modelClass: Class<V>): V {
                return constructor(arg) as V
            }
        }
    }
}

fun Fragment.dataViewModelProvider(): DataViewModel = activity!!.run {
    ViewModelProvider(
        this,
        DataViewModel.FACTORY(Injection.provideDataRepository(context!!))
    ).get(DataViewModel::class.java)
}

fun AppCompatActivity.dataViewModelProvider(): DataViewModel = this.run {
    ViewModelProvider(
        this,
        DataViewModel.FACTORY(
            Injection.provideDataRepository(this)
        )
    ).get(DataViewModel::class.java)
}
