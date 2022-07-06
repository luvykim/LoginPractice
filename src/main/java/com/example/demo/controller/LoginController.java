package com.example.demo.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.WebUtils;
import com.example.demo.common.annotation.AfterLogging;
import com.example.demo.common.annotation.AfterReturnLogging;
import com.example.demo.common.annotation.BeforeLogging;
import com.example.demo.common.annotation.PerLogging;
import com.example.demo.service.LoginService;
import com.example.demo.vo.LoginRequest;
import com.example.demo.vo.ModifyRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    // private MemberRepository memberRepository;
    @Autowired
    private final LoginService loginService;

    /***
     * 로그인 페이지
     * 
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main(HttpServletRequest request, Model model) {
        log.info("id : {} , pw : {}", request.getAttribute("username"),
                request.getAttribute("password"));
        // 쿠키 만료시 Cookie 값이 null이 된다. (유효 시간 동안은 개발자 모드 진입 후(F12) 쿠키 보면 AUTH 라는 이름으로 세션 ID가 들어가 있음)
        Cookie auth = WebUtils.getCookie(request, "AUTH");

        // 로그인 정보가 있을시
        if (!ObjectUtils.isEmpty(auth)) {
            if (StringUtils.equalsIgnoreCase(auth.getValue(), request.getSession().getId())) {
                String username = (String) request.getSession().getAttribute("username");
                String password = (String) request.getSession().getAttribute("password");
                // Member member = memberRepository.findMember(username, password);
                if (StringUtils.isNotEmpty(username) && username != null) {
                    model.addAttribute("username", username);
                    return "success";
                }
            }
        }
        // 로그인 만료 or 비 로그인자 일시
        return "login";
    }

    /***
     * 로그인 요청
     * 
     * @param loginRequest
     * @param request
     * @param response
     * @param model
     * @return
     */
    // @ModelAttribute는 생략할 수 있다. 그러나 @RequestParam도 생략할 수 있으니 혼란이 발생할 수 있다.
    @PerLogging
    @BeforeLogging
    @AfterLogging
    @AfterReturnLogging
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String login(LoginRequest loginRequest, HttpServletRequest request,
            HttpServletResponse response, Model model) {
        log.info("id : {} , pw : {}", loginRequest.getUsername(), loginRequest.getPassword());
        // DB체크(request객체 따로 만들기)
        if (loginService.login(loginRequest)) {

            // 세션 저장 (세션 ID, 사용자 정보)
            // 세션은 브라우저 당 1개 생성(시크릿 모드도 동일, 같은 브라우저에서 새탭 or 새창 띄워도 로그인 유지) / 쿠키는 시크릿 모드시 없어짐
            request.getSession().setAttribute("username", loginRequest.getUsername());

            // 쿠키 전달 (세션 ID)
            response.addCookie(new Cookie("AUTH", request.getSession().getId()) {
                {
                    setMaxAge(60); // 자동 로그인 10 초 유지
                    setPath("/");
                }
            });

            // 화면에 표시할 ID 셋팅
            model.addAttribute("username", loginRequest.getUsername());
            return "success";

        } else {
            return "error";
        }
    }

    // @RequestMapping(value = "/test", method = RequestMethod.GET)
    // public String test() {
    // log.info("test");
    // return loginService.loginn();
    // }

    /***
     * 로그아웃 요청
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        // 세션 저장소 세션 제거
        request.getSession().invalidate();
        return "redirect:/";
    }

    @RequestMapping(value = "/modify", method = RequestMethod.GET)
    public String modify(ModifyRequest modifyRequest, HttpServletRequest request) {
        return "modify";
    }
}
