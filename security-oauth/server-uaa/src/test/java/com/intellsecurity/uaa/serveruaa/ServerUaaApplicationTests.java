package com.intellsecurity.uaa.serveruaa;

//import org.bouncycastle.crypto.generators.BCrypt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ServerUaaApplicationTests {

	@Test
	public void testBcrypt() {

		//对密码进行加密
		String passsword = BCrypt.hashpw("123",BCrypt.gensalt());
		System.out.println(passsword);

		System.out.println(new BCryptPasswordEncoder().encode("secret"));
		System.out.println(new BCryptPasswordEncoder().encode("123"));
	}

}
