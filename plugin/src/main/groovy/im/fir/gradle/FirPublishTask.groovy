package im.fir.gradle

import com.android.build.gradle.api.ApplicationVariant
import im.fir.http.FirClient
import im.fir.module.App
import im.fir.module.Mapping
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class FirPublishTask extends DefaultTask{
    FirPublisherPluginExtension firExtension
    BugHdPublisherPluginExtension bugHdExtension
    FirClient client
    ApplicationVariant variant
    App app

    def publish(){
        if(client == null){
            client = FirPublisherHelper.init(firExtension)
        }
        app = new App()
        app.setBundleId(variant.applicationId)
        app.setAppType("android")
        app.setBuild(variant.versionCode + "")
        app.setVersion(variant.versionName)
    }
}