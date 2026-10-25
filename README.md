项目中的不懂点：
1.ThreadLocal 多线程

2.AOP切面的实现

3.Redis 缓存穿透 缓存雪崩 缓存击穿 

4.微信支付功能实现

5.配送地址是否超出范围判断功能实现

6.微信小程序登录流程

7.JWT令牌的生成及使用

8.拦截器的使用


苍穹外卖当中用到的技术：SpringBoot,ssm,nginx,Swagger,knife4j,全局异常处理器,ThreadLocal，pageHelper,AOP,Redis,HttpClient,SpringCache,Spring Task,WebSocket,Apache ECharts,Apache POI


知识点总结：

1.nginx反向代理介绍 :

-将前端发送的动态请求由nginx转发到后端服务器

--nginx反向代理的好处：

---提高访问速度

因为nginx本身可以进行缓存，如果访问的同一接口，并且做了数据缓存，nginx就直接可把数据返回，不需要真正地访问服务端，从而提高访问速度。


---进行负载均衡

所谓负载均衡,就是把大量的请求按照我们指定的方式均衡的分配给集群中的每台服务器。


---保证后端服务安全

因为一般后台服务地址不会暴露，所以使用浏览器不能直接访问，可以把nginx作为请求访问的入口，请求到达nginx后转发到具体的服务中，从而保证后端服务的安全。


2.Swagger knife4j

-Swagger是一种基于OpenAPI规范的工具，用于设计、生成和文档化RESTful API


--knife4j

@Api	用在类上，例如Controller，表示对类的说明

@ApiModel	用在类上，例如entity、DTO、VO

@ApiModelProperty	用在属性上，描述属性信息

@ApiOperation	用在方法上，例如Controller的方法，说明方法的用途、作用




3. 全局异常处理器

-全局异常处理器是一种用于集中处理应用程序中所有异常的机制。

--在Spring框架中，可以通过@ExceptionHandler注解来定义一个全局异常处理器方法。当程序运行时抛出异常时，Spring会自动调用该方法来处理异常，而不是直接抛出到客户端。

4.ThreadLocal
-ThreadLocal 并不是一个Thread，而是Thread的局部变量。
--ThreadLocal为每个线程提供单独一份存储空间，具有线程隔离的效果，只有在线程内才能获取到对应的值，线程外则不能访问。

---public void set(T value)	设置当前线程的线程局部变量的值
---public T get()	返回当前线程所对应的线程局部变量的值
---public void remove()	移除当前线程的线程局部变量

5.PageHelper
-PageHelper 的核心原理是通过 MyBatis 的拦截器机制实现分页。

--它在执行 SQL 查询之前，通过 ThreadLocal 保存分页参数，并在拦截器中对 SQL 语句进行分页处理（例如添加 LIMIT 子句）。查询结束后，分页信息会封装在 PageInfo 对象中，便于获取总记录数、总页数等信息。

6.AOP
-AOP是一种编程范式，旨在通过分离横切关注点（如日志记录、事务管理、安全检查等）来提高代码的模块化和可维护性。它将这些横切逻辑从业务逻辑中分离出来，集中管理，从而减少代码冗余和提高代码清晰度。
--作用：在程序运行期间在不修改源代码的基础上对已有方法进行增强（无侵入性: 解耦）

7.Redis
-Redis是一种键值型的NoSql数据库，这里有两个关键字：
--键值型
--NoSql
---其中键值型，是指Redis中存储的数据都是以key、value对的形式存储，而value的形式多种多样，可以是字符串、数值、甚至json：

----Redis通用命令
通用指令是部分数据类型的，都可以使用的指令，常见的有：

-KEYS：查看符合模板的所有key

-DEL：删除一个指定的key

-EXISTS：判断key是否存在

-EXPIRE：给一个key设置有效期，有效期到期时该key会被自动删除

-TTL：查看一个KEY的剩余有效期

----Redis 中字符串类型常用命令：

-SET key value 设置指定key的值

-GET key 获取指定key的值

