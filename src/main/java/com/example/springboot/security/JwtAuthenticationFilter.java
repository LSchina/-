package com.example.springboot.security;

import com.example.springboot.common.CheckResult;
import com.example.springboot.common.JwtConstant;
import com.example.springboot.domain.pojo.SUser;
import com.example.springboot.service.UserService;
import com.example.springboot.utils.JwtUtils;
import com.example.springboot.utils.StringUtils;
import com.example.springboot.utils.UserContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 */
@Slf4j
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    private MyUserDetailServiceImpl myUserDetailService;

    @Autowired
    private UserService sysUserService;

    // 请求白名单
    private static final String URL_WHITELIST[]={
            "/captcha",
            "/user/register"
    };

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requestURI = request.getRequestURI();
        String[] substring = requestURI.split("/");
        System.out.println(substring);
        System.out.println(substring[1]);
        if (substring[1].equals("admin")){
            chain.doFilter(request, response);
            return;
        }
        String token = request.getHeader("token");
        log.info("{}",token);
        System.out.println("request.getRequestURI()" + request.getRequestURI());
        if (StringUtils.isEmpty(token) || new ArrayList<String>(Arrays.asList(URL_WHITELIST)).contains(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }
        CheckResult checkResult = JwtUtils.validateJWT(token);
        if (!checkResult.isSuccess()) {
            switch (checkResult.getErrCode()) {
                case JwtConstant.JWT_ERRCODE_NULL:
                    throw new JwtException("Token不存在");
                case JwtConstant.JWT_ERRCODE_FAIL:
                    throw new JwtException("Token验证不通过");
                case JwtConstant.JWT_ERRCODE_EXPIRE:
                    throw new JwtException("Token过期");
            }
        }


        Claims claims = JwtUtils.parseJWT(token);
        String username = claims.getSubject();

        log.info("{}",username);
        SUser sysUser = sysUserService.lambdaQuery().eq(SUser::getUsername,username).one();

        log.info("{}",sysUser);
        UserContext.setUser(sysUser.getId());
        log.info("{}",UserContext.getUser());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, myUserDetailService.getUserAuthority(sysUser.getId()));

        log.info("{}",usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        chain.doFilter(request, response);

    }
}
