## 实现思路
用户第一次登录通过微信OAuth2进行用户授权，授权成功后第三方服务获取授权码去通过企业微信API获取用户信息。
服务端根据用户信息签发token凭证，并将其写入cookie中返回客户端。
以后客户端对服务端的请求需自动在cookie中带上该token，服务端通过对token对的校验来确认用户身份。
	
## 微信授权登录
前端：
    1. 应用加载时检验授权状态(ensureOauth:是否拥有授权码CODE)，若没有，通过OAuth2链接从企业微信后台获取授权码CODE。
后台：
    1. 拿到授权码CODE后，通过企业微信API获取用户ID和手机设备号DeviceId
    API：getAcessToken --> access_token, accress_token + CODE --> userId + DeviceId

- 注意：  
    1.授权码只能消费一次；  
    2.通过授权链接获取授权码过程要防止再次授权；
	

## 利用JWT和Spring Security进行安全认证
#### Spring Security配置 —— 重写 WebSecurityConfigurerAdapter
   打开JWT相关配置    
   进行用户身份的认证：UserDetailService  

#### JWT配置
   构建用户身份认证类JwtToken implements UserDetails {} ： userId, userName, deviceId，
   (账户锁定，密码，密码更新日期等)  
   JwtTokenUtil工具类：生成Token、验证Token、刷新Token等  
   Token过滤器，用于每次对外部接口请求时的Token处理  
    

#### token签发：
   通过微信授权登录获取userId后，根据userId获取userInfo。将生成的用户认证对象写入上下文和用于JwtToken签发。最后将token写入cookie返回给客户端。  

#### token校验：
   拦截外部接口请求，解析出cookie中的token然后对token进行解密和签名认证，拿到其中的用户信息并判断有效性(userId/userName,过期时间等)。  
   校验成功，则执行请求并返回客户端请求的资源；  
   校验失败，返回错误码，客户端判断错误码重新进行用户授权。  
    
## token过期处理方案
   token设置两个字段，分别代表token的刷新时间和有效期：  
        refreshTokenExpiration ： 时间过期进行token刷新  
        accessTokenExpiration : 时间过期必须重新授权  
        
   自token签发后，超过刷新时间就更新一次token, 同时， 刷新时间必须小于token有效时间，一旦超过令牌有效期，用户需要重新进行授权。
    
   前端封装登录授权函数、接口请求函数、token刷新函数，在请求成功之后对结果进行校验，如果token过期，则调用token刷新函数更新token；
   请求失败说明token失效，则调用登录授权函数重新获取token。  
    
   前端发起请求，后端验证token是否过期且在有效期内；如果过期，前端发起刷新token请求，请求成功。  
   前端发起请求，后端验证token超过有效期，拒绝刷新token, 请求失败。  
    
- 多请求应对：
       为了避免token重复刷新，需要设置一个刷新token的开关isRefresh,当一个请求出现token过期的时候，这时候会调用token刷新函数，于此同时关闭开关(isRefresh=false),避免后续请求去调用token刷新函数。  
       发现token过期时，使用promise函数将请求延缓到token刷新之后再重新执行请求。
        
    - question：后台要怎么处理这种情况？
