package im.fir.gradle

import im.fir.http.FirClient
import im.fir.module.User
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

import java.security.GeneralSecurityException
import java.util.logging.Logger

public class FirPublisherHelper {
    private static final Log log = LogFactory.getLog(FirPublisherHelper.class);

    private static final String APPLICATION_NAME = "gradle-fir-publisher"

    /** Global instance of the HTTP transport. */
    private static FirClient FIR_TRANSPORT;

    protected static FirClient init(FirPublisherPluginExtension extension) {
        newTrustedTransport();
        boolean b = authorizeWithServiceAccount(extension);
        if(b){
            return FIR_TRANSPORT;
        }
    }

    private static void newTrustedTransport() throws GeneralSecurityException,
            IOException {
        if (FIR_TRANSPORT == null) {
            FIR_TRANSPORT = new FirClient();
        }
    }


    private static boolean authorizeWithServiceAccount(FirPublisherPluginExtension extension){
        if (extension.apiToken) {
            return authorizeWithFirToken(extension.apiToken)
        }

        else if (extension.email && extension.pwd) {
            return authorizeWithFirAccount(extension.email, extension.pwd);
        }
        throw new IllegalArgumentException("No credentials provided.");
    }

    private static boolean authorizeWithFirAccount(String email, String pwd) {
        log.info(String.format("Authorizing using email: %s", email));
        if (!email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
            return false;
        }
        FIR_TRANSPORT.doCheckToken();
        return true;
    }

    private static boolean authorizeWithFirToken(String token) {
        log.info(String.format("Authorizing using token: %s", token));
        if (token.length() != 32) {
            return false;
        }
        User user = FIR_TRANSPORT.doCheckToken(token);
        log.info(user.email);
        if(user == null){
            return false;
        }
        return true;
    }


}