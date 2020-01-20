Oauth2 JWT Server
===================


![enter image description here](https://github.com/VickyFengYu/oauth2-jwt-server/blob/master/image/Oauth2-Flow.png?raw=true)

## <i class="icon-folder-open"></i> AuthorizationServerConfigurerAdapter


To create authorization server using spring security oauth2 module, we need to use annotation @EnableAuthorizationServer and extend the class AuthorizationServerConfigurerAdapter.


```
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

```



Spring security oauth exposes two endpoints for checking tokens (/oauth/check_token and /oauth/token_key) which are by default protected behind denyAll(). tokenKeyAccess() and checkTokenAccess() methods open these endpoints for use.


```
@Override
public void configure(final AuthorizationServerSecurityConfigurer oauthServer) {
    oauthServer
        .passwordEncoder(this.passwordEncoder)
        .tokenKeyAccess("permitAll()")
        .checkTokenAccess("isAuthenticated()");
  }

```


## <i class="icon-folder-open"></i> WebSecurityConfigurerAdapter

```
@Bean
@Override
public UserDetailsService userDetailsService() {}

```

> In a typical Spring Boot application secured by Spring Security, users are defined by a UserDetailsService

> as is typical of a Spring Security web application, users are defined in a WebSecurityConfigurerAdapter instance.


## <i class="icon-folder-open"></i> ResourceServerConfigurerAdapter

To create resource server component, use @EnableResourceServer annotation and extend the ResourceServerConfigurerAdapter class.


```
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

```


> Similar to how Spring Security works, you can customize authorization rules by endpoint in Spring Security OAuth, like so:

```
public class HasAuthorityConfig
		extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.authorizeRequests()
				.antMatchers("/flights/**").hasAuthority("#oauth2.hasScope('message:read')")
				.anyRequest().authenticated();
		// @formatter:on
	}

```

> Though, note that if a server is configured both as a resource server and as an authorization server, then there are certain endpoint that require special handling. To avoid configuring over the top of those endpoints (like /token), it would be better to isolate your resource server endpoints to a targeted directory like so:


```
public class ResourceServerEndpointConfig
		extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.antMatchers("/resourceA/**", "/resourceB/**")
			.authorizeRequests()
				.antMatchers("/resourceA/**").hasAuthority("#oauth2.hasScope('resourceA:read')")
				.antMatchers("/resourceB/**").hasAuthority("#oauth2.hasScope('resourceB:read')")
				.anyRequest().authenticated();
		// @formatter:on
	}
```

> As the above configuration will target your resource endpoints and not affect authorization server-specific endpoints.
