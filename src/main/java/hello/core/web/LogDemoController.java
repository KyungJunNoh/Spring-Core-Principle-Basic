package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    private final ObjectProvider<MyLogger> myLoggerObjectProvider; // 스프링 구동 시 의존관계를 주입 받을 수 있다.

    @RequestMapping("log-demo")
    @ResponseBody // String 형태를 화면에 출력
    public String logDemo(HttpServletRequest request) throws InterruptedException { // 자바에서 제공하는 표준 서블릿 정보에서 request 를 가져옴
        String requestUrl = request.getRequestURL().toString();
        MyLogger myLogger = myLoggerObjectProvider.getObject();
        System.out.println("myLogger = " + myLogger.getClass());

        myLogger.setRequestURL(requestUrl);

        myLogger.log("controller test");
        Thread.sleep(2000);
        logDemoService.logic("testId");
        return "OK";
    }

}
