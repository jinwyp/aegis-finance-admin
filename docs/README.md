# 文档


## 后端路由

### 页面路由

管理后台网站的页面,由于使用Angular2, 页面路由只有两个

/ 首页跳转到 /admin/login

/admin/login 登录页面

/admin/home  登录后管理员首页, 需要java端验证是否登录, 没有登录跳转到 登录页面


### API 路由

使用RESTfull 风格 [RESTfull参考文章](http://mherman.org/blog/2016/03/13/designing-a-restful-api-with-node-and-postgres)

POST /api/admin/login
GET /api/admin/logout


### 接口返回格式

#### 接口成功返回 

成功返回数组,包括meta字段表示分页相关信息

```
{
    success : true,
    error : null,
    meta : {
        total : 100,
        count : 10,
        offset : 0,
        page : 1
    },
    data : [
        {
            id : 11,
            orderNo : 1001
        },
        {
            id : 12,
            orderNo : 1002
        },
        {
            id : 13,
            orderNo : 1003
        },
        {
            id : 14,
            orderNo : 1004
        },
        {
            id : 15,
            orderNo : 1005
        },
        {
            id : 16,
            orderNo : 1006
        },
        {
            id : 17,
            orderNo : 1007
        },
        {
            id : 18,
            orderNo : 1008
        },
        {
            id : 19,
            orderNo : 1009
        },
        {
            id : 20,
            orderNo : 1010
        }
    ]
}
```


成功返回对象,不包括meta字段

```
{
    "success" : true,
    "error" : null,
    "meta" : null,
    "data" : {
        "id" : 11,
        "orderNo" : 1001
    }
}
```

失败返回,包括error字段, 不包括meta字段

```
{
    success : false,
    error : {
        code:1001,
        message: 'Field validation error,  order id length must be 4-30',
        field: 'orderId'
    },
    meta : null,
    data : null
}
```

注意如果是删除资源,删除成功 也要返回该资源

```
{
    success : true,
    error : null,
    meta : null,
    data : {
        id : 11,
        orderNo : 1001
    }
}
```



### 使用Swagger 文档工具



