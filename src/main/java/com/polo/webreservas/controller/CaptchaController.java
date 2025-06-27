package com.polo.webreservas.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class CaptchaController {

    @Autowired
    private DefaultKaptcha captchaProducer;

    // Generar imagen del captcha
    @GetMapping("/captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");

        // Genera texto captcha y lo guarda en sesión
        String capText = captchaProducer.createText();
        request.getSession().setAttribute("captcha", capText);

        // Genera imagen captcha
        BufferedImage captchaImage = captchaProducer.createImage(capText);
        ImageIO.write(captchaImage, "jpg", response.getOutputStream());
    }

    // Verificar captcha ingresado
    @PostMapping("/verificar-captcha")
    @ResponseBody
    public String verificarCaptcha(HttpServletRequest request, @RequestParam("captcha") String inputCaptcha) {
        String expectedCaptcha = (String) request.getSession().getAttribute("captcha");
        if (expectedCaptcha != null && expectedCaptcha.equalsIgnoreCase(inputCaptcha)) {
            return "✅ CAPTCHA correcto.";
        } else {
            return "❌ CAPTCHA incorrecto.";
        }
    }
}
