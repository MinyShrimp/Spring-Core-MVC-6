package hello.springcoremvcsuffix.basic.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {
    /**
     * 변환 타입이 없으면서 이렇게 응답에 값을 직접 집어 넣으면, view 조회 X
     */
    @RequestMapping("/request-param-v1")
    public void requestParamV1(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws IOException {
        String username = req.getParameter("username");
        int age = Integer.parseInt(req.getParameter("age"));
        log.info("username = {}, age = {}", username, age);
        resp.getWriter().write("ok");
    }

    /**
     * @RequestParam 사용
     * - 파라미터 이름으로 바인딩
     * @RequestBody 추가
     * - View 조회를 무시하고, HTTP Message Body에 직접 해당 내용 입력
     */
    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String username,
            @RequestParam("age") int age
    ) {
        log.info("username = {}, age = {}", username, age);
        return "ok";
    }

    /**
     * @RequestParam 사용
     * - String, int 등의 단순 타입이면 @RequestParam 도 생략 가능
     */
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            String username,
            int age
    ) {
        log.info("username = {}, age = {}", username, age);
        return "ok";
    }

    /**
     * @RequestParam.required
     * /request-param-required -> username이 없으므로 예외
     *
     * /request-param-required?username= -> 빈문자로 통과
     *
     * int age -> null을 int에 입력하는 것은 불가능, 따라서 Integer로 변경해야 함.
     */
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username,
            @RequestParam(required = false) Integer age
    ) {
        log.info("username = {}, age = {}", username, age);
        return "ok";
    }

    /**
     * @RequestParam
     * - defaultValue 사용
     *
     * defaultValue는 빈 문자의 경우도 적용된다.
     * - /request-param-default?username=
     *
     * required가 의미가 없어진다.
     */
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true, defaultValue = "guest") String username,
            @RequestParam(required = false, defaultValue = "-1") int age
    ) {
        log.info("username = {}, age = {}", username, age);
        return "ok";
    }

    /**
     * @RequestParam Map, MultiValue
     *
     * /request-param-map?username= -> 빈문자로 통과
     *
     * default -> required = false
     */
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(
            @RequestParam Map<String, Object> paramMap
    ) {
        log.info("username = {}, age = {}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }
}
