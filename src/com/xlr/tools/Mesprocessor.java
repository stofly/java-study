package com.xlr.tools;

import com.xlr.interceptor.Filter;
import com.xlr.interceptor.FilterChain;
import com.xlr.pojo.Request;
import com.xlr.pojo.Response;

/**
 * @ProjectName: Chain_Responsibility
 * @Package: com.xlr.tools
 * @ClassName: Mesprocessor
 * @Author: Mr.S
 * @Description: ${description}
 * @Date: 2019-02-18 11:34
 * @Version: 1.0
 */
public class Mesprocessor {
    private String message;

    private Request request;
    private Response response;

    FilterChain filterChain;

    public FilterChain getFilterChain() {
        return filterChain;
    }

    public void setFilterChain(FilterChain filterChain) {
        this.filterChain = filterChain;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void processor(Request request,Response response,FilterChain filterChain) {
       filterChain.doFilter(request,response,filterChain);
    }
}
