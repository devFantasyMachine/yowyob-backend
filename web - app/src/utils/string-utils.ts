









const equals = (str1?: string, str2?: string) => {

    return str1 == str2;
}

const equalsIgnoreCase = (str1?: string, str2?: string) => {

    if (str1 == str2)
        return true;

    if (str1 == undefined || str2 == undefined)
        return false;

    return str1.toLowerCase() == str2.toLowerCase()
}


export const HALF_MONTHS = [
    'Jan',
    'Feb',
    'Mar',
    'Apr',
    'May',
    'Jun',
    'July',
    'Aug',
    'Sep',
    'Oct',
    'Nov',
    'Dec',
  ];
  
export const WEEKS = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];


export const formattedDate = (date: Date | null) => {
	
    return date
    ? `${String(date.getDate()).padStart(2, '0')}, ${HALF_MONTHS[date.getMonth()]
    } ${String(date.getFullYear())}`
    : '--/--';
};



export const StringUtils = {

    equals: equals,
    equalsIgnoreCase: equalsIgnoreCase,
    formattedDate: formattedDate

}

