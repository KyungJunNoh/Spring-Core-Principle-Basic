package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    private final MyLogger myLogger;

    @RequestMapping("log-demo")
    @ResponseBody // String 형태를 화면에 출력
    public String logDemo(HttpServletRequest request) { // 자바에서 제공하는 표준 서블릿 정보에서 request 를 가져옴
        String requestUrl = request.getRequestURL().toString();
        myLogger.setRequestURL(requestUrl);
        myLogger.log("controller test");

        logDemoService.logic("testId");
        return "OK";
    }

}
