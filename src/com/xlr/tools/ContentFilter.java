package com.xlr.tools;

import com.xlr.interceptor.Filter;
import com.xlr.interceptor.FilterChain;
import com.xlr.pojo.Request;
import com.xlr.pojo.Response;

/**
 * @ProjectName: Chain_Responsibility
 * @Package: com.xlr.tools
 * @ClassName: ContentFilter
 * @Author: Mr.S
 * @Description: ${description}
 * @Date: 2019-02-18 11:53
 * @Version: 1.0
 */
public class ContentFilter implements Filter {
//    @Override
//    public String dofilter(String info) {
//        String result =null;
//        //处理敏感字符
//        result=info.replace("黄","*");
//        return result;
//    }

    public void doFilter(Request request, Response response,FilterChain chain) {
        //处理字符串中的敏感信息，将被就业和谐成就业
        request.requestStr=request.requestStr
                .replace("被就业", "就业").replace("敏感", "")+
                //后面添加的是便于我们观察代码执行步骤的字符串
                " ---sensitiveFilter()";
        chain.doFilter(request, response,chain);
        response.responseStr+="---sensitiveFilter()";
    }
}
