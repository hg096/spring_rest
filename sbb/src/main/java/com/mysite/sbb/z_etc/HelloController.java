package com.mysite.sbb.z_etc;

import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
  // @GetMapping("/hello")
  @ResponseBody
  public String hello() {
    return "안녕!";
  }
}
