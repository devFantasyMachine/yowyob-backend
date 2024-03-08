"use client"

import Accordion from "@/components/Accordion";
import {
  ChevronDownIcon,
  CreditCardIcon,
  DocumentIcon,
  EyeIcon,
  PaintBrushIcon,
  PlusCircleIcon,
  TrashIcon,
} from "@heroicons/react/24/outline";
import Image from "next/image";
import Link from "next/link";



const page = () => {
  return (
    <ul className="flex flex-col gap-6">

      <div className="p-3 sm:p-4 lg:p-6 xl:p-10 rounded-2xl bg-white shadow-3">
        <Accordion
          buttonContent={(open) => (
            <div className="flex justify-between items-center">

              <h3 className="mb-0 h3"> Payment method </h3>
              <ChevronDownIcon
                className={`w-5 h-5 sm:w-6 sm:h-6 duration-300 ${open ? "rotate-180" : ""
                  }`}
              />
            </div>
          )}
          initialOpen={false}>
          <div className="p-3">
            <div className="border-t my-6"></div>
            <p className="mb-4 clr-neutral-500">
              Cards will be charged either at the end of the month or whenever
              your balance exceeds the usage threshold All major credit/debit
              cards accepted.
            </p>
            <ul className="flex flex-col gap-6">
              <li>
                <div className="border border-dashed p-4 lg:p-6 xl:p-8 rounded-2xl">
                  <div className="flex items-center flex-wrap gap-4 mb-6">
                    <h5 className="mb-0 font-medium"> Leslie Alexander </h5>
                    <span className="inline-block shrink-0 rounded py-1 px-3 bg-[#37D27A] text-xs font-medium">
                      Primary
                    </span>
                  </div>
                  <div className="flex flex-wrap items-center justify-between gap-4">
                    <div className="flex gap-6 items-center">
                      <div className="grid place-content-center w-16 h-16 rounded-full shadow-lg bg-white shrink-0 overflow-hidden">
                        <Image
                          width={44}
                          height={38}
                          src="/img/icon-card.png"
                          alt="image"
                          className="w-full h-full object-fit-contain"
                        />
                      </div>
                      <div className="flex-grow">
                        <h5 className="font-semibold mb-1">
                          MasterCard •••• 4247
                        </h5>
                        <p className="mb-0 clr-neutral-500">
                          Checking - Expires 12/23
                        </p>
                      </div>
                    </div>
                    <div className="flex flex-wrap gap-4">
                      <Link
                        href="#"
                        className="btn-outline-gray flex items-center gap-3 font-semibold shrink-0">
                        <PaintBrushIcon className="w-5 h-5" />
                        Edit
                      </Link>
                      <Link
                        href="#"
                        className="btn-outline-gray flex items-center gap-3 font-semibold shrink-0">
                        <TrashIcon className="w-5 h-5" />
                        Delete
                      </Link>
                    </div>
                  </div>
                </div>
              </li>
              <li>
                <div className="border-dashed border p-4 lg:p-6 xl:p-8 rounded-2xl">
                  <div className="flex items-center flex-wrap gap-4 mb-6">
                    <h5 className="mb-0 font-medium"> Leslie Alexander </h5>
                    <span className="inline-block shrink-0 rounded py-1 px-3 bg-[var(--primary-light)] text-primary text-xs font-medium">
                      Expired
                    </span>
                  </div>
                  <div className="flex flex-wrap items-center justify-between gap-4">
                    <div className="flex gap-6 items-center">
                      <div className="grid place-content-center w-16 h-16 rounded-full shadow-lg bg-white shrink-0 overflow-hidden">
                        <Image
                          width={44}
                          height={38}
                          src="/img/icon-visa.png"
                          alt="image"
                          className="w-full h-full object-fit-contain"
                        />
                      </div>
                      <div className="flex-grow">
                        <h5 className="font-semibold mb-1"> Visa •••• 9077 </h5>
                        <p className="mb-0 clr-neutral-500">
                          Checking - Expires 12/23
                        </p>
                      </div>
                    </div>
                    <div className="flex flex-wrap gap-4">
                      <Link
                        href="#"
                        className="btn-outline-gray flex items-center gap-3 font-semibold shrink-0">
                        <PaintBrushIcon className="w-5 h-5" />
                        Edit
                      </Link>
                      <Link
                        href="#"
                        className="btn-outline-gray flex items-center gap-3 font-semibold shrink-0">
                        <TrashIcon className="w-5 h-5" />
                        Delete
                      </Link>
                    </div>
                  </div>
                </div>
              </li>
              <li>
                <div className="grid place-content-center border border-dashed p-4 lg:p-6 xl:p-8 rounded-2xl text-center">
                  <div className="flex justify-center">
                    <CreditCardIcon className="w-14 h-14 text-[#7A8699]" />
                  </div>
                  <Link
                    href="#"
                    className="link flex items-center justify-center gap-2 mt-1 clr-neutral-400 hover:text-primary">
                    <PlusCircleIcon className="w-5 h-5" />
                    <span className="font-semibold inline-block">
                      Add a new card
                    </span>
                  </Link>
                </div>
              </li>
            </ul>
          </div>
        </Accordion>
      </div>

      <li>
        <div className="p-3 sm:p-4 lg:p-6 xl:p-10 rounded-2xl bg-white shadow-3">
          <h3 className="mb-0 h3"> Order History </h3>
          <div className="border-t my-6"></div>
          <div className="w-full overflow-x-auto">
            <table className="table w-full table-borderless whitespace-nowrap mb-0">
              <thead className="table-light bg-[var(--primary-light)] font-bold">
                <tr>
                  <th className="px-5 py-4 font-medium"> Reference </th>
                  <th className="px-5 py-4 font-medium"> Status </th>
                  <th className="px-5 py-4 font-medium"> Amount </th>
                  <th className="px-5 py-4 font-medium"> Updated </th>
                  <th className="px-5 py-4 font-medium"> Invoice </th>
                  <th className="px-5 py-4 font-medium"></th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td className="px-5 py-4 text-primary">#1544715</td>
                  <td className="px-5 py-4">
                    <span className="inline-flex justify-center text-center rounded py-1 px-2 bg-tertiary-50 clr-tertiary-500 text-xs">
                      Pending
                    </span>
                  </td>
                  <td className="px-5 py-4"> $316 </td>
                  <td className="px-5 py-4"> 22/04/2023 </td>
                  <td className="px-5 py-4">
                    <Link
                      href="#"
                      className="btn-outline-gray-small justify-center flex items-center gap-1 font-medium shrink-0">
                      <DocumentIcon className="w-5 h-5" />

                      <span className="text-sm"> PDF </span>
                    </Link>
                  </td>
                  <td className="px-5 py-4">
                    <Link
                      href="#"
                      className="btn-outline-gray-small justify-center flex items-center gap-1 font-medium shrink-0">
                      <EyeIcon className="w-5 h-5" />
                      <span className="text-sm"> Quick View </span>
                    </Link>
                  </td>
                </tr>
                <tr>
                  <td className="px-5 py-4 text-primary">#1544715</td>
                  <td className="px-5 py-4">
                    <span className="inline-flex justify-center text-center rounded py-1 px-2 bg-[var(--primary-light)] text-primary text-xs">
                      Successful
                    </span>
                  </td>
                  <td className="px-5 py-4"> $316 </td>
                  <td className="px-5 py-4"> 22/04/2023 </td>
                  <td className="px-5 py-4">
                    <Link
                      href="#"
                      className="btn-outline-gray-small justify-center flex items-center gap-1 font-medium shrink-0">
                      <DocumentIcon className="w-5 h-5" />
                      <span className="text-sm"> PDF </span>
                    </Link>
                  </td>
                  <td className="px-5 py-4">
                    <Link
                      href="#"
                      className="btn-outline-gray-small justify-center flex items-center gap-1 font-medium shrink-0">
                      <EyeIcon className="w-5 h-5" />
                      <span className="text-sm"> Quick View </span>
                    </Link>
                  </td>
                </tr>
                <tr>
                  <td className="px-5 py-4 text-primary">#1544715</td>
                  <td className="px-5 py-4">
                    <span className="inline-flex justify-center text-center rounded py-1 px-2 bg-[var(--primary-light)] text-primary text-xs">
                      Successful
                    </span>
                  </td>
                  <td className="px-5 py-4"> $316 </td>
                  <td className="px-5 py-4"> 22/04/2023 </td>
                  <td className="px-5 py-4">
                    <Link
                      href="#"
                      className="btn-outline-gray-small justify-center flex items-center gap-1 font-medium shrink-0">
                      <DocumentIcon className="w-5 h-5" />
                      <span className="text-sm"> PDF </span>
                    </Link>
                  </td>
                  <td className="px-5 py-4">
                    <Link
                      href="#"
                      className="btn-outline-gray-small justify-center flex items-center gap-1 font-medium shrink-0">
                      <EyeIcon className="w-5 h-5" />
                      <span className="text-sm"> Quick View </span>
                    </Link>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </li>
    </ul >
  );
};

export default page;
