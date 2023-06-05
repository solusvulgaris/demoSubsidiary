package com.ak.springbootdemo.sub.web;

import com.ak.springbootdemo.sub.exceptions.SubsidiaryControllerException;
import com.ak.springbootdemo.sub.service.SubsidiaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Subsidiary Controller
 */
@Controller
@RequestMapping("/subsidiaries")
public class SubsidiaryController {
    private static final Logger logger = Logger.getLogger(SubsidiaryController.class.getName());

    private final SubsidiaryService subsidiaryService;

    public SubsidiaryController(SubsidiaryService subsidiaryService) {
        this.subsidiaryService = subsidiaryService;
    }

    /**
     * Get & Bind Subsidiaries List to the model
     *
     * @param sourceType: null - return subsidiariesList from DB;
     *                    types defined in enum SourceType processed in switch;
     *                    undefined types are converted to an UNKNOWN type, and likewise processed in switch.
     * @param model       input model
     * @return response view name
     */
    @GetMapping
    public String getSubsidiaries(@RequestParam(value = "type", required = false) String sourceType, Model model)
            throws SubsidiaryControllerException {
        model.addAttribute("sourceType", sourceType);
        model.addAttribute("subsidiaries", subsidiaryService.getSubsidiaries(sourceType));
        return "subsidiariesView";
    }

    /**
     * Exception handler if SubsidiaryControllerException is thrown in this Controller
     *
     * @param ex exception
     * @return Error message String.
     */
    @ExceptionHandler({SubsidiaryControllerException.class,})
    public String return400(SubsidiaryControllerException ex) {
        logger.log(Level.WARNING, ex.getMessage());
        return ex.getMessage();
    }
}
