package cn.itcast.filter;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public static final String UserName = "j_username";
    public static final String PassWord = "j_password";


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        请求方式判断
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported:" + request.getMethod());
        }

        String username = this.obtainUsername(request);
        String password = this.obtainPassword(request);

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        username = username.trim();

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        this.setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);

    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        // TODO Auto-generated method stub
        Object obj = request.getParameter(PassWord);
        return null == obj ? "" : obj.toString();
    }

    /**
     * @Description:获取用户名
     * @param :args
     * @return
     * @throws Exception
     */
    @Override
    protected String obtainUsername(HttpServletRequest request) {
        // TODO Auto-generated method stub
        Object obj = request.getParameter(UserName);
        return null == obj ? "" : obj.toString().trim().toLowerCase();
    }

}
