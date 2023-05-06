package com.tcs.sbi;

import in.shivam.security.PwdSecurityService;

public class UserReg {
	public static void main(String[] args) {
		
		PwdSecurityService pss = new PwdSecurityService();
		String encode =  pss.encode("Shivam");
		System.out.println(encode);
		
	}
}