-SETEX key seconds value 设置指定key的值，并将 key 的过期时间设为 seconds 秒

-SETNX key value 只有在 key 不存在时设置 key 的值

----Redis hash 是一个string类型的 field 和 value 的映射表，hash特别适合用于存储对象，常用命令：

-HSET key field value 将哈希表 key 中的字段 field 的值设为 value

-HGET key field 获取存储在哈希表中指定字段的值

-HDEL key field 删除存储在哈希表中的指定字段

-HKEYS key 获取哈希表中所有字段

-HVALS key 获取哈希表中所有值

----Redis中的List类型与Java中的LinkedList类似，可以看做是一个双向链表结构。既可以支持正向检索和也可以支持反向检索。

特征：

1.有序

2.元素可以重复

3.插入和删除快

4.查询速度一般

----Redis 列表是简单的字符串列表，按照插入顺序排序，常用命令：

-LPUSH key value1 [value2] 将一个或多个值插入到列表头部

-LRANGE key start stop 获取列表指定范围内的元素

-RPOP key 移除并获取列表最后一个元素(R:right)

-LPOP key 移除并获取列表第一个元素(L:left)

-LLEN key 获取列表长度

-BRPOP key1 [key2 ] timeout 移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超 时或发现可弹出元素为止

----Redis set 是string类型的无序集合。集合成员是唯一的，这就意味着集合中不能出现重复的数据，常用命令：

SADD key member1 [member2] 向集合添加一个或多个成员

SMEMBERS key 返回集合中的所有成员

SCARD key 获取集合的成员数

SINTER key1 [key2] 返回给定所有集合的交集

SUNION key1 [key2] 返回所有给定集合的并集

SREM key member1 [member2] 移除集合中一个或多个成员

----Redis的SortedSet是一个可排序的set集合，与Java中的TreeSet有些类似，但底层数据结构却差别很大。SortedSet中的每一个元素都带有一个score属性，可以基于score属性对元素排序，底层的实现是一个跳表（SkipList）加 hash表。

SortedSet具备下列特性：

1.可排序

2.元素不重复

3.查询速度快

因为SortedSet的可排序特性，经常被用来实现排行榜这样的功能。

----常用命令：

-ZADD key score1 member1 [score2 member2] 向有序集合添加一个或多个成员

-ZRANGE key start stop [WITHSCORES] 通过索引区间返回有序集合中指定区间内的成员

-ZINCRBY key increment member 有序集合中对指定成员的分数加上增量 increment

-ZREM key member [member ...] 移除有序集合中的一个或多个成员

8.HttpClient
-HttpClient 是Apache Jakarta Common 下的子项目，可以用来提供高效的、最新的、功能丰富的支持 HTTP 协议的客户端编程工具包，并且它支持 HTTP 协议最新的版本和建议。

--HttpClient作用：

1.发送HTTP请求

2.接收响应数据

---HttpClient应用场景：

当我们在使用扫描支付、查看地图、获取验证码、查看天气等功能时

9.微信小程序登录流程
-小程序端，调用wx.login()获取code，就是授权码。
-小程序端，调用wx.request()发送请求并携带code，请求开发者服务器。
-开发者服务端，通过HttpClient向微信接口服务发送请求，并携带对应参数。
-开发者服务端，接收微信接口服务返回的数据。opendId是微信用户的唯一标识。
-开发者服务端，自定义登录态，生成令牌(token)和openid等数据返回给小程序端。
-小程序端，收到自定义登录态，存储storage。
-小程序端，后绪通过wx.request()发起业务请求时，携带token。
-开发者服务端，收到请求后，通过携带的token，解析当前登录用户的id。
-开发者服务端，身份校验通过后，继续相关的业务逻辑处理，最终返回业务数据

