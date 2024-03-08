

type ButtonType = 'reset' | 'submit' | 'button'

interface ButtonProps {
    children: React.ReactNode,
    type?: ButtonType,
    className?: string,
    variant?: string,
    onClick?: () => void
}


export const Button: React.FC<ButtonProps> = ({ children, type, onClick, className }) => {

    return (
        <button onClick={(e) => {

            e.stopPropagation()
            e.preventDefault()

            onClick && onClick();

        }} className={`inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 bg-primary text-primary-foreground hover:bg-primary/90 h-10 px-4 py-2 w-full ${className}`}  type={type}>
            {children}
        </button>
    )
}

