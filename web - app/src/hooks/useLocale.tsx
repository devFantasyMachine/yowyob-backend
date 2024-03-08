"use client";
import { useEffect } from "react";
import useLocalStorage from "./useLocalStorage";

const useLocale = () => {
  const [locale, setLocale] = useLocalStorage("locale", "en");

  useEffect(() => {

    console.log("new locale ", locale)
 
  }, [locale]);

  return [locale, setLocale];
};

export default useLocale;
