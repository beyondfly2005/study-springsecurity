package com.intellsecurity.uaa.uaa.config;

import com.intellsecurity.uaa.uaa.util.PwdEnUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Copyright: Copyright (c) 2020 Anchuang Network Technology Co., Ltd
 *
 * @ClassName: com.intellsecurity.uaa.config.Md5AndSH1MixPasswordEncoder
 * @Description: 该类的描述
 * @version: V0.1.0
 * @author: gaolongfei
 * @date: 2020/12/18 9:43
 */
public class SHAMd5MixPasswordEncoder implements PasswordEncoder {

    private final Log logger;

    public SHAMd5MixPasswordEncoder(){
        this.logger = LogFactory.getLog(this.getClass());
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return PwdEnUtil.getMD5(PwdEnUtil.encryptToSHA(PwdEnUtil.getMD5(PwdEnUtil.encryptToSHA(PwdEnUtil.getMD5(rawPassword.toString())))));
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (encodedPassword != null && encodedPassword.length() != 0) {
            if (!this.encode(rawPassword.toString()).equals(encodedPassword)) {
                this.logger.warn("Encoded password does not look like BCrypt");
                return false;
            } else {
                return true;
            }
        } else {
            this.logger.warn("Empty encoded password");
            return false;
        }
    }

    public static void main(String[] args) {
        //System.out.println(PwdEnUtil.getMD5(PwdEnUtil.encryptToSHA(PwdEnUtil.getMD5(PwdEnUtil.encryptToSHA(PwdEnUtil.getMD5("secret"))))));
        System.out.println(PwdEnUtil.getMD5(PwdEnUtil.encryptToSHA(PwdEnUtil.getMD5(PwdEnUtil.encryptToSHA(PwdEnUtil.getMD5("123"))))));
        System.out.println(PwdEnUtil.getMD5(PwdEnUtil.encryptToSHA(PwdEnUtil.getMD5(PwdEnUtil.encryptToSHA(PwdEnUtil.getMD5("secret"))))));
    }
}
