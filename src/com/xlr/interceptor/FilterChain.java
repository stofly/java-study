package com.xlr.interceptor;

import com.xlr.pojo.Request;
import com.xlr.pojo.Response;

import java.util.ArrayList;

/**
 * @ProjectName: Chain_Responsibility
 * @Package: com.xlr.interceptor
 * @ClassName: FilterChain
 * @Author: Mr.S
 * @Description: ${description}
 * @Date: 2019-02-18 14:12
 * @Version: 1.0
 */
//为确保自己可以添加自己继承filter接口，使自己成为一个filter，因为add方法是添加Filter类型的参数
public class FilterChain implements Filter {
    int index =0;
    ArrayList<Filter> filterArrayList = new ArrayList<>();

    //添加filter的方法,将后来写的规则过滤器放在这个大过滤器的数据组中
    public FilterChain add(Filter filter){//为了能使用方法链放回直接是自身
        this.filterArrayList.add(filter);
        return this;
    }

    @Override
    public void doFilter(Request request, Response response, FilterChain filterChain) {
        //index初始化为0,filters.size()为3，不会执行return操作
        if(index==filterArrayList.size()){
            return;
        }
        //每添加一个过滤规则，index自增1
        Filter f=filterArrayList.get(index);
        index++;
        //根据索引值获取对应的规律规则对字符串进行处理
        f.doFilter(request, response, filterChain);

    }


}
