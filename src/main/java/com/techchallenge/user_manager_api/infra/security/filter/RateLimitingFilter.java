package com.techchallenge.user_manager_api.infra.security.filter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Order(1) // Executa antes do filtro de segurança
public class RateLimitingFilter extends OncePerRequestFilter {

    private final Map<String, Bucket> bucketsByIp = new ConcurrentHashMap<>();
    private final Map<String, Bucket> bucketsByUser = new ConcurrentHashMap<>();

    // Rotas que não terão rate limiting (exemplo)
//    private static final String[] EXCLUDED_PATHS = {"/usuarios/login"};
    private static final String[] EXCLUDED_PATHS = {};

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // Ignora rotas excluídas do rate limiting
        for (String exclude : EXCLUDED_PATHS) {
            if (path.startsWith(exclude)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        String ip = getClientIP(request);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Bucket bucket;

//        // Aplicar regra específica para /auth/login (mais restrita)
//        if (path.startsWith("/usuarios/login")) {
//            bucket = bucketsByIp.computeIfAbsent(ip, this::createStrictLoginBucket);
//            if (!consumeTokenOrReject(bucket, response)) {
//                return;
//            }
//            filterChain.doFilter(request, response);
//            return;
//        }

        if (auth != null && auth.isAuthenticated() && auth.getName() != null && !auth.getName().equals("anonymousUser")) {
            // Limita por usuário autenticado
            bucket = bucketsByUser.computeIfAbsent(auth.getName(), this::createUserBucket);
        } else {
            // Limita por IP para usuários não autenticados
            bucket = bucketsByIp.computeIfAbsent(ip, this::createIpBucket);
        }

        if (!consumeTokenOrReject(bucket, response)) {
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean consumeTokenOrReject(Bucket bucket, HttpServletResponse response) throws IOException {
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            return true;
        } else {
            long waitForRefillSeconds = probe.getNanosToWaitForRefill() / 1_000_000_000;
            if (waitForRefillSeconds == 0) waitForRefillSeconds = 1; // garantir ao menos 1 seg

            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setHeader("Retry-After", String.valueOf(waitForRefillSeconds));
            response.getWriter().write("Muitas requisições. Tente novamente em " + waitForRefillSeconds + " segundos.");
            return false;
        }
    }

    private Bucket createIpBucket(String ip) {
        // Limite para IP não autenticado: 5 requisições a cada 10 segundos
        return Bucket4j.builder()
                .addLimit(Bandwidth.classic(5, Refill.intervally(5, Duration.ofSeconds(10))))
                .build();
    }

    private Bucket createUserBucket(String username) {
        // Limite para usuário autenticado: 20 requisições a cada 10 segundos (exemplo)
        return Bucket4j.builder()
                .addLimit(Bandwidth.classic(20, Refill.intervally(20, Duration.ofSeconds(10))))
                .build();
    }

    private Bucket createStrictLoginBucket(String ip) {
        // Limite para login: 3 tentativas a cada 5 minutos (mais restrito)
        return Bucket4j.builder()
                .addLimit(Bandwidth.classic(3, Refill.intervally(3, Duration.ofMinutes(5))))
                .build();
    }

    /**
     * Recupera o IP real do cliente, considerando proxies e load balancers.
     */
    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null || xfHeader.isEmpty()) {
            return request.getRemoteAddr();
        }
        // Pode vir vários IPs separados por vírgula, o primeiro é o original
        return xfHeader.split(",")[0].trim();
    }
}
