package com.mysite.sbb.user;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysite.sbb.user.fomVal.UserCreateForm;
import com.mysite.sbb.user.fomVal.UserFindPassForm;
import com.mysite.sbb.util.EmailService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

  private final UserService userService;
  private final EmailService emailService;

  @GetMapping("/signup")
  public String signup(UserCreateForm userCreateForm) {
    return "signup_form";
  }

  @PostMapping("/signup")
  public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "signup_form";
    }

    if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
      bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
      return "signup_form";
    }

    try {
      userService.create(userCreateForm.getUsername(),
          userCreateForm.getEmail(), userCreateForm.getPassword1());
    } catch (DataIntegrityViolationException e) {
      e.printStackTrace();
      bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
      return "signup_form";
    } catch (Exception e) {
      e.printStackTrace();
      bindingResult.reject("signupFailed", e.getMessage());
      return "signup_form";
    }

    return "redirect:/";
  }

  @GetMapping("/login")
  public String login() {
    return "login_form";
  }

  @GetMapping("/findpass")
  public String findpass(UserFindPassForm userFindPassForm) {
    return "find_pass";
  }

  @PostMapping("/findpass")
  public String findpass(@Valid UserFindPassForm userFindPassForm, BindingResult bindingResult) {

    // 임시 비밀번호 랜덤 생성기를 만들기
    this.userService.modify_pass(userFindPassForm.getEmail(), "qweqwe123");

    String to = userFindPassForm.getEmail();
    String subject = "[sbb] - 임시 비밀번호 발급되었습니다";
    // String text = "sbb에서 보낸 테스트.";
    // emailService.sendEmail(to, subject, text);

    String text = """
          <h1 >sbb 서비스 입니다</h1>
          <p >임시 비밀번호: qweqwe123</p>
          <a href='http://localhost:8080/question/list'>이동하기</a>
        """;
    try {
      emailService.sendHtmlEmail(to, subject, text);
    } catch (MessagingException e) {
      e.printStackTrace();
    }

    // 완료 되었으면 이메일 발송되었다고 알림창 띄우기

    return "find_pass";
  }


}
