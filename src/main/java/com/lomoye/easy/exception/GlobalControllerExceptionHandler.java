package com.lomoye.easy.exception;

import com.google.common.collect.Lists;
import com.lomoye.easy.model.common.Result;
import com.lomoye.easy.model.common.ResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tommy on 09/02/2018.
 */
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler(value = {Exception.class})
    public Object processException(HttpServletRequest req, Exception ex) throws Exception {
        // If the exception is annotated with @ResponseStatus rethrow it and let
        // the framework handle it - like the OrderNotFoundException example
        // at the start of this post.
        // AnnotationUtils is a Spring Framework utility class.
        if (AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class) != null) {
            throw ex;
        }

        if (ex instanceof BusinessException) {
            BusinessException se = (BusinessException) ex;
            LOGGER.info("encounter business exception|code={}|message={}|url={}", se.getErrorCode(), se.getErrorMessage(), req.getRequestURL());
            return new Result(se.getErrorCode(), se.getErrorMessage());
        } else if (ex instanceof HttpMediaTypeNotSupportedException) {
            LOGGER.warn("encounter HttpMediaTypeNotSupportedException  exception|url={}", req.getRequestURL(), ex);
            return new Result(ErrorCode.PARAMETER_ILLEGAL, "接口操作类型不正确");
        } else {
            LOGGER.warn("encounter unknown exception|url={}", req.getRequestURL(), ex);

            if (ex instanceof BindException) {
                return new ResultData<>(ErrorCode.PARAMETER_ILLEGAL, "绑定数据异常", ((BindException) ex).getBindingResult());
            }

            if (ex instanceof HttpMessageNotReadableException) {
                return new ResultData<>(ErrorCode.PARAMETER_ILLEGAL, "读取绑定数据异常", ex.getMessage());
            }

            if (ex instanceof IllegalArgumentException) {
                return new ResultData<>(ErrorCode.PARAMETER_ILLEGAL, "参数验证异常", ex.getMessage());
            }

            return new ResultData<>(ErrorCode.PARAMETER_ILLEGAL, "系统异常", ex.getClass().getName() + Lists.newArrayList(ex.getStackTrace()).toString());
        }
    }
}
