import { useEffect } from "react";

/**
 * Add an event listener that will be automatically removed when the component
 * unmounts.
 */
const useEventListener = (type: string, callback: (event: Event) => any) => {
    useEffect(() => {
        document.addEventListener(type, callback);
        
        return () => document.removeEventListener(type, callback);
    },
    []
    );
};

export default useEventListener;