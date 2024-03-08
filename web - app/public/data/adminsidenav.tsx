export const sidenavData = [
  {
    id: 1,
    name: "Apps",
    icon: <i className="las la-hotel text-2xl"></i>,
    submenus: [
      { url: "/apps/all-apps", title: "All Apps" },
      { url: "/apps/add-new-tenant", title: "Add New App" },
      { url: "/apps/attributes", title: "Attributes" },
      { url: "/apps/room-attributes", title: "Room Attributes" }, 
    ],
  },
  {
    id: 2,
    name: "Users",
    icon: <i className="las la-umbrella text-2xl"></i>,
    submenus: [
      { url: "/users/all-user", title: "All Users" },
      { url: "/users/add-new-user", title: "Add New User" },
      { url: "/tour/categories", title: "Categories" },
      { url: "/tour/attributes", title: "Attributes" },
      { url: "/tour/availability", title: "Availability" },
      { url: "/tour/booking-calendar", title: "Booking Calendar" },
      { url: "/tour/recovery", title: "Recovery" },
    ],
  },
  {
    id: 3,
    name: "Groups",
    icon: <i className="las la-network-wired text-2xl"></i>,
    submenus: [
      { url: "/space/all-space", title: "All Space" },
      { url: "/space/add-new-space", title: "Add New Space" },
      { url: "/space/attributes", title: "Attributes" },
      { url: "/space/availability", title: "Availability" },
      { url: "/space/recovery", title: "Recovery" },
    ],
  },
  {
    id: 4,
    name: "Role",
    icon: <i className="las la-plane-departure text-2xl"></i>,
    submenus: [
      { url: "/flight/all-flight", title: "All Flight" },
      { url: "/flight/add-new-flight", title: "Add New Flight" },
      { url: "/flight/airline", title: "Airline" },
      { url: "/flight/airport", title: "Airport" },
      { url: "/flight/seat-type", title: "Seat Type" },
      { url: "/flight/attributes", title: "Attributes" },
    ],
  },
  {
    id: 5,
    name: "OAuth",
    icon: <i className="las la-taxi text-2xl"></i>,
    submenus: [
      { url: "/cab/all-cab", title: "All Cab" },
      { url: "/cab/add-new-cab", title: "Add New Cab" },
      { url: "/cab/attributes", title: "Attributes" },
      { url: "/cab/availability", title: "Availablility" },
      { url: "/cab/recovery", title: "Recovery" },
    ],
  },
  {
    id: 8,
    name: "Cancel Booking",
    icon: <i className="las la-calendar-times text-2xl"></i>,
    url: "/cancel-booking",
  }, 
  {
    id: 16,
    name: "General Settings",
    icon: <i className="las la-cog text-2xl"></i>,
    submenus: [
      { url: "/settings/logo", title: "Logo" },
      { url: "/settings/favicon", title: "Favicon" },
      { url: "/settings/loader", title: "Loader" },
      { url: "/settings/website-content", title: "Website Content" },
      { url: "/settings/footer", title: "Footer" },
      { url: "/settings/error-page", title: "Error Page" },
    ],
  },
 
  {
    id: 19,
    name: "Authentication",
    icon: <i className="las la-user-shield text-2xl"></i>,
    submenus: [
      { url: "/auth/signup", title: "Sign up" },
      { url: "/auth/signin", title: "Sign in" },
      { url: "/auth/error", title: "404 Error" },
    ],
  },
];
