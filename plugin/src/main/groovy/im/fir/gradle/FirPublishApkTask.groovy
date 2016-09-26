package im.fir.gradle
import com.android.build.gradle.api.ApkVariantOutput
import com.android.builder.model.ProductFlavor
import im.fir.module.App
import im.fir.module.Mapping
import net.dongliu.apk.parser.ApkParser
import net.dongliu.apk.parser.bean.Icon
import org.gradle.api.tasks.TaskAction

class FirPublishApkTask extends FirPublishTask {
    static def MAX_CHARACTER_LENGTH_FOR_WHATS_NEW_TEXT = 500
    static def FILE_NAME_FOR_WHATS_NEW_TEXT = "whatsnew"


    @TaskAction
    publishApk() {
        super.publish()
        String changeLog;
        def log = project.logger;

        def apkOutput = variant.outputs.find { variantOutput -> variantOutput instanceof ApkVariantOutput }

        String apkPath = apkOutput.outputFile.getAbsolutePath()
        log.warn("apkPath ===> " + apkPath)
        Iterator<ProductFlavor> iterator = variant.productFlavors.iterator();
        while ( iterator.hasNext()){
          ProductFlavor flavor =  iterator.next();
            log.warn("flavor ===> " + flavor.getName());
            Map<String, Object> map = flavor.getManifestPlaceholders();
            if (map.containsKey("FIR_CHANGE_LOG_VALUE")){
                changeLog = map.get("FIR_CHANGE_LOG_VALUE");

        }}
        parseApk(apkPath,app);
        app.setAppPath(apkPath)
        if (changeLog){
            app.setChangeLog(changeLog);
        } else if(firExtension.changeLog != null){
            app.setChangeLog(firExtension.changeLog);
        }
        Mapping mapping =null;
        if (variant.mappingFile != null && bugHdExtension != null) {
            String mappingPath = variant.mappingFile.getAbsolutePath();
            mapping = new Mapping();
            mapping.setFilePath(mappingPath);
            mapping.setApiToken(bugHdExtension.apiToken)
            mapping.setProjectId(bugHdExtension.projectId)
        }
        client.deployFile(app, mapping, firExtension.apiToken);

//        FileContent newApkFile = new FileContent(AndroidPublisherHelper.MIME_TYPE_APK, apkOutput.outputFile)

//        Apk apk = edits.apks()
//                .upload(variant.applicationId, editId, newApkFile)
//                .exe -- cute()
//
//        Track newTrack = new Track().setVersionCodes([apk.getVersionCode()])
//        if (extension.track?.equals("rollout")) {
//            newTrack.setUserFraction(extension.userFraction)
//        }
//        edits.tracks()
//                .update(variant.applicationId, ed itId, extension.track, newTrack)
//                .execute()
//
//        if (inputFolder.exists()) {
//
//            // Matches if locale have the correct naming e.g. en-US for play store
//            inputFolder.eachDirMatch(matcher) { dir ->
//                File whatsNewFile = new File(dir, FILE_NAME_FOR_WHATS_NEW_TEXT + "-" + extension.track)
//
//                if (!whatsNewFile.exists()) {
//                    whatsNewFile = new File(dir, FILE_NAME_FOR_WHATS_NEW_TEXT)
//                }
//
//                if (whatsNewFile.exists()) {
//
//                    def whatsNewText = TaskHelper.readAndTrimFile(whatsNewFile, MAX_CHARACTER_LENGTH_FOR_WHATS_NEW_TEXT, extension.errorOnSizeLimit)
//                    def locale = dir.name
//
//                    ApkListing newApkListing = new ApkListing().setRecentChanges(whatsNewText)
//                    edits.apklistings()
//                            .update(variant.applicationId, editId, apk.getVersionCode(), locale, newApkListing)
//                            .execute()
//                }
//            }
//
//        }
//
//        edits.commit(variant.applicationId, editId).execute()
    }

    static App parseApk(String apkPath, App app) {
        ApkParser apkParser = null;
        try {
//            String path = appPath.getAbsolutePath();
            File apkFile = new File(apkPath);
            apkParser = new ApkParser(apkFile);
            Icon icon = apkParser.getIconFile();
            String iconPath = icon.getPath();
            app.setName(apkParser.apkMeta.name);
            String[] strs;
            if (iconPath != null) {
                strs = iconPath.split("/");
                createFile(icon.getData(), apkFile.getParent(), strs[(strs.length - 1)], app);
            }
            return app;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                apkParser.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    static void createFile(byte[] bytes, String path, String name, App app) {
        try {
            FileOutputStream fos = new FileOutputStream(path + "/" + name);
            fos.write(bytes);
            app.setIconPath(path + "/" + name);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}