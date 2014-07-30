package org.grails.plugin.console.charts

import javax.annotation.PostConstruct
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESedeKeySpec
import java.security.spec.KeySpec

class ChartsEncryprionService {

    static final String unicodeFormat = 'UTF8'
    static final String encryptionScheme = 'DESede'

    Cipher cipher
    SecretKey key

    String encrypt(String unencryptedString) {
        cipher.init(Cipher.ENCRYPT_MODE, key)

        byte[] plainText = unencryptedString.getBytes(unicodeFormat)
        byte[] encryptedText = cipher.doFinal(plainText)

        encryptedText.encodeAsBase64()
    }

    String decrypt(String encryptedString) {
        cipher.init(Cipher.DECRYPT_MODE, key)

        byte[] encryptedText = encryptedString.decodeBase64()
        byte[] plainText = cipher.doFinal(encryptedText)

        new String(plainText, unicodeFormat)
    }

    @PostConstruct
    void init() {
        String myEncryptionKey = "ThisIsSecretEncryptionKey"

        KeySpec keySpec = new DESedeKeySpec(myEncryptionKey.getBytes(unicodeFormat))
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(encryptionScheme)

        cipher = Cipher.getInstance(encryptionScheme)
        key = keyFactory.generateSecret(keySpec)
    }

}
