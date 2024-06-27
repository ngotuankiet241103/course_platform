import React from 'react';


const FrameSideBar= ({children} : {children: React.ReactNode}) => {
    
    return (
        <div className={`md:min-w-[280px] max-sm:max-w-[240px] bg-second side-bar  py-4 px-2 h-[100vh]`} >
            {children}
        </div>
    );
};

export default FrameSideBar;