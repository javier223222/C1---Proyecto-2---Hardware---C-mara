package com.example.myapplication.data.repository

import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import android.util.Base64

fun encryptData(data: String): String {
    val key = "ELTopn4590ABCDE1"
    val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
    val secretKey: SecretKey = SecretKeySpec(key.toByteArray(), "AES")
    cipher.init(Cipher.ENCRYPT_MODE, secretKey)
    val encryptedData = cipher.doFinal(data.toByteArray())
    return Base64.encodeToString(encryptedData, Base64.DEFAULT)
}
