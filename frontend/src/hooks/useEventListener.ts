import { useEffect } from "react";
import devLog from "../util/devLog";

/**
 * Add an event listener that will be automatically removed when the component
 * unmounts.
 */
const useEventListener = (type: string, callback: (event: Event) => any, target = document, log = false) => {
    useEffect(() => {
        target.addEventListener(type, callback);
        
        if (log) {
            devLog(`Event of type ${type} added`);
        }
        
        return () => target.removeEventListener(type, callback);
    },
    []
    );
};

export default useEventListener;