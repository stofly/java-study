此部分是研究责任链中，老师对struts2核心过滤器进行的模仿，不过马士兵老师说他的过滤模式比struts2的模式还要好！！主要要实现的功能如下：

我们有一个字符串String msg = ":):,<script>,敏感,被就业,网络授课";我们希望应用以下三个规则对字符串进行过滤和谐处理：

(1)将字符串中出现的"<>"符号替换成"[]"

(2)处理字符串中的敏感信息，将被就业和谐成就业

(3)将字符串中出现的":):"转换成"^V^";

字符串会依次运用这三条规则，对字符串进行处理，每个规则都有自己需要完成的责任和任务。把信息放入request 中，让三个过滤器依次处理，然后，在放回的过程中，让response在进行过滤处理，返回时倒序进行返回。

​

 废话少说，上代码：

首先我们需要建立request和response两个类

public class Request {
    public String requestStr;

    public String getRequestStr() {
        return requestStr;
    }

    public void setRequestStr(String requestStr) {
        this.requestStr = requestStr;
    }
}
public class Response {
    public String responseStr;

    public String getResponseStr() {
        return responseStr;
    }

    public void setResponseStr(String responseStr) {
        this.responseStr = responseStr;
    }
}
 建立过滤器接口类Filter，中间模仿javaweb中的过滤器，所以限定参数为request和response：

public interface Filter {

    
    /*
     * 定义接口Filter,具体的过滤规则需要实现这个接口，最后一个参数添加的意义是我们在Main函数中:
     * fc.doFilter(request, response,fc);执行这一步的时候可以按照规则链条一次使用三个过滤规则对字符串进行处理
     * 因为
     */
    void doFilter(Request request, Response response, FilterChain filterChain);
}
定义三个实现filter的过滤实现类HTMLfilter、contentFilter、facefilter：
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
public class ContentFilter implements Filter {


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
定义责任链filterchain，此链实现添加规则、添加其他filter类型的责任链：

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
        //获取下一过滤器的下标
        index++;
        //根据索引值获取对应的规律规则对字符串进行处理
        f.doFilter(request, response, filterChain);

    }
接下来编写一个信息和责任链处理的封装类，实际上这个可以不用写，直接用，以为我的前两版本，已经弄好了所以在这里我也用了：

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
接下来就时主类了：这个比较简单，在这里不多做解释：

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
结果：如下

 

 实现了倒序，这里的话，如果有不太明白可以使用debug进行追踪，我也是追了半天稍微有点头绪，中间还牵扯栈的知识



