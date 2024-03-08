"use client"


import { useRouter } from 'next/navigation'
import { useEffect } from 'react'; 


export default function Component() {

    const router = useRouter(); 

	useEffect(() => {

		router.prefetch("/login");
		router.prefetch("/register");
		router.push("/account");
 
	}, [])

	return null

}


