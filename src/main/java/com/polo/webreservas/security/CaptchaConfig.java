package com.polo.webreservas.security;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

@Configuration
public class CaptchaConfig {

    @Bean
    public DefaultKaptcha captchaProducer() {
        Properties props = new Properties();
        props.setProperty("kaptcha.image.width", "250");
        props.setProperty("kaptcha.image.height", "80");
        props.setProperty("kaptcha.textproducer.font.size", "60");

        // ✅ Soporte para árabe y chino
        props.setProperty("kaptcha.textproducer.font.names", "Arial,宋体,Times New Roman");

        // ✅ Puedes alternar aquí según el idioma
        props.setProperty("kaptcha.textproducer.char.string", "ABCDEFGHIJKLMNOPQRSTUVWXYZ");

        props.setProperty("kaptcha.textproducer.char.length", "4");
        props.setProperty("kaptcha.textproducer.char.space", "10");

        // Opcional: color y border
        props.setProperty("kaptcha.border", "no");
        props.setProperty("kaptcha.textproducer.font.color", "black");

        Config config = new Config(props);
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        kaptcha.setConfig(config);
        return kaptcha;
    }
}