10. SpringCache
-Spring Cache 是一个框架，实现了基于注解的缓存功能，只需要简单地加一个注解，就能实现缓存功能。
--在SpringCache中提供了很多缓存操作的注解，常见的是以下的几个：
-@EnableCaching	开启缓存注解功能，通常加在启动类上
-@Cacheable	在方法执行前先查询缓存中是否有数据，如果有数据，则直接返回缓存数据；如果没有缓存数据，调用方法并将方法返回值放到缓存中
-@CachePut	将方法的返回值放到缓存中
-@CacheEvict	将一条或多条数据从缓存中删除

---在spring boot项目中，使用缓存技术只需在项目中导入相关缓存技术的依赖包，并在启动类上使用@EnableCaching开启缓存支持即可。

例如，使用Redis作为缓存技术，只需要导入Spring data Redis的maven坐标即可。

11.Spring Task
-Spring Task 是Spring框架提供的任务调度工具，可以按照约定的时间自动执行某个代码逻辑。

1.定位：定时任务框架

2.作用：定时自动执行某段Java代码

--cron表达式其实就是一个字符串，通过cron表达式可以定义任务触发的时间
----构成规则：分为6或7个域，由空格分隔开，每个域代表一个含义
每个域的含义分别为：秒、分钟、小时、日、月、周、年(可选)
通配符：
符号	含义
*	   表示所有值
?	   表示未说明的值，即不关心它为何值
-	   表示一个指定的范围
,	   表示附加一个可能值
/	   符号前表示开始时间，符号后表示每次递增的值

12.WebSocket
-WebSocket 是基于 TCP 的一种新的网络协议。它实现了浏览器与服务器全双工通信——浏览器和服务器只需要完成一次握手，两者之间就可以创建持久性的连接， 并进行双向数据传输。
--HTTP协议和WebSocket协议对比：
1.HTTP是短连接

2.WebSocket是长连接

3.HTTP通信是单向的，基于请求响应模式

4.WebSocket支持双向通信

5.HTTP和WebSocket底层都是TCP连接

---WebSocket缺点：
1.服务器长期维护长连接需要一定的成本
2.各个浏览器支持程度不一
3.WebSocket 是长连接，受网络限制比较大，需要处理好重连
----结论：WebSocket并不能完全取代HTTP，它只适合在特定的场景下使用，WebSocket应用场景：视频弹幕等

13. Apache ECharts
-Apache ECharts 是一款基于 Javascript 的数据可视化图表库，提供直观，生动，可交互，可个性化定制的数据可视化图表。 官网地址：Apache ECharts

14.Apache POI
-Apache POI 是一个处理Miscrosoft Office各种文件格式的开源项目。简单来说就是，我们可以使用 POI 在 Java 程序中对Miscrosoft Office各种文件进行读写操作。 一般情况下，POI 都是用于操作 Excel 文件。

15.微信支付功能
1.用户发起请求：用户在商户应用（App / 小程序等）点击支付，向商户服务器发起支付请求。

2.商户生成订单：验证请求合法性，生成含唯一订单号的业务订单，标记 “待支付” 并存库。

3. 调用统一下单 API：构造参数（商户号mch_id、订单号、金额、回调地址notify_url等），用 API 密钥签名后，请求微信统一下单接口。

4. 获取预支付 ID：微信验证通过后，返回含prepay_id（预支付标识）的响应，商户提取prepay_id。

5. 返回前端支付参数：商户用prepay_id构造前端参数（appId、时间戳timeStamp、随机串nonceStr、package=Sign=WXPay、签名paySign等），二次签名后返回前端。

6. 调起微信支付：前端调用微信支付接口（如wx.requestPayment），传入参数拉起支付界面，用户输密码完成支付。

7. 微信处理支付：微信验证密码 / 余额，扣款后向用户展示支付结果（成功 / 失败）。

8. 异步通知商户：微信通过notify_url向商户服务器 POST 支付结果，商户验签、核对订单金额，更新订单为 “已支付”，并返回成功 XML 告知微信。

9. 同步结果至前端：若前端未实时感知，商户通过轮询 / WebSocket 将支付结果推送给前端，更新界面。
————————————————
版权声明：本文为CSDN博主「Purse Wind」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/chan12345678910/article/details/148069962
























