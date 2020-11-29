package cn.chiu.haveatry.UtilsTest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;
import io.jsonwebtoken.lang.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT工具类
 */
@Component
public class JwtTokenUtil {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    static final String CLAIM_KEY_USERID = "user_id";
    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_CREATED = "iat";

    private Clock clock = DefaultClock.INSTANCE;

    @Value("${jwt.token.secret}")
    private String secret;  //密钥

    @Value("${jwt.token.expiration}")
    private Long expiration;    //有效期


    /**************************************************自定义部分*******************************************************/

    /**
     * 根据token，获取用户信息
     * @param token
     * @return userDetail
     */
    public JwtUser getUserFromToken(String token) {
        JwtUser userDetails;
        try {
            final Claims claims = getAllClaimsFromToken(token);
            String userId = claims.get(CLAIM_KEY_USERID).toString();
            String username = getUsernameFromToken(token);
            userDetails = new JwtUser(userId,username,null,null,null);
        } catch (Exception e) {
            userDetails = null;
        }
        return userDetails;
    }

    /**
     * 获取用户ID
     * @param token
     * @return userId
     */
    public String getUserIdFromToken(String token) {
        String userId;
        try {
            final Claims claims = getAllClaimsFromToken(token);
            userId = String.valueOf(claims.get(CLAIM_KEY_USERID));
        } catch (Exception e) {
            userId = "";
        }
        return userId;
    }

    /**
     * 生成令牌
     * @param userDetails
     * @return
     */
    public String generateAccessToken(UserDetails userDetails) {
        Assert.notNull(userDetails, "UserDetails cannot be null.");

        JwtUser jwtUser = (JwtUser) userDetails;
        Map<String, Object> claims = new HashMap<>(16);
        claims.put(CLAIM_KEY_USERID,jwtUser.getUserId());
        return doGenerateToken(claims, jwtUser.getUsername());
    }

    /**
     * 验证token 是否合法
     * @param token
     * @return
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        Assert.notNull(userDetails, "UserDetails cannot be null.");

        JwtUser user = (JwtUser) userDetails;
        final String userId = getUserIdFromToken(token);
        //final String username = getUsernameFromToken(token);
        final Date created = getIssuedAtDateFromToken(token);
        //final Date expiration = getExpirationDateFromToken(token);
//        return (
//                userId.equals(user.getUserId())
//                        && !isTokenExpired(token)
//                        && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate())
//        );
        return false;
    }

    /******************************************************************************************************************/


    /**
     * 获取用户名
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * 获取签发日期
     * @param token
     * @return
     */
    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    /**
     * 获取过期时间
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * 获取token某一信息
     * @param token
     * @param claimsResolver
     * @param <T>
     * @return
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 解析token信息
     * @param token
     * @return
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 判断token是否过期
     * @param token
     * @return
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(clock.now());
    }

    /**
     * 生成时间是否在最后修改时间之前
     * @param created   生成时间
     * @param lastPasswordReset 最后生成时间
     * @return
     */
    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private Boolean ignoreTokenExpiration(String token) {
        // here you specify tokens, for that the expiration is ignored
        return false;
    }

    /**
     * 生成token
     * @param claims
     * @param subject
     * @return
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 判断token是否可刷新
     * @param token
     * @param lastPasswordReset
     * @return
     */
    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getIssuedAtDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    /**
     * 刷新token
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        final Claims claims = getAllClaimsFromToken(token);
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 计算过期时间
     * @param createdDate
     * @return
     */
    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + expiration * 1000);
    }
}
