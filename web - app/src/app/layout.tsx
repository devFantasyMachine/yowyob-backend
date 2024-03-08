'use client'

import './globals.css';
import { useState } from 'react';
import { QueryClient, QueryClientProvider } from "react-query";
import StoreProvider from './store-provider';


export default function RootLayout({ children }: { children: React.ReactNode }) {

	const [queryClient] = useState(() => new QueryClient());

	return (

		<html lang="en">
			<head>
				<title>Yowyob One ID</title>
				<meta name='description' content='Yowyob One ID App' />
				<meta name='viewport' content='width=device-width, initial-scale=1' />
				<link rel='icon' href='/favicon.ico' />
			</head>
			<body suppressHydrationWarning={true}>
				<StoreProvider>
					<QueryClientProvider client={queryClient}>
						<main className="dark:bg-boxdark-2 dark:text-bodydark">
							{children}
						</main>
					</QueryClientProvider>
				</StoreProvider>
			</body>
		</html >
	)
}


