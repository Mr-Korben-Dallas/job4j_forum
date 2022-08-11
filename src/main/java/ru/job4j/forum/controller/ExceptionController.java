package ru.job4j.forum.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.job4j.forum.config.exception.LocalizedException;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler({ DataIntegrityViolationException.class, LocalizedException.class })
    public ModelAndView handleException(
            final DataIntegrityViolationException exception,
            final HttpServletRequest req,
            final RedirectAttributes redirectAttributes
    ) {
        String path = (String) req.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        LocalizedException localizedException = new LocalizedException(exception.getMessage());
        String exceptionMessage = localizedException.getLocalizedMessage();
        redirectAttributes.addFlashAttribute("errorMessage", exceptionMessage);
        return new ModelAndView("redirect:" + path);
    }
}
