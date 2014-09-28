/*
 * Copyright 2014 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package grails.plugin.console.charts

import javax.annotation.PostConstruct
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESedeKeySpec
import java.security.spec.KeySpec

/**
 * @author <a href='mailto:donbeave@gmail.com'>Alexey Zhokhov</a>
 */
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
