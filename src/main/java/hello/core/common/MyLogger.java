package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS) // 이 빈의 생존 범위는 request 이다. ( 고객으로 부터 요청이 들어왔을 때만 생존 인데 요청이 들어오지 않아 DI 를 할때 에러가 터진다. )
// ( 스프링 애플리케이션을 실행하는 시점에 싱글톤 빈은 생성해서 주입이 가능하지만, request 스코프 빈은 아직 생성되지 않는다. 이 빈은 실제 고객의 요청이 와야 생성할 수 있다! )
// ( 하지만 Provider 를 사용하여 request 가 아니여도 스프링 구동 시점에도 DI가 가능 해졌다! )

// @Scope 의 proxyMode 옵션의 ScopedProxyMode.TARGET_CLASS 는 프록시 클래스를 주입시켜 Provider 처럼 행동한다.
// CGLIB 라는 라이브러리로 내 클래스를 상속 받은 가짜 프록시 객체를 만들어서 주입한다.
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message){
        System.out.println("[" + uuid + "]" + "[" + requestURL + "]" + message);
    }

    @PostConstruct
    public void init(){
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean create : " + this);
    }

    @PreDestroy
    public void close(){
        System.out.println("[" + uuid + "] request scope bean close : " + this);
    }
}
