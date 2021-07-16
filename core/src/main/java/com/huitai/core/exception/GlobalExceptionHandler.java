package com.huitai.core.exception;

import com.huitai.common.model.Result;
import com.huitai.core.base.BaseException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;
import java.util.StringJoiner;

/**
 * description: GlobalExceptionHandler <br>
 * date: 2020/4/8 15:11 <br>
 * author: PLF <br>
 * version: 1.0 <br>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MessageSource messageSource;

    private final String SEPARATOR = "-";

    /**
     * description: 自定义业务异常 <br>
     * version: 1.0 <br>
     * date: 2020/4/8 15:41 <br>
     * author: PLF <br>
     */
    @ExceptionHandler(BaseException.class)
    public Result handleBindException(BaseException ex) {
        logger.error(ex.toString(), ex);
        return Result.error(ex.getMessage());
    }

    /**
     * description: 参数绑定错误 <br>
     * version: 1.0 <br>
     * date: 2020/4/8 15:41 <br>
     * author: PLF <br>
     */
    @ExceptionHandler(BindException.class)
    public Result handleBindException(BindException ex) {
        StringJoiner sj = new StringJoiner("、", "[", "]");
        ex.getBindingResult().getFieldErrors().forEach(x -> sj.add(x.getDefaultMessage()));
        String message = messageSource.getMessage("system.error.bind", null, LocaleContextHolder.getLocale()) + SEPARATOR + sj.toString();
        logger.error(message, ex);
        return Result.error(message);
    }

    /**
     * description: 参数校验错误 <br>
     * version: 1.0 <br>
     * date: 2020/4/8 15:42 <br>
     * author: PLF <br>
     */
    @ExceptionHandler(ValidationException.class)
    public Result handleValidationException(ValidationException ex) {
        String message = messageSource.getMessage("system.error.validation", null, LocaleContextHolder.getLocale()) + SEPARATOR + ex.toString();
        logger.error(message, ex);
        return Result.error(message);
    }

    /**
     * description: 字段校验不通过异常 <br>
     * version: 1.0 <br>
     * date: 2020/4/8 15:42 <br>
     * author: PLF <br>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        StringJoiner sj = new StringJoiner(";");
        ex.getBindingResult().getFieldErrors().forEach(x -> sj.add(x.getDefaultMessage()));
        String message = messageSource.getMessage("system.error.methodArgumentNotValid", null, LocaleContextHolder.getLocale()) + SEPARATOR + sj.toString();
        logger.error(message, ex);
        return Result.error(message);
    }

    /**
     * description: 控制层参数绑定错误 <br>
     * version: 1.0 <br>
     * date: 2020/4/8 15:42 <br>
     * author: PLF <br>
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        String message = messageSource.getMessage("system.error.missingServletRequestParameter", null, LocaleContextHolder.getLocale()) + SEPARATOR + ex.toString();
        logger.error(message, ex);
        return Result.error(message);
    }

    /**
     * description: 处理方法不支持异常 <br>
     * version: 1.0 <br>
     * date: 2020/4/8 15:42 <br>
     * author: PLF <br>
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        String message = messageSource.getMessage("system.error.httpRequestMethodNotSupported", null, LocaleContextHolder.getLocale()) + SEPARATOR + ex.toString();
        logger.error(message, ex);
        return Result.error(message);
    }

    /**
     * description: 处理不允许访问异常 <br>
     * version: 1.0 <br>
     * date: 2020/4/27 17:31 <br>
     * author: XJM <br>
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Result handleAccessDeniedException(AccessDeniedException ex) {
        String message = messageSource.getMessage("system.error.accessDeniedException", null, LocaleContextHolder.getLocale()) + SEPARATOR + ex.toString();
        logger.error(message, ex);
        return Result.error(message);
    }



    /**
     * description: 其他未知异常 <br>
     * version: 1.0 <br>
     * date: 2020/4/8 15:42 <br>
     * author: PLF <br>
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception ex) {
        String message = messageSource.getMessage("system.error.exception", null, LocaleContextHolder.getLocale()) + SEPARATOR + ex.toString();
        ex.printStackTrace();
        logger.error(message, ex);
        return Result.error(message);
    }
}
