package com.shard.config;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import org.apache.shardingsphere.spi.encrypt.ShardingEncryptor;

import java.util.Properties;

/**
 * 自定义加密，解决中文解密乱码。通过SPI方式注册
 */
public class CAESShardingEncryptor implements ShardingEncryptor {

    private String key;
    private AES aes;

    @Override
    public void init() {
        aes = SecureUtil.aes(key.getBytes(CharsetUtil.CHARSET_UTF_8));
    }

    //加密
    @Override
    public String encrypt(Object o) {
        return aes.encryptHex(String.valueOf(o));
    }

    //解密
    @Override
    public Object decrypt(String s) {
        return aes.decryptStr(s, CharsetUtil.CHARSET_UTF_8);
    }

    @Override
    public String getType() {
        return "CAES";
    }

    @Override
    public Properties getProperties() {
        return null;
    }

    @Override
    public void setProperties(Properties properties) {
        this.key = properties.getProperty("deskey");
    }
}
