package com.gbm.mgb.web.base;

import com.gbm.mgb.core.Result;
import com.gbm.mgb.core.ResultCode;
import com.gbm.mgb.utils.BeanUtils;
import com.google.common.base.Strings;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Waylon on 2017/10/19.
 */
@RestController
public class TokenErrorController extends BasicErrorController {

    public TokenErrorController(){
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }

    private static final String PATH = "/error";

    @RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request,
                isIncludeStackTrace(request, MediaType.ALL));
        HttpStatus status = getStatus(request);
        Result result = new Result();
        if (!Strings.isNullOrEmpty((String)body.get("exception")) && body.get("exception").equals(ExpiredJwtException.class.getName())){
            result.setCode(ResultCode.UNAUTHORIZED).setMessage("签名已过期！");
            status = HttpStatus.FORBIDDEN;
        }
        return new ResponseEntity<Map<String, Object>>(BeanUtils.beanToMap(result),status);
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
