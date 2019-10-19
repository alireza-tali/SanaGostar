package vavkhan.com.sanagostar.Di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppMadule {
    Application application;
    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application.getApplicationContext();
    }

}
