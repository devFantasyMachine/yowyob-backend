// components/layout/defaultNavItems.tsx
import React from "react";
import {
	BellAlertIcon,
	CalendarIcon,
	FolderIcon,
	HomeIcon,
	KeyIcon,
	UserGroupIcon,
	UsersIcon,
	WindowIcon,
} from "@heroicons/react/24/outline";
import { NavItem } from "./Sidebar";

export const defaultNavItems: NavItem[] = [
	{
		label: "Alerts",
		href: "/alerts",
		icon: <BellAlertIcon className="w-6 h-6" />,
	},
	{
		label: "Groups/Roles",
		href: "/groups",
		icon: <UserGroupIcon className="w-6 h-6" />,
	},
	{
		label: "Users",
		href: "/users",
		icon: <UsersIcon className="w-6 h-6" />,
	},
	{
		label: "Authentication",
		href: "/auth",
		icon: <KeyIcon className="w-6 h-6" />,
	},
	{
		label: "Client", 
		icon: <WindowIcon className="w-6 h-6" />,
		submenus: [
			{
				title: "OAuth",
				url: "/oauth-clients"
			},
			{
				title: "API KEY",
				url: "/clients/api-keys"
			}

		]
	},
	{
		label: "General Settings", 
		icon: <FolderIcon className="w-6 h-6" />,
		submenus: [
			{
				title: "Global Config",
				url: "/settings"
			}, 
			{
				title: "Advanced",
				url: "/advanced"
			}

		]
	},
];
