package vavkhan.com.sanagostar.Di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import vavkhan.com.sanagostar.views.DrawerFragment;
import vavkhan.com.sanagostar.views.MapFragment;
import vavkhan.com.sanagostar.views.RegisterFragment;

@Module
public abstract class FragmentBuilderModule {
    @ContributesAndroidInjector
    abstract DrawerFragment contributeDrawerFragment();
    @ContributesAndroidInjector
    abstract RegisterFragment contributeRegisterFragment();
    @ContributesAndroidInjector
    abstract MapFragment contributeMapFragment();
}
