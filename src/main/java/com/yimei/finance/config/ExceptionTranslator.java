package com.yimei.finance.config;

import com.yimei.finance.entity.common.result.Result;
import com.yimei.finance.exception.BusinessException;
import com.yimei.finance.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionTranslator {
//    @Autowired
//    private ObjectMapper om;
//    @Autowired
//    private Queue mailQueue;
//    @Value("${mail.to}")
//    private String to;

    private static final Logger logger = LoggerFactory.getLogger(ExceptionTranslator.class);

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Result process404Error(NotFoundException ex) {
        logger.error("404Exception:",ex);
        return Result.error(404, ex.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public Result process409Error(BusinessException ex) {
        logger.error("409Exception:",ex);
        return Result.error(409, ex.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, TypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result process400Error(Exception ex) {
        logger.error("400Exception:", ex);
        return Result.error(400, ex.getMessage());
    }

//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ResponseBody
//    public Result process500Error(HttpServletRequest request, Exception ex) {
//        logger.error("500Exception:",ex);
//        this.sendAlarmQueueMessage(request, ex);
//        return Result.error(500, ex.getMessage());
//
//    }
//
//    //获取header对象
//    private String getHeadersInfo(HttpServletRequest request) throws JsonProcessingException {
//        Map<String, String> map = new HashMap<String, String>();
//        Enumeration headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String key = (String) headerNames.nextElement();
//            String value = request.getHeader(key);
//            map.put(key, value);
//        }
//        map.put("Request Method", request.getMethod());
//        map.put("requestURL", request.getRequestURL().toString());
//        return om.writeValueAsString(map);
//    }
//
//    public static String getStackTrace(final Throwable throwable) {
//        final StringWriter sw = new StringWriter();
//        final PrintWriter pw = new PrintWriter(sw, true);
//        throwable.printStackTrace(pw);
//        return sw.getBuffer().toString();
//    }

//    //获取application/json 数据
//    public static String extractPostRequestBody(HttpServletRequest request) {
//        if ("POST".equalsIgnoreCase(request.getMethod())) {
//            Scanner s = null;
//            try {
//                s = new Scanner(request.getInputStream(), "UTF-8").useDelimiter("\\A");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return s.hasNext() ? s.next() : "";
//        } else {
//            return "{}";
//        }
//    }
//
//    @Async
//    private void sendAlarmQueueMessage(HttpServletRequest request, Exception ex) {
//        MailMessage mailMessage = new MailMessage();
//
//        String[] mail_tos = to.split(";");
//        for (int i = 0; i < mail_tos.length; i++) {
//            mailMessage.setTo(mail_tos[i]);
//            mailMessage.setFrom("auto_server@yimei180.com");
//            mailMessage.setSubject(ZonedDateTime.now().toString() + " " + ex.getClass().getName());
//            StringBuilder sb = new StringBuilder();
//            try {
//                sb.append("requestHeader:\t")
//                        .append(getHeadersInfo(request))
//                        .append("\nrequestUrl:\t")
//                        .append(request.getRequestURL())
//                        .append("\nformData:\t")
//                        .append(om.writeValueAsString(request.getParameterMap()))
//                        .append("\nrequestBody:\t")
//                        .append(om.writeValueAsString(extractPostRequestBody(request)))
//                        .append("\n").append(ex.getMessage())
//                        .append(getStackTrace(ex));
//            } catch (Exception e) {
//                logger.error("mail content build failed!", e);
//            }
//            mailMessage.setBody(sb.toString());
//
//            ObjectMapper mapper = ObjectMapperUtil.getMapper();
//            String mailJson = null;
//            try {
//                mailJson = mapper.writeValueAsString(mailMessage);
//                logger.info("message to send : " + mailJson);
//            } catch (Exception e) {
//                logger.error("can not jsonify mail, error[{}]", e);
//                continue;
//            }
//            mailQueue.sendMessage(mailJson, 0);
//        }
//    }

}
