"use client";

import iconGoogle from "../../../../../public/img/icon-google.png";

import iconDropbox from "../../../../../public/img/icon-dropbox.png";
import iconSlack from "../../../../../public/img/icon-slack.png";
import iconMailChimp from "../../../../../public/img/icon-mailchimp.png";
import iconJira from "../../../../../public/img/icon-jira.png";
import Image from "next/image";
import Link from "next/link";
import Accordion from "@/components/Accordion";
import {
  ChevronDownIcon,
  ComputerDesktopIcon,
  DevicePhoneMobileIcon,
} from "@heroicons/react/24/outline";
import CustomSwitch from "@/components/Switch";
import Alert from "@/components/Alert";
import { useState } from "react";








const page = () => {


  const [selected, setSelected] = useState(1)


  return (
    <div className="flex flex-col p-4 sm:p-6 md:p-8">

      <Alert txt={" Change a few things up and try submitting again."} />

      <div className="mb-6 flex flex-col gap-4">
        <Accordion
          buttonContent={(open) => (
            <div className="rounded-2xl flex justify-between items-center">
              <h3 className="h3">Two-step verification</h3>
              <ChevronDownIcon
                className={`w-5 h-5 sm:w-6 sm:h-6 duration-300 ${open ? "rotate-180" : ""
                  }`}
              />
            </div>
          )}
          initialOpen={true}>
          <div className="pt-4 lg:pt-6">
            <p className="mb-4">
              Start by entering your password so that we know it&apos;s you.
              Then we&apos;ll walk you through two more simple steps.
            </p>
            <form action="#" className="grid grid-cols-12 gap-4">
              <div className="col-span-12">
                <label
                  htmlFor="account-password"
                  className="block mb-2 font-medium clr-neutral-500">
                  Account password :
                </label>
                <input
                  type="password"
                  id="account-password"
                  className="py-3 px-6 border w-full focus:outline-none rounded-lg"
                  placeholder="Enter current password"
                />
              </div>
              <div className="col-span-12">
                <Link
                  href="#"
                  className="link inline-block py-3 px-6 rounded-full bg-primary text-white :bg-primary-400 hover:text-white font-semibold">
                  Set Up
                </Link>
              </div>
            </form>
          </div>
        </Accordion>

      </div>

 
    </div>
  );
};

export default page;
