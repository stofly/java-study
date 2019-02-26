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
public class FaceFilter implements Filter {

    @Override
    public void doFilter(Request request, Response response, FilterChain filterChain) {

        //将字符串中出现的":):"转换成"^V^";
        request.requestStr = request.requestStr.replace(":):", "^V^")
                //后面添加的是便于我们观察代码执行步骤的字符串
                + "----FaceFilter()";
        filterChain.doFilter(request, response, filterChain);
        response.responseStr += "---FaceFilter()";
    }
}
