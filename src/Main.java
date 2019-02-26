import com.xlr.interceptor.Filter;
import com.xlr.interceptor.FilterChain;
import com.xlr.pojo.Request;
import com.xlr.pojo.Response;
import com.xlr.tools.ContentFilter;
import com.xlr.tools.FaceFilter;
import com.xlr.tools.HtmlFilter;
import com.xlr.tools.Mesprocessor;

/*
*
*@Description：责任链
*@Author Mr.S
*@Date 2019-02-18
*@Time 11:27
*/
public class Main {
    public static void main(String[] args) {
        String msg =":):,<script>,敏感,被就业,网络授课";

        Request request = new Request();
        request.setRequestStr(msg);

        Response response = new Response();
        response.setResponseStr("response");

        Mesprocessor mesprocessor = new Mesprocessor();
        mesprocessor.setMessage(msg);
        FilterChain filterChain = new FilterChain();
        filterChain.add(new HtmlFilter())
                   .add(new ContentFilter())//前面返回this,在此提供了方便
                   .add(new FaceFilter());
        mesprocessor.processor(request,response,filterChain);
        System.out.println("测试"+request.getRequestStr());
        System.out.println("测试"+response.getResponseStr());
    }
}
