//package com.course_platform.courses.auth;
//
//import com.course_platform.courses.config.AppProperties;
//
//import com.course_platform.courses.repository.TokenRepository;
//import com.course_platform.courses.repository.UserRepository;
//import com.course_platform.courses.service.TokenProvider;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//
//@RequiredArgsConstructor
//public class AuthenticationService {
//    private final UserRepository userRepository;
//    private final RoleRepository roleRepository;
//
//    private final PasswordEncoder passwordEncoder;
//    private final TokenProvider tokenProvider;
//    private final AuthenticationManager authenticationManager;
//    private final UserDetailsService userDetailsService;
//    private final AppProperties appProperties;
//    private final TokenRepository tokenRepository;
//
//
////    public Token signUp(AuthenticationRequest authenticationRequest) {
////        boolean isExistEmail = userRepository.existsByEmail(authenticationRequest.getEmail());
////        if(isExistEmail){
////            throw new CustomRuntimeException(ErrorCode.EMAIL_EXIST);
////        }
////        RoleEntity role = roleRepository.findByName("USER")
////                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.ROLE_NOT_FOUND));
////        UserEntity user = userMapper.toUserEntity(authenticationRequest);
////        user.setRoles(new HashSet<>(List.of(role)));
////        user.setPassword(passwordEncoder.encode(authenticationRequest.getPassword()));
////        UserPrincipal userPrincipal = UserPrincipal.create(userRepository.save(user));
////        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest.getPassword());
////        authenticationManager.authenticate(token);
////        return  generateToken(userPrincipal);
////
////    }
////
////    public Token login(AuthenticationRequest authenticationRequest) {
////        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest.getPassword());
////        authenticationManager.authenticate(token);
////        UserPrincipal userPrincipal = (UserPrincipal) userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
////        this.expiredToken(userPrincipal);
////        return  generateToken(userPrincipal);
////    }
////
////    private void expiredToken(UserPrincipal userPrincipal){
////        this.tokenRepository.updateExpiredToken(userPrincipal.getId());
////    }
////    private Token generateToken(UserPrincipal userPrincipal){
////        String accessToken = tokenProvider.generateAccessToken(userPrincipal);
////        String refreshToken = tokenProvider.generateRefreshToken(userPrincipal);
////        createToken(userPrincipal,refreshToken);
////        return buildToken(accessToken,refreshToken);
////    }
////    private Token buildToken(String accessToken,String refreshToken){
////        return Token.builder()
////                .accessToken(accessToken)
////                .refreshToken(refreshToken)
////                .build();
////    }
////    private void createToken(UserPrincipal userPrincipal,String token){
////        UserEntity user = userRepository.findById(userPrincipal.getId())
////                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.USER_NOT_FOUND));
////        TokenEntity tokenEntity = TokenEntity.builder()
////                .token(token)
////                .expiredAt(new Date(System.currentTimeMillis() +
////                        appProperties.getAuth().getRefreshExpiration()))
////                .type("Bearer")
////                .user(user)
////                .build();
////        tokenRepository.save(tokenEntity);
////    }
////
////
////    public Token refreshToken(TokenRequest tokenRequest) {
////        TokenEntity tokenEntity  = tokenRepository.findByToken(tokenRequest.getRefreshToken())
////                .orElseThrow(() -> new CustomRuntimeException(ErrorCode.TOKEN_NOT_FOUND));
////        if(tokenEntity.isExpired()){
////            throw  new CustomRuntimeException(ErrorCode.TOKEN_EXPRIRED);
////        }
////
////        boolean check = tokenProvider.validateToken(tokenRequest.getRefreshToken());
////        if(!check){
////            tokenEntity.setExpired(true);
////            tokenRepository.save(tokenEntity);
////            throw  new CustomRuntimeException(ErrorCode.TOKEN_EXPRIRED);
////        }
////
////        String email = tokenProvider.extractUsername(tokenRequest.getRefreshToken());
////        UserPrincipal userPrincipal = (UserPrincipal) userDetailsService.loadUserByUsername(email);
////        String accessToken = tokenProvider.generateAccessToken(userPrincipal);
////        return buildToken(accessToken,tokenRequest.getRefreshToken());
////    }
//}
