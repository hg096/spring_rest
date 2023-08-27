package com.mysite.sbb;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class ErrorControllers implements ErrorController {

  private final ErrorAttributes errorAttributes;

  @Autowired
  public ErrorControllers(ErrorAttributes errorAttributes) {
    this.errorAttributes = errorAttributes;
  }

  @RequestMapping("/error")
  public String handleError(WebRequest webRequest, Model model) {

    // return "redirect:/";

    Map<String, Object> errorAttributesMap = errorAttributes.getErrorAttributes(webRequest,
        ErrorAttributeOptions.defaults());

    int status = (int) errorAttributesMap.get("status");
    String error = (String) errorAttributesMap.get("error");
    String message = (String) errorAttributesMap.get("message");
    String message2 = (String) errorAttributesMap.get(message);

    // Add error attributes to the model
    model.addAttribute("status", status);
    model.addAttribute("error", error);
    model.addAttribute("message", message);
    model.addAttribute("message2", message2);

    return "error"; // error.html 또는 해당 페이지로 리다이렉트
  }

  // @Override
  // public String getErrorPath() {
  // return "/error";
  // }

}