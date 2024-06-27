

import { NavLink } from 'react-router-dom';
import IconMenu from '../../components/Icon/IconMenu';
import {  menuItem } from '../../types/dashboard/Menu';

const MenuItem = ({path,item,icon}: menuItem) => {
    
    const property  = "p-2 text-[16px] flex gap-4 items-center";
   
    return (
        <div className={` rounded-md overflow-hidden`}>
            <NavLink to={path} className={({isActive}) => isActive ? `bg-fill ${property}  ` :  `${property}`}>
                <IconMenu icon={icon}></IconMenu>
                <span>{item}</span>

            </NavLink>
        </div>
    );
};

export default MenuItem;