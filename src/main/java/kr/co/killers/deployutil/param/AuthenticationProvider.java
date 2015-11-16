package kr.co.killers.deployutil.param;

import kr.co.killers.deployutil.controller.AuthenticationController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASH on 2015-11-16.
 */
public class AuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String svnId = (String)authentication.getPrincipal();
        String svnPassword = (String)authentication.getCredentials();
        // TODO: svnUrl 입력받아 넘기기
        String svnUrl = "";

        log.debug("Login info. {}", svnId + "/" + svnPassword);

        try {
            SVNRepository repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(svnUrl));
            ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(svnId, svnPassword);
            repository.setAuthenticationManager(authManager);

            repository.testConnection();

            log.debug("LOGIN SUCCESS");
            List<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
            roles.add(new SimpleGrantedAuthority("ROLE_USER"));

            UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(svnId, svnPassword, roles);
            result.setDetails(new AuthehticationDetails(svnId, svnPassword));
            return result;

        } catch(SVNException svn) {
            log.debug("LOGIN FAILE");
            throw new BadCredentialsException("Bad credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authehtication) {
        return authehtication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
