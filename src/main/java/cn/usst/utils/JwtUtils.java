package cn.usst.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.Map;

/**
 * JWT 工具类：生成、解析、验证 JWT 令牌
 */
public class JwtUtils {

    // 签名密钥（实际开发中建议从配置文件读取，而非硬编码）
    private static final String SECRET_KEY = "aXRjYXN0"; // 对应明文 "itcast"

    // 令牌过期时间（单位：毫秒，这里设置为 60 秒，可根据需求调整）
    private static final long EXPIRATION_TIME = 12*60*60*1000;

    private static final ThreadLocal<Long> CURRENT_USER = new ThreadLocal<>();
    /**
     * 生成 JWT 令牌
     * @param claims 自定义载荷（存储业务数据）
     * @return 生成的 JWT 字符串
     */
    public static String generateToken(Map<String, Object> claims) {
        // 计算过期时间（当前时间 + 过期时长）
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);

        return Jwts.builder()
                .setClaims(claims) // 设置自定义载荷（替代 addClaims，更推荐）
                .setExpiration(expirationDate) // 设置过期时间
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 指定签名算法和密钥
                .compact(); // 生成令牌
    }

    /**
     * 解析 JWT 令牌，获取载荷数据
     * 注意：若令牌无效（签名错误、已过期等），会抛出对应的异常
     * @param token JWT 令牌字符串
     * @return 载荷对象（包含自定义数据和过期时间等）
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY) // 使用相同的密钥验证
                .parseClaimsJws(token) // 解析并验证令牌
                .getBody(); // 获取载荷
    }




}
