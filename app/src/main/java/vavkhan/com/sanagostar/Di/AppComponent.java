package vavkhan.com.sanagostar.Di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import vavkhan.com.sanagostar.SanaApplication;


@Singleton
@Component(modules = {AppMadule.class,
        AndroidInjectionModule.class,
        ActivityBuilderModule.class,
        ViewModelModule.class})

public interface AppComponent {


    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(SanaApplication application);

}