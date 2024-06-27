

import { menuList } from '../../types/dashboard/Menu';
import MenuItem from './MenuItem';


const MenuList = (props : menuList) => {
    
    return (
        <>
        {props.menu.length > 0 && props.menu.map((menu,index) => <MenuItem  key={index} icon={menu.icon} path={menu.path} item={menu.item}></MenuItem>)}
        </>
    );
};

export default MenuList;