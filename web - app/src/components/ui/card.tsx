

interface Container {

    children: React.ReactNode,
    className?: string
}


export const Card: React.FC<Container> = ({children, className}) => {

    return (
        <div className={`rounded-lg border-1 bg-card text-card-foreground shadow  ${className}`} data-v0-t="card">
            {children}
        </div>
    )
}



export const CardHeader: React.FC<Container> = ({children}) => {

    return (
        <div className="flex flex-col space-y-1.5 p-6">
            {children}
        </div>
    )

}


export const CardContent: React.FC<Container> = ({children}) => {

    return (
        <div className="p-6">
            {children}
        </div>
    )

}





