package hello.springcoremvcsuffix.basic.request;

import hello.springcoremvcsuffix.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class RequestModelParamController {
    /**
     * @ModelAttribute 사용
     *
     * 1. HelloData 객체를 생성한다.
     * 2. 요청 파라미터의 이름으로 HelloData 객체의 프로퍼티를 찾는다. 그리고 해당 프로퍼티의 setter를 호출해서 파라미터의 값을 바인딩 한다.
     * 예) 파라미터 이름이 username이면 setUsername() 메서드를 찾아서 호출하면서 값을 입력한다.
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(
            @ModelAttribute HelloData helloData
    ) {
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    /**
     * @ModelAttribute 생략 가능
     * 하지만 권장하지 않음.
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(
            HelloData helloData
    ) {
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }
}
