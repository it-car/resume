## note

### 前端弹出框（编辑&新增）细节

1. 使用jQuery，直接clone 一个全新的from表单，此方法不推荐，性能较差 02-19视频。
2. 使用原生的form对象reset重置一下表单。推荐做法02-20视频。

![image-20220715155041878](http://img.minic.xyz//myPhotos/202207151550045.png)



## java对象 -> JSON字符串

需要做的功能点：做 编辑 功能。02-20视频

发现问题：点击 编辑 显示对应的数据出来到form表单上。那就涉及到了数据转换的问题了，前端发生点击事件后，需要传进一个js对象参数进去才可以在方法中得以填充相关的数据。而我们在jsp页面中利用${education}这样传过去的是java对象toString得到的东西，实际就是education.toString()。

解决方法：所以我们可以在bean中重写toString下手做文章解决，但这样只是杯水车薪，也很麻烦。所以使用第三方插件，讲java对象 -> JSON对象。Jackson(目前受欢迎程度最高，调用你bean中所有的getxxx方法。哪个需要就调用注解@JsonIgnore)，FastJson(阿里巴巴出的)。

![image-20220715162955764](http://img.minic.xyz//myPhotos/202207151629820.png)

![image-20220715163121716](http://img.minic.xyz//myPhotos/202207151631830.png)

![image-20220715163413314](http://img.minic.xyz//myPhotos/202207151634394.png)

![image-20220715163724253](http://img.minic.xyz//myPhotos/202207151637338.png)

![image-20220715164151450](http://img.minic.xyz//myPhotos/202207151641532.png)



## 页面的可见性

WEB-INF里的页面 .jsp .html  不可直接访问，只能通过servlet转发到

![image-20220715190244758](http://img.minic.xyz//myPhotos/202207151902828.png)



## 分层

servlet层：控制层，不去执行业务，只负责接收请求，把请求参数拿出来，拿到参数可能要组装一下东西，组装完毕之后就传到业务层，真正去执行业务，譬如说登录业务。后面再业务层返回成功or失败，来去决定去转发还是重定向。来决定给用户返回什么提醒。

![image-20220715192031972](http://img.minic.xyz//myPhotos/202207151920039.png)

service层：业务层，它完成这个业务。譬如说登录这个业务，它牵扯到两个数据库操作，一个是查用户，一个是改用户。那这两个分别对应dao的两个方法，总的来说就是一个业务牵扯到dao的多种操作，业务层就将其组合在一起。

![image-20220715192044733](http://img.minic.xyz//myPhotos/202207151920802.png)

dao层：数据访问层，编写sql语句查询数据。

![image-20220715192118987](http://img.minic.xyz//myPhotos/202207151921032.png)



## 面向接口编程

依赖抽象 而不依赖实现 适配器模式

方便适应各种方案的切换。

左边写接口，右边是实现类。

![image-20220715195504603](http://img.minic.xyz//myPhotos/202207151955681.png)

![image-20220715195538748](http://img.minic.xyz//myPhotos/202207151955820.png)



## 图片上传









## Cookie & Session

同一个请求 之间 的数据共享 ： requst

同一个浏览器  发送多个请求 之间的数据共享



## 踩坑

  1. Jsp的 ${} 不起作用

    在页面添加```<%@page isELIgnored="false" %>```
  这句配置的意思就是：当isELIgnored设置成true时，该jsp就会教EL表达式置为无效，不会将EL表达式的效果展现在页面上，反之当isELIgnored设置成false时，在代码没有错误的前提下，jsp就能将EL表达式的对应值展现在页面上。
    一般情况下jsp页面上直接使用El表达式是没有问题的，但如果出现EL表达式无效，可以试