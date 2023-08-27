package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class MainController {

  // @GetMapping("/sbb")
  // @ResponseBody
  // public String index() {
  // return "안녕하세요 sbb에 오신것을 환영합니다.";
  // }

  @GetMapping("/")
  public String root() {
    return "redirect:/question/list";
  }

  // @GetMapping("/error")
  // public String handleError() {
  //   return "error"; // error.html 또는 해당 페이지로 리다이렉트
  // }

}
