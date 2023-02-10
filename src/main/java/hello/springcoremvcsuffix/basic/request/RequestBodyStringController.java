package hello.springcoremvcsuffix.basic.request;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {
    @PostMapping("/request-body-string-v1")
    public void requestBodyString(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws IOException {
        ServletInputStream inputStream = req.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody = {}", messageBody);

        resp.getWriter().write("echo: " + messageBody);
    }

    /**
     * InputStream( Reader ): HTTP 요청 메시지 바디의 내용을 직접 조회
     * OutputStream( Writer ): Http 응답 메시지 바디에 직접 결과 출력
     */
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(
            InputStream inputStream,
            Writer respWriter
    ) throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody = {}", messageBody);
        respWriter.write("echo: " + messageBody);
    }

    /**
     * HttpEntity: HTTP header, body 정보를 편리하게 조회
     * - 메시지 바디 정보를 직접 조회
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     *
     * 응답에서도 HttpEntity 사용 가능
     * - 메시지 바디 정보 직접 변환(view 조회 X)
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     */
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(
            HttpEntity<String> httpEntity
    ) throws IOException {
        String messageBody = httpEntity.getBody();
        log.info("messageBody = {}", messageBody);
        return new HttpEntity<>("echo: " + messageBody);
    }

    /**
     * @RequestBody
     * - 메시지 바디 정보를 직접 조회
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter
     *
     * @ResponseBody
     * - 메시지 바디 정보 직접 반환
     * - HttpMessageConverter 사용 -> StringHttpMessageConverter
     */
    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(
            @RequestBody String messageBody
    ) throws IOException {
        log.info("messageBody = {}", messageBody);
        return "echo: " + messageBody;
    }
}
