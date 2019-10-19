package vavkhan.com.sanagostar

import android.app.Activity
import android.app.Application
import android.content.Context
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import vavkhan.com.sanagostar.Di.DaggerAppComponent
import javax.inject.Inject

class SanaApplication : Application(), HasActivityInjector {
    @Inject
    lateinit var activityDispatchingInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)
        if (mRequestQues == null) {
            mRequestQues = Volley.newRequestQueue(this)
        }
    }

    fun <T> addToRequestQueue(req: Request<T>, baseTag: String?) {
        req.retryPolicy = DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        req.tag = baseTag
        mRequestQues!!.add(req)
    }

    override fun activityInjector() = activityDispatchingInjector

    companion object {
        val TAG = SanaApplication::class.java.simpleName
        private var mRequestQues: RequestQueue? = null
        operator fun get(context: Context): SanaApplication {
            return context.applicationContext as SanaApplication
        }
    }
}