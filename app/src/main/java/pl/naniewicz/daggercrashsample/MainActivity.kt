package pl.naniewicz.daggercrashsample

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Scope

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}

class App : Application(),
    Application.ActivityLifecycleCallbacks by EmptyActivityLifecycleCallbacks
//Application.ActivityLifecycleCallbacks // uncomment and implement to stop dagger crashing
{

    private val component = DaggerApplicationComponent.create()

    @Inject
    lateinit var thermosiphon: Thermosiphon

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
        Log.d("App", "Thermosiphon $thermosiphon")
    }

    /* override fun onActivityCreated(p0: Activity, p1: Bundle?) {
         TODO("Not yet implemented")
     }

     override fun onActivityStarted(p0: Activity) {
         TODO("Not yet implemented")
     }

     override fun onActivityResumed(p0: Activity) {
         TODO("Not yet implemented")
     }

     override fun onActivityPaused(p0: Activity) {
         TODO("Not yet implemented")
     }

     override fun onActivityStopped(p0: Activity) {
         TODO("Not yet implemented")
     }

     override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
         TODO("Not yet implemented")
     }

     override fun onActivityDestroyed(p0: Activity) {
         TODO("Not yet implemented")
     }*/
}

object EmptyActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
    override fun onActivityPaused(activity: Activity) = Unit
    override fun onActivityResumed(activity: Activity) = Unit
    override fun onActivityStarted(activity: Activity) = Unit
    override fun onActivityDestroyed(activity: Activity) = Unit
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit
    override fun onActivityStopped(activity: Activity) = Unit
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) = Unit
}


@Scope
internal annotation class AppScope

@Component(
    modules = [ThermosiphonsModule::class]
)
@AppScope
interface ApplicationComponent {

    fun inject(app: App)
}

@Module
object ThermosiphonsModule {
    @Provides
    fun thermosiphon() = Thermosiphon("xyz")
}

class Thermosiphon(val heater: String)
