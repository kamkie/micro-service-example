package net.devopssolutions.microservice.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@SuppressWarnings("deprecation")
@Slf4j
@Configuration
public class TrustAllManager implements TrustManager, X509TrustManager {

    public TrustAllManager() {
        try {
            log.info("init TrustAllManager");
            System.setProperty("com.netflix.eureka.shouldSSLConnectionsUseSystemSocketFactory", "true");
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
            SSLContext sslContext = SSLContext.getInstance("Ssl");
            SSLContext tlsContext = SSLContext.getInstance("TLS");
            SecureRandom secureRandom = new SecureRandom();
            TrustManager[] trustManagers = {this};
            sslContext.init(null, trustManagers, secureRandom);
            tlsContext.init(null, trustManagers, secureRandom);
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            SSLContext.setDefault(sslContext);

            Class<SSLSocketFactory> sslSocketFactoryClass = SSLSocketFactory.class;
            Field browser_compatible_hostname_verifier = sslSocketFactoryClass.getDeclaredField("BROWSER_COMPATIBLE_HOSTNAME_VERIFIER");
            setFinalStatic(browser_compatible_hostname_verifier, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        } catch (Exception e) {
            log.error("error creating trust all manager", e);
        }
    }

    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }

    private static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue);
    }
}
