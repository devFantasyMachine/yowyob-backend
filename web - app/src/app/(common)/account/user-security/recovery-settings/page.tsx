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
    <div className="flex flex-col  ">

      <Alert txt={" Change a few things up and try submitting again."} />

    </div>
  );
};

export default page;
