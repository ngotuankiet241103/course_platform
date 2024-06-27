import React from "react";
import useTheme from "../../hooks/useTheme";

const FrameDashboard = ({ children }: { children: React.ReactNode }) => {
  const {isExpand,handleToggleExpand} = useTheme();
  return (
    <>
      <div className="p-4">
        <div className="max-sm:hidden">
          {!isExpand && (
            <div className="p-4">
              <div onClick={handleToggleExpand}>
                <i className="fa-solid fa-table-columns"></i>
              </div>
            </div>
          )}
        </div>
        {/* <div className="max-sm:block md:hidden">
          {isShow && <SideBarHeader onClick={handleToggleModel}></SideBarHeader>}
          <div className="p-4">
            <div onClick={handleToggleModel}>
              <i className="fa-solid fa-table-columns"></i>
            </div>
          </div>
        </div> */}
        {children}
      </div>
    </>
  );
};

export default FrameDashboard;
