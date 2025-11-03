package SDD.smash.Security.Filter;

import SDD.smash.Security.Service.ApiRateLimitService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class ApiRateLimitFilter extends OncePerRequestFilter {

    private final ApiRateLimitService apiRateLimitService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        // 프리플라이트의 경우 통과
        if("OPTIONS".equalsIgnoreCase(request.getMethod()))
        {
            filterChain.doFilter(request, response);
            return;
        }

        String ip = request.getRemoteAddr();

        // 락 여부 확인
        long ipTtl = apiRateLimitService.checkIpLockAndINCAndMaybeLock(ip);
        if(ipTtl > 0)
        {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setHeader("Retry-After", String.valueOf(ipTtl)); // 초 단위
            return;
        }
        filterChain.doFilter(request, response);
    }
}
