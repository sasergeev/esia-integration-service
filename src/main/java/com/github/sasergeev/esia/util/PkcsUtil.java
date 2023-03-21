package com.github.sasergeev.esia.util;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.*;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
@Slf4j
public class PkcsUtil {
    @Value("${esia.path-to-keystore}")
    private String PATH_TO_KEYSTORE;
    @Value("${esia.key-alias}")
    private String KEY_ALIAS;
    @Value("${esia.keystore-password}")
    private String KEYSTORE_PASSWORD;

    @PostConstruct
    private KeyStore loadKeyStore() throws KeyStoreException, NoSuchAlgorithmException, CertificateException {
        KeyStore keystore = KeyStore.getInstance("PKCS12");
        try {
            ClassPathResource classPathResource = new ClassPathResource(PATH_TO_KEYSTORE);
            InputStream is = classPathResource.getInputStream();
            keystore.load(is, KEYSTORE_PASSWORD.toCharArray());
        } catch (IOException e) {
            log.error("Error: {}", getClass().getSimpleName() + " " + e.getMessage());
        }
        return keystore;
    }

    private CMSSignedDataGenerator setUpProvider(final KeyStore keystore) throws Exception {

        Security.addProvider(new BouncyCastleProvider());
        Certificate[] certificateChain = keystore.getCertificateChain(KEY_ALIAS);
        final List<Certificate> certificates = new ArrayList<>();

        for (int i = 0, length = certificateChain == null ? 0 : certificateChain.length; i < length; i++) {
            certificates.add(certificateChain[i]);
        }

        Store store = new JcaCertStore(certificates);
        Certificate cert = keystore.getCertificate(KEY_ALIAS);

        String SIGNATURE_ALG = "GOST3411WITHECGOST3410-2012-256";
        ContentSigner signer = new JcaContentSignerBuilder(SIGNATURE_ALG).setProvider("BC").build((PrivateKey) (keystore.getKey(KEY_ALIAS, KEYSTORE_PASSWORD.toCharArray())));

        CMSSignedDataGenerator generator = new CMSSignedDataGenerator();
        generator.addSignerInfoGenerator(new JcaSignerInfoGeneratorBuilder(new JcaDigestCalculatorProviderBuilder().setProvider("BC").build()).build(signer, (X509Certificate) cert));

        generator.addCertificates(store);

        return generator;
    }

    private byte[] signPkcs(final byte[] content, final CMSSignedDataGenerator generator) throws CMSException, IOException {
        CMSTypedData cmsTypedData = new CMSProcessableByteArray(content);
        CMSSignedData signedData = generator.generate(cmsTypedData, true);
        return signedData.getEncoded();
    }

    public String getUrlSafeSign(final String content) {
        try {
            byte[] signedBytes = signPkcs(content.getBytes(StandardCharsets.UTF_8), setUpProvider(loadKeyStore()));
            return new String(Base64.getUrlEncoder().encode(signedBytes));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
