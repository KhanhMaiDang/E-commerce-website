package com.example.ecommerceweb.errorHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
@Slf4j
public class MaxSizeErrorHandler {
    @ExceptionHandler
    public String maxSizeError(MultipartException e, RedirectAttributes redirectAttributes) {
        log.info("Max File Size Exception Occurs");
        redirectAttributes.addFlashAttribute("message", "Excced Max File Size Error");
        return "redirect:/";
    }
}
