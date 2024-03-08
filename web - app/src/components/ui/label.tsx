

interface LabelProps {

    htmlFor?: string,
    children: React.ReactNode,  
}


export const Label: React.FC<LabelProps> = ({children, htmlFor}) => {

    return (
        <label className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70" htmlFor={htmlFor}>
        {children}
      </label>
      )
}

