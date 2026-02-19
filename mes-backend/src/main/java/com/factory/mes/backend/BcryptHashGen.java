package com.factory.mes.backend;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptHashGen {
	public static void main(String[] args) {
	    System.out.println("{bcrypt}" + new BCryptPasswordEncoder().encode("1234"));
	  }
}
