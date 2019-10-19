package vavkhan.com.sanagostar.Di;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import vavkhan.com.sanagostar.views.ActivityList;
import vavkhan.com.sanagostar.views.RegisterActivity;

@Module
public abstract class ActivityBuilderModule {
    @ContributesAndroidInjector(modules = FragmentBuilderModule.class)
    abstract ActivityList activityList();
    @ContributesAndroidInjector(modules = FragmentBuilderModule.class)
    abstract RegisterActivity registerActivity();
    /*@ContributesAndroidInjector()
    abstract MenuActivity menuActivity();*/
}
