package vavkhan.com.sanagostar.Di;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;

import dagger.multibindings.IntoMap;
import vavkhan.com.sanagostar.ViewModels.ListViewModel;
import vavkhan.com.sanagostar.ViewModels.RegisterViewModel;
import vavkhan.com.sanagostar.ViewModels.ViewModelFactory;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel.class)
    abstract ViewModel bindsListViewModel(ListViewModel mainMenuViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel.class)
    abstract ViewModel bindsRegisterViewModel(RegisterViewModel menuViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindsViewModelFactory(ViewModelFactory movieViewModelFactory);
}
