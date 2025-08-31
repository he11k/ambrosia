package es.boffmedia.mcef.download;

import es.boffmedia.mcef.MCEF;
import es.boffmedia.mcef.MCEFDownloader;
import es.boffmedia.mcef.MCEFPlatform;
import es.boffmedia.mcef.MCEFSettings;
import es.boffmedia.mcef.internal.MCEFDownloadListener;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.io.IOException;

public class MCEFDownloadManager {
    private static void setupLibraryPath() throws IOException {
        final File mcefLibrariesDir;

        // Check for development environment
        // TODO: handle eclipse/others
        // i.e. mcef-repo/forge/build
        File buildDir = new File("../build");
        if (buildDir.exists() && buildDir.isDirectory()) {
            mcefLibrariesDir = new File(buildDir, "mcef-libraries/");
        } else {
            mcefLibrariesDir = new File("mods/mcef-libraries/");
        }

        mcefLibrariesDir.mkdirs();

        System.setProperty("mcef.libraries.path", mcefLibrariesDir.getCanonicalPath());
        System.setProperty("jcef.path", new File(mcefLibrariesDir, MCEFPlatform.getPlatform().getNormalizedName()).getCanonicalPath());
    }

    public static void sinit() {
        try {
            setupLibraryPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread downloadThread = new Thread(() -> {
            String javaCefCommit;

            try {
                javaCefCommit = MCEF.getJavaCefCommit();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            MCEF.getLogger().info("java-cef commit: " + javaCefCommit);

            MCEFSettings settings = MCEF.getSettings();
            MCEFDownloader downloader = new MCEFDownloader(settings.getDownloadMirror(), settings.getJavaCefCommitHash(), MCEFPlatform.getPlatform());

            boolean downloadJcefBuild;

            // We always download the checksum for the java-cef build
            // We will compare this with mcef-libraries/<platform>.tar.gz.sha256
            // If the contents of the files differ (or it doesn't exist locally), we know we need to redownload JCEF
            try {
                downloadJcefBuild = !downloader.downloadJavaCefChecksum();
            } catch (IOException e) {
                e.printStackTrace();
                MCEFDownloadListener.INSTANCE.setFailed(true);
                return;
            }

            // Ensure the mcef-libraries directory exists
            // If not, we want to try redownloading
            File mcefLibrariesDir = new File(System.getProperty("mcef.libraries.path"));
            downloadJcefBuild |= !mcefLibrariesDir.exists();

            if (downloadJcefBuild && !settings.isSkipDownload()) {
                try {
                    downloader.downloadJavaCefBuild();
                } catch (IOException e) {
                    e.printStackTrace();
                    MCEFDownloadListener.INSTANCE.setFailed(true);
                    return;
                }

                downloader.extractJavaCefBuild(true);
            }

            MCEFDownloadListener.INSTANCE.setDone(true);
        });
        downloadThread.start();
    }
}
