package com.xlr.tools;

import com.xlr.interceptor.Filter;
import com.xlr.interceptor.FilterChain;
import com.xlr.pojo.Request;
import com.xlr.pojo.Response;

/**
 * @ProjectName: Chain_Responsibility
 * @Package: com.xlr.tools
 * @ClassName: HtmlFilter
 * @Author: Mr.S
 * @Description: ${description}
 * @Date: 2019-02-18 11:53
 * @Version: 1.0
 */
public class HtmlFilter implements Filter {
    @Override
    public void doFilter(Request request, Response response, FilterChain filterChain) {
        request.requestStr = request.requestStr.replace('<', '[').replace('>', ']')+
                //后面添加的是便于我们观察代码执行步骤的字符串
                "----HTMLFilter()";
        filterChain.doFilter(request,response,filterChain);
        response.responseStr +="---HTMLFilter()";
    }
}
