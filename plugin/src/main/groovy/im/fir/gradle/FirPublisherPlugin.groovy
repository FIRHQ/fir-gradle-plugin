package im.fir.gradle
import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class FirPublisherPlugin implements Plugin<Project> {

        public static final String FIR_IM_GROUP = "fir.im"

        @Override
        void apply(Project project) {
            def log = project.logger

            def hasAppPlugin = project.plugins.find { p -> p instanceof AppPlugin }
            if (!hasAppPlugin) {
                throw new IllegalStateException("The 'com.android.application' plugin is required.")
            }

            def firExtension = project.extensions.create('fir', FirPublisherPluginExtension)
            def bugHdExtension = project.extensions.create('bughd', BugHdPublisherPluginExtension)

            project.android.applicationVariants.all { variant ->
//                if (variant.buildType.isDebuggable()) {
//                    log.debug("Skipping debuggable build type ${variant.buildType.name}.")
//                    return
//                }

                if (firExtension == null) {
                    log.error("Please config your fir.im apiToken in your build.gradle.")
                    return
                }
                def buildTypeName = variant.buildType.name.capitalize()

                def productFlavorNames = variant.productFlavors.collect { it.name.capitalize() }
                if (productFlavorNames.isEmpty()) {
                    productFlavorNames = [""]
                }
                def productFlavorName = productFlavorNames.join('')
//                def flavor = StringUtils.uncapitalize(productFlavorName)

                def variationName = "${productFlavorName}${buildTypeName}"

//                def bootstrapTaskName = "bootstrap${variationName}PlayResources"
//                def playResourcesTaskName = "generate${variationName}PlayResources"
                def publishApkTaskName = "publishApk${variationName}"
                def outputData = variant.outputs.first()
                def assembleTask = variant.assemble

                def variantData = variant.variantData

                // Create and configure bootstrap task for this variant.
//                def bootstrapTask = project.tasks.create(bootstrapTaskName, BootstrapTask)
//                bootstrapTask.extension = extension
//                bootstrapTask.variant = variant
//                if (StringUtils.isNotEmpty(flavor)) {
//                    bootstrapTask.outputFolder = new File(project.projectDir, "src/${flavor}/play")
//                } else {
//                    bootstrapTask.outputFolder = new File(project.projectDir, "src/main/play")
//                }
//                bootstrapTask.description = "Downloads the play store listing for the ${variationName} build. No download of image resources. See #18."
//                bootstrapTask.group = PLAY_STORE_GROUP

                // Create and configure task to collect the play store resources.
//                def playResourcesTask = project.tasks.create(playResourcesTaskName, GeneratePlayResourcesTask)
//
//                playResourcesTask.inputs.file(new File(project.projectDir, "src/main/play"))
//                if (StringUtils.isNotEmpty(flavor)) {
//                    playResourcesTask.inputs.file(new File(project.projectDir, "src/${flavor}/play"))
//                }
//                playResourcesTask.inputs.file(new File(project.projectDir, "src/${variant.buildType.name}/play"))
//                if (StringUtils.isNotEmpty(flavor)) {
//                    playResourcesTask.inputs.file(new File(project.projectDir, "src/${variant.name}/play"))
//                }
//
//                playResourcesTask.outputFolder = new File(project.projectDir, "build/outputs/play/${variant.name}")
//                playResourcesTask.description = "Collects play store resources for the ${variationName} build"
//                playResourcesTask.group = PLAY_STORE_GROUP

                // Create and configure publisher meta task for this variant
//                def publishListingTask = project.tasks.create(publishListingTaskName, PlayPublishListingTask)
//                publishListingTask.extension = extension
//                publishListingTask.variant = variant
//                publishListingTask.inputFolder = playResourcesTask.outputFolder
//                publishListingTask.description = "Updates the play store listing for the ${variationName} build"
//                publishListingTask.group = PLAY_STORE_GROUP
//
//                // Attach tasks to task graph.
//                publishListingTask.dependsOn playResourcesTask

                log.warn("variantData.zipAlignEnabled === >" + variantData.zipAlignEnabled);
                if (variantData.zipAlignEnabled) {
                    // Create and configure publisher apk task for this variant.
                    log.warn("publishApkTaskName === " + publishApkTaskName);

                    def publishApkTask = project.tasks.create(publishApkTaskName, FirPublishApkTask)
                    publishApkTask.firExtension = firExtension
                    if(bugHdExtension.apiToken != null && bugHdExtension.projectId != null){
                        log.warn("bugHdExtension === bugHdExtension not null");
                        publishApkTask.bugHdExtension = bugHdExtension
                    }
                    publishApkTask.variant = variant
//                    publishApkTask.inputFolder = playResourcesTask.outputFolder
                    publishApkTask.description = "Uploads the APK for the ${variationName} build"
                    publishApkTask.group  = FIR_IM_GROUP

//                    def publishTask = project.tasks.create(publishTaskName)
//                    publishTask.description = "Updates APK and play store listing for the ${variationName} build"
//                    publishTask.group = FIR_IM_GROUP
//                    log.warn("FIR_IM_GROUP === " + FIR_IM_GROUP);
//
//                    // Attach tasks to task graph.
//                    publishTask.dependsOn publishApkTask
//                    publishTask.dependsOn publishListingTask
//                    publishApkTask.dependsOn playResourcesTask
                    publishApkTask.dependsOn assembleTask
                } else {
                    log.warn("Could not find ZipAlign task. Did you specify a signingConfig for the variation ${variationName}?")
                }
            }
        }

    }
