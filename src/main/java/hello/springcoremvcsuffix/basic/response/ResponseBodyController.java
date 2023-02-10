package hello.springcoremvcsuffix.basic.response;

import hello.springcoremvcsuffix.basic.HelloData;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@Slf4j
@Controller
//@RestController
public class ResponseBodyController {
    /**
     * 서블릿을 직접 다룰 때 처럼
     * HttpServletResponse 객체를 통해서 HTTP 메시지 바디에 직접 ok 응답 메시지를 전달한다.
     * resp.getWriter().write("ok");
     */
    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(
            HttpServletResponse resp
    ) throws IOException {
        resp.getWriter().write("ok");
    }

    /**
     * ResponseEntity는 HttpEntity를 상속 받았는데,
     * HttpEntity는 HTTP 메시지의 헤더, 바디 정보를 가지고 있다.
     * ResponseEntity는 여기에 더해 HTTP 응답 코드를 설정할 수 있다.
     */
    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() throws IOException {
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    /**
     * @ResponseBody 를 사용하면 view를 사용하지 않고,
     * HTTP 메시지 컨버터를 통해서 HTTP 메시지를 직접 입력할 수 있다.
     * ResponseEntity도 동일한 방식으로 동작한다.
     */
    @ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3() throws IOException {
        return "ok";
    }

    /**
     * ResponseEntity를 반환한다.
     * HTTP 메시지 컨버터를 통해서 JSON 형식으로 변환되어 반환된다.
     */
    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1() {
        HelloData data = new HelloData();
        data.setUsername("hello");
        data.setAge(20);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    /**
     * ResponseEntity는 HTTP 응답 코드를 설정할 수 있는데, @ResponseBody를 사용하면 이런 것을 설정하기 까다롭다.
     * @ReponseStatus(HttpStatus.OK) 애노테이션을 사용하면 응답 코드도 설정할 수 있다.
     *
     * 물론 애노테이션이기 떄문에 응답 코드를 동적으로 변경할 수는 없다.
     * 프로그램 조건에 따라서 동적으로 변경하려면 ResponseEntity를 사용하면 된다.
     */
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/response-body-json-v2")
    public HelloData responseBodyJsonV2() {
        HelloData data = new HelloData();
        data.setUsername("hello");
        data.setAge(20);
        return data;
    }
}
